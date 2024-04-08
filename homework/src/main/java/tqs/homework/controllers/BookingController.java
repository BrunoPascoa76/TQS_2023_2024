package tqs.homework.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.homework.data.Trip;
import tqs.homework.data.Reservation;
import tqs.homework.services.BookingService;


@RestController
@RequestMapping("/api")
public class BookingController {
    private BookingService service;

    @Autowired
    public BookingController(BookingService service){
        this.service=service;
    }

    @GetMapping("/trips/list")
    public ResponseEntity<String> getAllTrips(){
        List<Trip> trips=service.getTrips();
        return ResponseEntity.status(200).body(processTrips(trips));
    }

    @GetMapping("/trips")
    public ResponseEntity<String> getTripsParameters(@RequestParam long tripDate,String fromLocation,String toLocation){
        List<Trip> trips=service.getTrips(new Date(tripDate), fromLocation, toLocation);
        return ResponseEntity.status(200).body(processTrips(trips));
    }

    @PostMapping("/trips/schedule")
    public ResponseEntity<String> bookTrip(@RequestHeader String token, @RequestBody Reservation reservation){
        Optional<Reservation> result=service.reserveSeat(token, reservation.getTrip().getId(), reservation.getSeat());
        if(result.isPresent()){
            return ResponseEntity.status(201).body("scheduling successful");
        }else{
            return ResponseEntity.status(403).body("token/seat/tripId incorrect/missing");
        }
    }

    private String processTrips(List<Trip> trips){ //it's safer to send the occupiedSeats list instead of the reservations (would reveal usernames)
        JSONArray result=new JSONArray();
        for(Trip trip:trips){
            JSONObject entry=new JSONObject();
            entry.put("id",trip.getId());
            entry.put("busNumber",trip.getBusNumber());
            entry.put("tripDate",trip.getTripDate());
            entry.put("fromTime",trip.getFromTime());
            entry.put("toTime",trip.getToTime());
            entry.put("fromLocation",trip.getFromLocation());
            entry.put("toLocation",trip.getToLocation());
            entry.put("numSeats", trip.getNumSeats());

            List<Integer> occupiedSeats=new ArrayList<>();
            for(Reservation reservation: trip.getReservations()){
                occupiedSeats.add(reservation.getSeat());
            }
            result.put(entry);
        }
        return result.toString();
    }
}

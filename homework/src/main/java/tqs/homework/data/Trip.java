package tqs.homework.data;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private int busNumber;

    @Temporal(TemporalType.DATE)
    private Date tripDate;

    @Temporal(TemporalType.TIME)
    private Time fromTime;
    @Temporal(TemporalType.TIME)
    private Time toTime;

    private String fromLocation;
    private String toLocation;

    private int numSeats=45;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public Trip(int busNumber,Date tripDate,Time fromTime,Time toTime,String fromLocation,String toLocation){
        this.busNumber=busNumber;
        this.tripDate=tripDate;
        this.fromTime=fromTime;
        this.toTime=toTime;
        this.fromLocation=fromLocation;
        this.toLocation=toLocation;
    }
}
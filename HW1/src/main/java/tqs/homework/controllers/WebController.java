package tqs.homework.controllers;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tqs.homework.data.User;
import tqs.homework.services.AuthenticationService;
import tqs.homework.services.BookingService;

@Controller
@RequestMapping("/")
public class WebController {

    private AuthenticationService authService;
    private BookingService bookingService;
    private String username="username";

    @Autowired
    public WebController(AuthenticationService authService, BookingService bookingService){
        this.authService=authService;
        this.bookingService=bookingService;
    }

    @GetMapping({"/","/home","/index"})
    public String home(@RequestHeader(name="token",required=false) String token, Model model) {
        if(token!=null){
            Optional<User> user=authService.getFromToken(token);
            if(user.isPresent()){
                model.addAttribute(username, user.get().getUsername());
            }
        }
        model.addAttribute("locations",Arrays.asList("Porto","Aveiro","Figueira da Foz"));
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestHeader(name="token",required=false) String token, Model model){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(@RequestHeader(name="token",required=false) String token, Model model){
        return "register";
    }

    @GetMapping("/results")
    public String loginPage(@RequestHeader(name="token",required=false) String token, Model model, @RequestParam long tripDate, @RequestParam String fromLocation, @RequestParam String toLocation){
        if(token!=null){
            Optional<User> user=authService.getFromToken(token);
            if(user.isPresent()){
                model.addAttribute(username, user.get().getUsername());
            }
        }
        model.addAttribute("fromLocation",fromLocation);
        model.addAttribute("toLocation", toLocation);
        model.addAttribute("trips",bookingService.getTrips(new Date(tripDate), fromLocation, toLocation));

        return "results";
    }
}

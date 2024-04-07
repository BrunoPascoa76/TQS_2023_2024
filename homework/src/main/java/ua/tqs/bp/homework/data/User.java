package ua.tqs.bp.homework.data;

import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that represents each account in the website
 */
@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private String username;
    private String password; // pre-hashed, of course
    
    
    @OneToMany(fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    public User(String username, String password){
        this.username=username;
        this.password=password;
        this.reservations=new ArrayList<>();
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }
}
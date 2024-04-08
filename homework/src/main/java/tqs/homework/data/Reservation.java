package tqs.homework.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name="trip")
    private Trip trip;

    private int seat;

    public Reservation(User user,Trip trip,int seat){
        this.user=user;
        this.trip=trip;
        this.seat=seat;
    }

    public Reservation(long tripId,int seat){//for requests
        this.trip=new Trip(tripId);
        this.seat=seat;
    }
}

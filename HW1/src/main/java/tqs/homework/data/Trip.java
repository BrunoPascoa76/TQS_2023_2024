package tqs.homework.data;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "busNumber")
    private int busNumber;

    @Temporal(TemporalType.DATE)
    private Date tripDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "fromTime")
    private Time fromTime;
    @Temporal(TemporalType.TIME)
    @Column(name = "toTime")
    private Time toTime;

    @Column(name = "fromLocation")
    private String fromLocation;
    @Column(name = "toLocation")
    private String toLocation;

    @Column(name="numSeats")
    private int numSeats = 45;

    @OneToMany(mappedBy = "trip")
    private List<Reservation> reservations;

    public Trip(int busNumber, Date tripDate, Time fromTime, Time toTime, String fromLocation, String toLocation) {
        this.busNumber = busNumber;
        this.tripDate = tripDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    public Trip(Date tripDate, String fromLocation, String toLocation) {
        this.tripDate = tripDate;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    public Trip(long id) {
        this.id = id;
    }
}
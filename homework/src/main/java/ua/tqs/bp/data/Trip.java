package ua.tqs.bp.data;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trip")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private int busNumber;

    @Temporal(TemporalType.TIME)
    private Date fromTime;
    @Temporal(TemporalType.TIME)
    private Date toTime;

    private String fromLocation;
    private String toLocation;

    private int numSeats=45;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
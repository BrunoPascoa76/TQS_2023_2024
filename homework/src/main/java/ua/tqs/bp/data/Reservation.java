package ua.tqs.bp.data;

import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Reservation {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch=FetchType.LAZY)
    private List<Seat> seats;

    @ManyToOne(fetch=FetchType.LAZY)
    private Trip trip;
}

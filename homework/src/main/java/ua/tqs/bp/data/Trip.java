package ua.tqs.bp.data;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Trip {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    @OneToMany(fetch=FetchType.LAZY)
    private List<Reservation> reservations;

}

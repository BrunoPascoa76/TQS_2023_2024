package ua.bp.lab_3_2.data;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;
    @NonNull
    private String maker;
    @NonNull
    private String model;

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }
}

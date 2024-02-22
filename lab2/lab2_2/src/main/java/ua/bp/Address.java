package ua.bp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class Address {
    private String city;
    private String zip;
    private String road;
    private String houseNumber;
}

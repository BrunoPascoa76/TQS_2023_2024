package ua.bp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Stock {
    private String label;
    private int quantity;
}

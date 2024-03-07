package ua.bp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReservePage {
    private WebDriver driver;

    public ReservePage(WebDriver driver) {
        this.driver = driver;
    }

    public DetailsPage selectFlight() {
        driver.findElement(By.cssSelector("tr:nth-child(4) .btn")).click();
        return new DetailsPage(driver);
    }
}

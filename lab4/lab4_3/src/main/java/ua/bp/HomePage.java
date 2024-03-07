package ua.bp;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage{
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1550, 878));
    }

    public void selectFrom(String option) {
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Philadelphia']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(2)")).click();
    }

    public void selectTo(String option) {
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(2)")).click();
    }

    public ReservePage findFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
        return new ReservePage(driver);
    }
}

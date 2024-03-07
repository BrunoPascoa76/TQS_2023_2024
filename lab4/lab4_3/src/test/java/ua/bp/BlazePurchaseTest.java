package ua.bp;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazePurchaseTest {
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Test
    public void purchasetest(FirefoxDriver driver) {
        HomePage homePage = new HomePage(driver);
        homePage.selectFrom("Philadelphia");
        homePage.selectTo("Rome");
        ReservePage reservePage = homePage.findFlights();

        DetailsPage detailsPage=reservePage.selectFlight();

        detailsPage.inputName("asda");
        detailsPage.inputAddress("sdad");
        detailsPage.inputCity("dasdad");
        detailsPage.inputState("dasasd");
        detailsPage.inputZip("12345");
        detailsPage.selectCard("American Express","12345","abcd");
        ConfirmationPage confirmationPage=detailsPage.purchaseFlight();

        assertThat("Thank you for your purchase today!");

    }
}

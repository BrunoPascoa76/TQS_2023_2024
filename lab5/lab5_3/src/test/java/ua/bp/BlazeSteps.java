package ua.bp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazeSteps {
    private WebDriver driver;

    @Given("a webpage open on the homepage")
    public void goToHomePage(){
        driver=WebDriverManager.chromedriver().create();
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1550, 878));
    }

    @When("I select a flight from {string} to {string}")
    public void selectToFrom(String from, String to){
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = '"+from+"']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(2)")).click();

        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = '"+to+"']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @When("I select a flight")
    public void selectFlight(){
        driver.findElement(By.cssSelector("tr:nth-child(4) .btn")).click();
    }

    @When("I put {string} as my name")
    public void fillInname(String name){
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys(name);
    }

    @When("I put {string} as my address")
    public void fillInAddress(String address){
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(address);
    }

    @When("I put {string} as my city")
    public void fillInCity(String city){
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys(city);
    }

    @When("I put {string} as my state")
    public void fillInState(String state){
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys(state);
    }

    @When("I put {string} as my zip code")
    public void fillInZipCode(String zipCode){
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys(zipCode);
    }

    @When("I put {string}, {string} and {string} as my card's details")
    public void fillInCardDetails(String cardType, String cardNumber, String cardName) {
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = '" + cardType + "']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys(cardNumber);
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys(cardName);
    }

    @When("I purchase a flight")
    public void purchaseFlight(){
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("h1")).click();
    }

    @Then("I see a page that says {string}")
    public void assertTitle(String title){
        assertEquals(title, driver.findElement(By.cssSelector("h1")).getText());
    }
}

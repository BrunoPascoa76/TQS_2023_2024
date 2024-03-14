package ua.bp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DetailsPage {
    private WebDriver driver;

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputName(String name) {
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys(name);
    }

    public void inputAddress(String address) {
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys(address);
    }

    public void inputCity(String city) {
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys(city);
    }

    public void inputState(String state) {
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys(state);
    }

    public void inputZip(String zip) {
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys(zip);
    }

    public void selectCard(String cardType, String cardNumber, String cardName) {
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

    public ConfirmationPage purchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("h1")).click();
        return new ConfirmationPage(driver);
    }
}

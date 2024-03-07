package ua.bp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {
    private WebDriver driver;

    public ConfirmationPage(WebDriver driver){
        this.driver=driver;
    }

    public String getTitle(){
        return driver.findElement(By.cssSelector("h1")).getText();
    }
}

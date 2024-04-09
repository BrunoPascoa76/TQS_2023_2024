package tqs.homework;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;

public class SeleniumManager {
    private WebDriver driver;
    private WebDriverWait wait;
    private LocalStorage local;

    public SeleniumManager(int port) {
        this.driver = WebDriverManager.chromedriver().create();
        driver.get("http://localhost:" + port + "/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,Duration.ofSeconds(2));
        local = ((WebStorage) driver).getLocalStorage();
        local.clear();
    }

    public void shutdown() {
        driver.quit();
    }

    public void clickLink(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }

    public void typeById(String input, String id) {
        driver.findElement(By.id(id)).click();
        driver.findElement(By.id(id)).sendKeys(input);
    }

    public void selectById(int optionNum, String id) {
        driver.findElement(By.id(id)).click();
        driver.findElement(By.cssSelector("#" + id + " > option:nth-child(" + optionNum + ")")).click();
    }

    public void clickById(String id) {
        driver.findElement(By.id(id)).click();
    }

    public void submit() {
        driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
    }

    public void assertHome() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcome")));
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Welcome to TicketBus"));
    }

    public void assertAlert(String message) {
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is(message));
    }

    public void insertIntoLocalStorage(String key, String value) {
        local.setItem(key, value);
    }
}

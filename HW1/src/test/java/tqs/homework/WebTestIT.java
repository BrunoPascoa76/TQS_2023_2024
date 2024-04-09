package tqs.homework;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class WebTestIT {
    @LocalServerPort
    int port;

    private WebDriver driver=WebDriverManager.chromedriver().create();
    private JavascriptExecutor js = (JavascriptExecutor) driver;  

    @Container
    public static MySQLContainer<?> container= new MySQLContainer<>("mysql:latest")
        .withUsername("sa")
        .withPassword("password")
        .withDatabaseName("showcase");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.password",container::getPassword);
        registry.add("spring.datasource.username",container::getUsername);
    }

    @Test
    public void contextLoads(){

    }
    
}

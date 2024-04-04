package ua.bp.tqs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    String baseURI = "https://jsonplaceholder.typicode.com/";

    @Test
    public void testTodos200() {
        given().when().get(baseURI + "todos").then().statusCode(200);
    }

    @Test
    public void testTodo4Title() {
        given().when()
                .get(baseURI + "todos/" + "4")
                .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void testTodos198And199Exist() {
        given().when()
                .get(baseURI + "todos")
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }

    @Test
    public void testTodosResponseTime(){
        given().when()
            .get(baseURI+"todos")
            .then()
            .statusCode(200)
            .time(lessThan(2000L));
    }
}

package ua.bp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CalculatorSteps {
    private Calculator calc;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @Given("I add {int} and {int}")
    public void add(int val1, int val2) {
        calc.insert(val1);
        calc.insert(val2);
        calc.insert("+");
    }

    @Given("I subtract {int} to {int}")
    public void subtract(int val1, int val2) {
        calc.insert(val1);
        calc.insert(val2);
        calc.insert("-");
    }

    @Given("I multiply {int} with {int}")
    public void multiply(int val1, int val2) {
        calc.insert(val1);
        calc.insert(val2);
        calc.insert("*");
    }

    @Given("I divide {int} by {int}")
    public void divide(int val1, int val2) {
        calc.insert(val1);
        calc.insert(val2);
        calc.insert("/");
    }

    @Then("the result is {int}")
    public void result(int expected){
        assertEquals(expected, calc.result());
    }
}

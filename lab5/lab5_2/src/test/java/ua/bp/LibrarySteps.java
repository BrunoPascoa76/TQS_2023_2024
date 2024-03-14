package ua.bp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LibrarySteps {
    Library library;
    List<Book> result;

    @Given("a library with the following books:")
    public void setup(DataTable table) {
        library = new Library();

        for (Book book : tableToBooks(table)) {
            library.addBook(book);
        }
    }

    @Given("I search for a book with the title {string}")
    public void searchByTitle(String title) {
        result = library.searchByTitle(title);
    }

    @Given("I search for a book written by {string}")
    public void searchByAuthor(String author) {
        result = library.searchByAuthor(author);
    }

    @Given("I search for a book released between {int} and {int}")
    public void searchByYears(int start, int end) {
        result = library.searchByYears(start, end);
    }

    @Then("I should find no books")
    public void nobooks() {
        assertEquals(0, result.size());
    }

    @Then("I should find the following books:")
    public void assertBooks(DataTable table) {
        Set<Book> expected = Set.copyOf(tableToBooks(table));
        Set<Book> actual = Set.copyOf(result);

        assertEquals(expected, actual);
    }

    public List<Book> tableToBooks(DataTable table) {
        return table.asMaps(String.class, String.class).stream()
                .map(book -> new Book(book.get("Title"), book.get("Author"), Integer.parseInt(book.get("Published"))))
                .collect(Collectors.toList());
    }
}

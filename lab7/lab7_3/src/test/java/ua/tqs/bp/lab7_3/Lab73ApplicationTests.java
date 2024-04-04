package ua.tqs.bp.lab7_3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Lab73ApplicationTests {

	@Autowired
	private BookRepository repo;

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("Roland")
			.withPassword("ProjectMoon")
			.withDatabaseName("LibraryofRuina");

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@Test
	@Order(1)
	void testAddBook() {
		Book book = new Book();
		book.setTitle("Book of Jun");
		repo.save(book);
	}

	@Test
	@Order(2)
	void testCheckAllBooks() {
		List<Book> books = repo.findAll();
		assertEquals(5, books.size());
	}

	@Test
	@Order(3)
	void testFindBookByName() {
		Book book = repo.findByName("Book of Tomerry");
		assertNotNull(book);
	}

	@Test
	@Order(4)
	void testFindBookById() {
		Book book = repo.findById(1);
		assertEquals("Book of Bamboo-hatted Kim", book.getTitle());
	}
}

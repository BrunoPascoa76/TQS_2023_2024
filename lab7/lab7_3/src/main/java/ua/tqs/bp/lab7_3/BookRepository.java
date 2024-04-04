package ua.tqs.bp.lab7_3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
    Book findById(long id);
    Book findByName(String name);
}

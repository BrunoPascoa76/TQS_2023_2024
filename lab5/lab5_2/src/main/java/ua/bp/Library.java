package ua.bp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    List<Book> library;

    public Library(){
        library=new ArrayList<>();
    }

    public void addBook(Book book){
        library.add(book);
    }

    public List<Book> searchByTitle(String title){
        return library.stream().filter(book->book.getTitle().equals(title)).collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author){
        return library.stream().filter(book->book.getAuthor().equals(author)).collect(Collectors.toList());
    }

    public List<Book> searchByYears(int start, int end){
        return library.stream().filter(book->(book.getYear()>=start && book.getYear()<=end)).collect(Collectors.toList());
    }
}

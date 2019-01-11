package br.com.guiabolso.iws.store.model.response;

import br.com.guiabolso.iws.store.model.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BooksResponse {
    private Integer numberBooks;
    private List<BookResponse> books;


    public BooksResponse(List<Book> books) {
        this.numberBooks = books.size();
        this.books = books.stream().map(BookResponse::new).collect(Collectors.toList());
    }

    public Integer getNumberBooks() {
        return numberBooks;
    }

    public void setNumberBooks(Integer numberBooks) {
        this.numberBooks = numberBooks;
    }

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}

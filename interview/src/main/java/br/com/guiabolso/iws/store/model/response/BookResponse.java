package br.com.guiabolso.iws.store.model.response;

import br.com.guiabolso.iws.store.model.entity.Book;

public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private String isbn;
    private String language;

    public BookResponse(){
        super();
    }

    public BookResponse(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.language = book.getLanguage();

    }

    public BookResponse(String title, String description, String isbn, String language) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
    }

    public BookResponse(Long id, String title, String description, String isbn, String language) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

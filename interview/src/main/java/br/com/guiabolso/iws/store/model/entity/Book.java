package br.com.guiabolso.iws.store.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String isbn;
    @Column
    private String language;

    public Book(){
        super();
    }

    public Book(String title, String description, String isbn, String language) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
    }

    public Book(Long id, String title, String description, String isbn, String language) {
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

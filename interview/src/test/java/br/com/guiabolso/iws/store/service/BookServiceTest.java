/*
package com.test.bookstore.service;

import Book;
import BookRepository;
import NOSQLBookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.TestUtils.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    SaraivaClient saraivaClient;

    @Mock
    NOSQLBookRepository nosqlBookRepository;
    @Mock
    ThreadPoolTaskExecutor executor;

    BookService bookService;

    @Before
    public void setUp() {
        bookService = new BookService(bookRepository, saraivaClient, nosqlBookRepository, executor);
    }

    @Test
    public void findBookByidReturnBook() {
        Book expected = new Book(333, "name", "brand", 40.0);
        when(bookRepository.findById(333)).thenReturn(Optional.of(expected));
        Book actual = bookService.findByid(333);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void findBookByidReturnNull() {
        when(bookRepository.findById(333)).thenReturn(Optional.empty());
        Book actual = bookService.findByid(333);
    }
}

*/

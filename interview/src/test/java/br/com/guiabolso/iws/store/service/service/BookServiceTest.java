package br.com.guiabolso.iws.store.service.service;

import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.repository.BookRepository;
import br.com.guiabolso.iws.store.repository.NOSQLBookRepository;
import br.com.guiabolso.iws.store.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private NOSQLBookRepository nosqlBookRepository;

    private BookService service;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Before
    public void setUp() throws Exception {
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.initialize();
        this.service = new BookService(bookRepository,nosqlBookRepository,threadPoolTaskExecutor);
    }


    @Test
    public void insert(){
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(buildBook());
        Book book = buildBook();
        service.insertBook(book);
    }

    @Test(expected = Exception.class)
    public void insertFail(){
        Mockito.when(bookRepository.save(Mockito.any())).thenThrow(RuntimeException.class);
        Book book = buildBook();
        service.insertBook(book);
    }

    private Book buildBook() {
        return new Book("The Guardian","it's nice book","1234567890987","PT");
    }


}

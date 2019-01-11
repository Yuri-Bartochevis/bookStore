package br.com.guiabolso.iws.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.repository.BookRepository;
import br.com.guiabolso.iws.store.repository.NOSQLBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BookService {
    public static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;
    private NOSQLBookRepository nosqlBookRepository;
    private ThreadPoolTaskExecutor executor;

    @Autowired
    public BookService(BookRepository bookRepository, NOSQLBookRepository nosqlBookRepository, @Qualifier("mongoExecutor") ThreadPoolTaskExecutor executor) {
        this.bookRepository = bookRepository;
        this.nosqlBookRepository = nosqlBookRepository;
        this.executor = executor;
    }

    public Book insertBook(Book book) {
        LOGGER.info("persisting book: {}",book.getTitle());
        Book savedBook = bookRepository.save(book);
        saveDocumentDatabase(book);
        return savedBook;
    }

    @Cacheable(value = "GUIABOLSO_CACHE", key = "'BOOK'+#id")
    @HystrixCommand(fallbackMethod = "findByIdNoSql")
    public Book findByid(Long id) {
        LOGGER.info("Accessing database to get book with id {}",id);
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id: " + id + " not exists"));
    }

    public Book findByIdNoSql(Long id) {
        LOGGER.warn("Calling fallback, please check main database status");
        return nosqlBookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id: " + id + " not exists into NOSQL"));
    }

    @HystrixCommand(fallbackMethod = "allBooksFromNoSql")
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public List<Book> allBooksFromNoSql() {
        return nosqlBookRepository.findAll();
    }


    @CacheEvict(value = "GUIABOLSO_CACHE", key = "'BOOK'+#id")
    public void delete(Long id) {
        deleteDocumentDatabase(id);
        bookRepository.deleteById(id);
    }

    private CompletableFuture<Void> deleteDocumentDatabase(Long id) {
        return CompletableFuture.runAsync(() -> nosqlBookRepository.deleteById(id), executor);
    }

    private CompletableFuture<Void> saveDocumentDatabase(Book newBook) {
        LOGGER.info("SAVING ASYNC ON MONGODB...");
        return CompletableFuture.runAsync(() -> nosqlBookRepository.save(newBook), executor);
    }

}
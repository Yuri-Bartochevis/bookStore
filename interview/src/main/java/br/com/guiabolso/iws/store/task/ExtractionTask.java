package br.com.guiabolso.iws.store.task;

import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.service.BookService;
import br.com.guiabolso.iws.store.service.crawler.BookCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractionTask {
    public static final Logger lOGGER = LoggerFactory.getLogger(ExtractionTask.class);

    private BookCrawlerService bookCrawlerService;
    private BookService bookService;

    @Autowired
    public ExtractionTask(BookCrawlerService bookCrawlerService, BookService bookService) {
        this.bookCrawlerService = bookCrawlerService;
        this.bookService = bookService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        lOGGER.info("Starting Extraction");
        try {
            List<Book> list = bookCrawlerService.extract().get();
            lOGGER.info("list size : {}",list.size());
                list.forEach(bookService::insertBook);
                lOGGER.error("extraction ended...");
        } catch (Exception ex) {
            lOGGER.error("Problem was found...", ex);
        }
    }
}

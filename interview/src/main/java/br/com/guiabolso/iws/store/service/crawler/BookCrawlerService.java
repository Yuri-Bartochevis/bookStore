package br.com.guiabolso.iws.store.service.crawler;

import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.util.IsbnFinder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BookCrawlerService {
    public static final Logger LOGGER = LoggerFactory.getLogger(BookCrawlerService.class);
    public static final String UNAVAILABLE = "Unavailable";

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public BookCrawlerService(@Qualifier("crawlerPoolExecutor") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }


    public CompletableFuture<List<Book>> extract() throws IOException {
        Document doc = Jsoup.connect("https://kotlinlang.org/docs/books.html").get();
        List<CompletableFuture<Book>> listFutures = getPromisesOfBook(doc);
        return parseCompletableFuture(listFutures);

    }

    protected List<CompletableFuture<Book>> getPromisesOfBook(Document doc) {
        Element bookBody = doc.select("html > body > div.global-layout > div.g-layout.global-content > article.page-content.g-9").first();
        return bookBody.select("h2").stream().map(titleElement -> {
            String title = titleElement.html();
            Element languageElement = titleElement.nextElementSibling();
            Element linkElement = languageElement.nextElementSibling();
            Element descriptionElement = linkElement.nextElementSibling();
            return CompletableFuture.supplyAsync(() -> buildBook(title, languageElement.html(), descriptionElement.html(), linkElement.attr("abs:href")), threadPoolTaskExecutor);
        }).collect(Collectors.toList());
    }

    protected CompletableFuture<List<Book>> parseCompletableFuture(List<CompletableFuture<Book>> listFutures) {
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[0]));

        return allFutures.thenApply(future -> listFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

    private Book buildBook(String title, String language, String description, String link) {
        String isbn = UNAVAILABLE;
        try {
            Document doc = Jsoup.connect(link).timeout(50000).get();
            Elements allElements = doc.getAllElements();
            isbn = allElements.stream().map(element -> new IsbnFinder().apply(element)).filter(Objects::nonNull).findFirst().orElse("undefined");
        } catch (Exception ex) {
            LOGGER.error("Problems to get Connection...", ex);
        }
        LOGGER.info("extracting book : {}", title);
        return new Book(title, description, isbn, language);
    }


}

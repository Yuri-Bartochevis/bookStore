package br.com.guiabolso.iws.store.service.crawler;

import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.service.TestUtils;
import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class BookCrawlerServiceTest {


    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    BookCrawlerService crawlerService;

    @Before
    public void setUp() throws Exception {
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.initialize();
        crawlerService= new BookCrawlerService(threadPoolTaskExecutor);

    }

    @Test
    public void getPromisesReturnListOfBooks() throws IOException, ExecutionException, InterruptedException {
        String html = new TestUtils().readResource("kotlinPage.txt", Charset.defaultCharset());
        List listPromises = crawlerService.getPromisesOfBook(Jsoup.parse(html));
        assertFalse(listPromises.isEmpty());
    }


    @Test
    public void testDicingFutures() throws IOException, ExecutionException, InterruptedException {
        List<CompletableFuture<Book>> listPromises = Arrays.asList(CompletableFuture.completedFuture(new Book()), CompletableFuture.completedFuture(new Book()));
        CompletableFuture<List<Book>> switchedData = crawlerService.parseCompletableFuture(listPromises);
        Assert.assertEquals(listPromises.size(),switchedData.get().size());
    }


    @After
    public void disposable(){
        threadPoolTaskExecutor.destroy();
    }
}
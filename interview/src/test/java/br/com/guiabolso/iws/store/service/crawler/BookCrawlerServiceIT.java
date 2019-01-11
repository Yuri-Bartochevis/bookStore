package br.com.guiabolso.iws.store.service.crawler;

import br.com.guiabolso.iws.store.repository.NOSQLBookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@IfProfileValue(name = "integration", values ={"true"})
@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookCrawlerServiceIT {

    @MockBean
    DataSource dt;

    @MockBean
    NOSQLBookRepository mgTemplate;

    @Autowired
    BookCrawlerService crawlerService;

    @Test
    public void extract() throws Exception {
      List bookList = crawlerService.extract().get();
      Assert.assertFalse(bookList.isEmpty());
    }


}

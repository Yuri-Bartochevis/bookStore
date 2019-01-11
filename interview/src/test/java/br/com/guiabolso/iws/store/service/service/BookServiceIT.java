package br.com.guiabolso.iws.store.service.service;


import br.com.guiabolso.iws.store.model.entity.Book;
import br.com.guiabolso.iws.store.repository.BookRepository;
import br.com.guiabolso.iws.store.repository.NOSQLBookRepository;
import br.com.guiabolso.iws.store.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@IfProfileValue(name = "integration", values = {"true"})
@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceIT {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private NOSQLBookRepository nosqlBookRepository;

    @InjectMocks
    private BookService service;


    @Test
    public void testingCache() throws Exception {
        Book book = service.findByid(2L);
        Book otherBook = service.findByid(3L);
        Book sameAsFirstbook = service.findByid(2L);
        Mockito.verify(bookRepository.findById(Mockito.any()),Mockito.atMost(2));
    }


}

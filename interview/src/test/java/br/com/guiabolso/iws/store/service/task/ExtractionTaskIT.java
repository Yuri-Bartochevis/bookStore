package br.com.guiabolso.iws.store.service.task;

import br.com.guiabolso.iws.store.task.ExtractionTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@IfProfileValue(name = "integration", values ={"true"})
@ActiveProfiles("it")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExtractionTaskIT {

    @MockBean
    DataSource dt;

    @MockBean
    ExtractionTask task;

    @Test
    public void extractionCalledWhenInitialized() {
        Mockito.verify(task,Mockito.atLeastOnce()).run();
    }


}


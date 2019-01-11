package br.com.guiabolso.iws.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {

    @Bean("mongoExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(5_000);
        taskExecutor.setThreadNamePrefix("MongoPool");
        taskExecutor.initialize();
        return taskExecutor;
    }


    @Bean("crawlerPoolExecutor")
    public ThreadPoolTaskExecutor getCrawlerPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(15);
        taskExecutor.setQueueCapacity(5_000);
        taskExecutor.setThreadNamePrefix("CrawlerPool");
        taskExecutor.initialize();
        return taskExecutor;
    }
}

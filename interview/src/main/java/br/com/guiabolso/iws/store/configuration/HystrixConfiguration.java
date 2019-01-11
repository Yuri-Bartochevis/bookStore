package br.com.guiabolso.iws.store.configuration;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfiguration implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(HystrixConfiguration.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Configuring Hystrix...");
        //Setting the number off sequential errors to open the circuit and stop calling the method annotated with @HystrixCommand and go to fallback
        ConfigurationManager.getConfigInstance().addProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 50);
        //Setting the time to wait until try to close the circuit again executing the method annotated with @HystrixCommand
        ConfigurationManager.getConfigInstance().addProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds", 5 * 1000);
        //This is the timeout that the method annotated with @HystrixCommand will go to Fallback if not returned in the specified time in ms
        ConfigurationManager.getConfigInstance().addProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 90 * 1000);
        ConfigurationManager.getConfigInstance().addProperty("hystrix.threadpool.default.maxQueueSize", 10000);
        ConfigurationManager.getConfigInstance().addProperty("hystrix.threadpool.default.queueSizeRejectionThreshold", 5000);

    }

    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

}

package com.example.demospringcloudkafka.services;

import com.example.demospringcloudkafka.entities.PageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {
    private static final Logger logger = LoggerFactory.getLogger(PageEventService.class);
    @Bean
    public Consumer<PageEvent> pageEventConsumer() {
        logger.info("Consumer bean created and ready to consume messages from Kafka");
        return (pageEvent) -> {
            System.out.println("*** PageEventService ***");
            System.out.println("PageEvent received: " + pageEvent);
            System.out.println("*** PageEventService ***");
        };
    }

    @Bean
    public Supplier<PageEvent> pageEventSupplier() {
        return () -> {
            PageEvent pageEvent = new PageEvent("pageEvent", "anas", new java.util.Date(), new Random().nextInt(9000));
            logger.info("PageEvent generated: " + pageEvent);
            return pageEvent;
        };
    }

    @Bean
    public Function<PageEvent,PageEvent> pageEventFunction() {
        return (pageEvent) -> {
            pageEvent.setName(pageEvent.getName().toUpperCase());
            logger.info("PageEvent processed: " + pageEvent);
            return pageEvent;
        };
    }
}

package com.example.demospringcloudkafka.services;

import com.example.demospringcloudkafka.entities.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
            PageEvent pageEvent = new PageEvent(Math.random()>0.5?"P1":"P2", "anas", new java.util.Date(), new Random().nextInt(9000));
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

    @Bean
    public Function<KStream<String,PageEvent>,KStream<String,Long>> kStreamFunction() {
        return (kStream) -> {
            return kStream.filter((k,v)->v.getDuration()>10)
                    .map((k,v)->new KeyValue<>(v.getName(),0L))
                    .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Long()))
                    .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
                    .count(Materialized.as("page-count"))
                    .toStream()
                    .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime()+k.key(),v))
                    ;
        };

    }
}

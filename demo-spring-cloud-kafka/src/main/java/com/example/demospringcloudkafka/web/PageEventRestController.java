package com.example.demospringcloudkafka.web;

import com.example.demospringcloudkafka.entities.PageEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class PageEventRestController {

    private StreamBridge streamBridge;

    public PageEventRestController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @GetMapping("/publish/{topic}/{name}")
    public PageEvent generatePageEvent(@PathVariable String topic, @PathVariable  String name) {
        PageEvent pageEvent = new PageEvent(name ,Math.random()>0.5 ? "anas":"yassin" , new Date(), new Random().nextInt(1000));
        streamBridge.send(topic, pageEvent);
        return pageEvent;
    }
}

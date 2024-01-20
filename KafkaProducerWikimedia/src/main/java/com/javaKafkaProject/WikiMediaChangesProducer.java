package com.javaKafkaProject;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikiMediaChangesProducer {

    private static final Logger logger= LoggerFactory.getLogger(WikiMediaChangesProducer.class);

    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String valueName;

    public WikiMediaChangesProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        // to read real time stream data from wikimedia , we  use event source

        EventHandler eventHandler=new WikiMediaChangesHandler(kafkaTemplate,valueName);
        String url= "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder= new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource=builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(10);


    }


}

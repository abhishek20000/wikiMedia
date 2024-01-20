package com.javaKafkaProject;

import com.javaKafkaProject.entity.WikiMediaData;
import com.javaKafkaProject.repository.WikiMediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataBaseConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaDataBaseConsumer.class);

    private WikiMediaRepository wikiMediaRepository;

    public KafkaDataBaseConsumer(WikiMediaRepository wikiMediaRepository) {
        this.wikiMediaRepository = wikiMediaRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}" ,groupId="${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage){
        LOGGER.info(String.format("eventMessage recived -> %s",eventMessage));

        WikiMediaData wikiMediaData=new WikiMediaData();
        wikiMediaData.setWikiEventData(eventMessage);
        wikiMediaRepository.save(wikiMediaData);
    }
}

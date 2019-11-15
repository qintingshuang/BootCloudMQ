package com.example.kafka.cosumer;

import com.example.kafka.model.Mail;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 16:30
 */
@Slf4j
@Component
public class SimpleCosumer {
    private final Gson gson = new Gson();

    @KafkaListener(topics = "${kafka.topic.defalut}", containerFactory = "kafkaListenerContainerFactory")
    public void receive(Mail mail) {
        log.info(gson.toJson(mail));
    }
}

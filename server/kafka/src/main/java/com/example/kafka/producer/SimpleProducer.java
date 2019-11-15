package com.example.kafka.producer;

import com.example.kafka.model.Mail;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 15:56
 */
@Component
public class SimpleProducer {

    @Autowired
    @Qualifier("kafkaTemplate")
    KafkaTemplate<String, Mail> kafkaTemplate;


    public void send(String topic, Mail mail) {
        kafkaTemplate.send(topic, mail);
    }

    public void send(String key, String topic, Mail mail) {
        ProducerRecord<String, Mail> record = new ProducerRecord<>(
                topic,
                key,
                mail
        );
        long startTime = System.currentTimeMillis();

        ListenableFuture<SendResult<String, Mail>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallBack(startTime, key, mail));
    }

}

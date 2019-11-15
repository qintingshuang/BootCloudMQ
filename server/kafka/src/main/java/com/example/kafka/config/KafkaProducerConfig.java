package com.example.kafka.config;

import com.example.kafka.model.Mail;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 15:25
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("kafka.producer.servers")
    private String servers;

    @Value("kafka.producer.retries")
    private String retries;

    @Value("kafka.producer.batch.size")
    private String batchSize;

    @Value("kafka.producer.buffer.memory")
    private String bufferMemory;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        map.put(ProducerConfig.RETRIES_CONFIG, retries);
        map.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        map.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return map;
    }

    public ProducerFactory<String, Mail> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                producerConfigs(),
                new StringSerializer(),
                new JsonSerializer<Mail>()
        );
    }

    @Bean
    public KafkaTemplate<String, Mail> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }



}

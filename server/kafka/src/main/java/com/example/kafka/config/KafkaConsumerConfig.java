package com.example.kafka.config;

import com.example.kafka.model.Mail;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 13:02
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("kafka.consumer.server")
    private String server;

    @Value("kafka.consumer.enable.auto.commit")
    private boolean enableAutoCommit;

    @Value("kafka.consumer.session.timeout")
    private String sessionTimeout;

    @Value("kafka.consumer.enable.auto.commit.interval")
    private String commitInterval;

    @Value("kafka.consumer.auto.offset.reset")
    private String autoOffetReset;

    @Value("kafka.consumer.concurrency")
    private int concurrenty;


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Mail>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Mail> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerfactory());
        factory.setConcurrency(concurrenty);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }


    private ConsumerFactory<String, Mail> consumerfactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(Mail.class)
        );
    }


    private Map<String, Object> consumerConfigs() {
        Map<String, Object> map = new HashMap();

        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, commitInterval);
        map.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffetReset);

        return map;
    }


}

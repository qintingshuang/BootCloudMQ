package springcloud.producer.point.config;


import io.swagger.models.auth.In;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-07-30 21:38
 */
@Component
@Configuration
@EnableRabbit
@PropertySource(value = {"classpath:rabbitmq.properties"})
public class RabbitmqConfig {

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private Integer port;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${rabbitmq.listener.simple.concurrency}")
    private String concurrency;

    @Value("${rabbitmq.listener.simple.max-concurrency}")
    private String maxConcurrency;

    /**
     * 连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory conn = new CachingConnectionFactory(this.host);
        conn.setUsername(this.username);
        conn.setPassword(this.password);
        conn.setPort(this.port);
        conn.setVirtualHost(this.virtualHost);
        return conn;
    }


    /**
     * RabbitAdmin
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    /**
     * rabbit模板
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * 配置消费者监听的容器
     *
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(Integer.valueOf(this.concurrency));
        factory.setMaxConcurrentConsumers(Integer.valueOf(this.maxConcurrency));
        return factory;
    }


    /**
     * 消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }


}

package springcloud.producer.point.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import springcloud.producer.point.model.Mail;

import java.io.IOException;

/**
 * @description:消费者
 * @author: qintingshuang
 * @date: 2019-08-04 20:40
 */
@Slf4j
@Component
public class ConsumerListener {

    @RabbitListener(queues = "topicqueue1")
    public void displayMail(Mail mail, Channel channel, Message message) throws IOException {
        log.info("对列监听器，获取的用户名为： " + mail.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("手动确认消费成功");
    }
}

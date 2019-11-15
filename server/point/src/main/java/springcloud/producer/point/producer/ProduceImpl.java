package springcloud.producer.point.producer;


import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloud.producer.point.model.Mail;

/**
 * @description:生产者
 * @author: qintingshuang
 * @date: 2019-07-31 23:02
 */
@Transactional
@Service
public class ProduceImpl {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMail(Mail mail, String routingKey) {
        rabbitTemplate.convertAndSend("qinTopic", routingKey, mail);
    }


}

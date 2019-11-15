package springcloud.producer.point.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 通配符模式 需要一个topic交换机，两个队列，三个binding
 * topicExchange 即通配符模式配置
 * 其中的directExchange 为路由模式
 * FanoutExchange 为订阅分发模式
 * @author: qintingshuang
 * @date: 2019-07-31 20:39
 */
@Configuration
public class TopicExchangeConfig {

    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange("qinTopic");
        return topicExchange;
    }

    @Bean
    public Queue topicQueue1() {
        Queue queue = new Queue("topicqueue1");
        return queue;
    }

    @Bean
    public Queue topicQueue2() {
        Queue queue = new Queue("topicqueue2");
        return queue;
    }

    /**
     * 绑定 交换机与队列，并且规定队列中的通配符数据规则
     * *为单个关键字  #为0个或若干个关键字
     * @return
     */
    @Bean
    public Binding bindingTopic1() {
        Binding binding = BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("#.hongkong"); //binding key
        return binding;
    }

    @Bean
    public Binding bindingTopic2(){
        Binding binding=BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("*.*.致");
        return  binding;

    }

    @Bean
    public Binding bindingTopic3(){
        Binding binding =BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("张.#");
        return binding;
    }
}

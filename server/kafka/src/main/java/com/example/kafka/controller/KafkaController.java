package com.example.kafka.controller;

import com.example.kafka.common.ApiResult;
import com.example.kafka.model.Mail;
import com.example.kafka.producer.SimpleProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 16:38
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    SimpleProducer simpleProducer;

    @Value("${kafka.topic.defalut}")
    private String topic;

    @GetMapping("/send")
    public ApiResult sendKafka() {

        Mail mail = new Mail();
        mail.setMailName("qintingshuang@foxmail.com");
        mail.setMailtopic("招聘");
        mail.setMessage("java工程师");
        ApiResult apiResult = new ApiResult();
        try {
            simpleProducer.send("key", topic, mail);
            apiResult.setData(mail);
            log.info("成功！！！！");
        } catch (Exception e) {
            apiResult.setCode(500);
            apiResult.setMessage("fail");
        }
        return apiResult;
    }

}

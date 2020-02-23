package springcloud.producer.point.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloud.producer.point.model.Mail;
import springcloud.producer.point.producer.ProduceImpl;

import java.util.HashMap;

/**
 * @description 测试rabbit
 * @author qintingshuang
 * @date 2019-08-04 21:06
 */
@Slf4j
@RestController
@Api(tags = "测试rabbit")
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    ProduceImpl producer;

    @ApiOperation("模拟发送信息")
    @GetMapping("/produce")
    public void produce() {
        HashMap<String, String> result = new HashMap();
        Mail mail = new Mail();
        mail.setCountry("china");
        mail.setMailId(1);
        mail.setMessage("rabbit测试成功");
        mail.setUserName("王大拿");
        mail.setMailName("wangdana@foxmail.com");
        producer.sendMail(mail, "china.hongkong");
        log.info("send rabbtimq message success!");
        // return result;
    }


}

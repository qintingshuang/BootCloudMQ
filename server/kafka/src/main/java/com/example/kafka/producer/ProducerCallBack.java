package com.example.kafka.producer;

import com.example.kafka.model.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 16:09
 */
@Slf4j
public class ProducerCallBack implements ListenableFutureCallback<SendResult<String, Mail>> {

    private final long startTime;
    private final String key;
    private final Mail mail;

    public ProducerCallBack(long startTime, String key, Mail mail) {
        this.startTime = startTime;
        this.key = key;
        this.mail = mail;
    }


    @Override
    public void onFailure(Throwable ex) {
        ex.printStackTrace();
    }

 
    @Override
    public void onSuccess(@Nullable SendResult<String, Mail> stringMailSendResult) {

        if (stringMailSendResult == null) {
            return;
        }

        Long elansedTime = System.currentTimeMillis() - startTime;

        RecordMetadata metadata = stringMailSendResult.getRecordMetadata();
        if (metadata != null) {
            StringBuffer record = new StringBuffer();
            record.append("message=").append(mail.toString());
            record.append("with ").append((metadata.partition()));
            log.info(elansedTime.toString() + record.toString());
        }


    }
}

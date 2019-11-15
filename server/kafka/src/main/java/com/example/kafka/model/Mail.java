package com.example.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 13:00
 */
@Data
@NoArgsConstructor
public class Mail {

    private String MailName;

    private String Mailtopic;

    private String message;
}

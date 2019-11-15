package springcloud.producer.point.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-07-31 23:03
 */
@Data
@ApiModel("mail")
public class Mail implements Serializable {

    private Integer mailId;

    private  String mailName;

    private String userName;

    private String message;

    private  String  country;

}

package com.example.kafka.common;

import lombok.Data;

/**
 * @description:
 * @author: qintingshuang
 * @date: 2019-08-25 12:52
 */

@Data
public class ApiResult<T> {

    T data;

    private String message = "success";

    private Integer code = 200;

    public ApiResult() {

    }
    public ApiResult(T data) {
        this.data = data;
    }

}

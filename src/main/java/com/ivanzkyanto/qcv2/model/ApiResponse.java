package com.ivanzkyanto.qcv2.model;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private Integer code;

    private String status;

    private T data;

}

package io.github.stackpan.qurancord.model;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private Integer code;

    private String status;

    private T data;

}

package com.example.azure_login.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private Object data;
}

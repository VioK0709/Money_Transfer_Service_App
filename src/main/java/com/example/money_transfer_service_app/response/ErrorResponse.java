package com.example.money_transfer_service_app.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int id;
}
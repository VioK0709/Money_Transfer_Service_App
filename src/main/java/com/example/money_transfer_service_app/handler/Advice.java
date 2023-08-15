package com.example.money_transfer_service_app.handler;

import com.example.money_transfer_service_app.exception.ErrorConfirmation;
import com.example.money_transfer_service_app.exception.ErrorInputData;
import com.example.money_transfer_service_app.exception.ErrorTransfer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.money_transfer_service_app.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
@Slf4j
public class Advice {

    private final AtomicInteger idNumber = new AtomicInteger(1);

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorResponse> handlerErrorInputData(ErrorInputData e) {
        log.info(idNumber.getAndIncrement() + "Ошибка перевода " + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(e.getLocalizedMessage()).id(idNumber.getAndIncrement()).build());
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<ErrorResponse> handlerErrorTransfer(ErrorTransfer e) {
        log.info(idNumber.getAndIncrement() + "Ошибка перевода " + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ErrorResponse.builder()
                        .message(e.getLocalizedMessage()).id(idNumber.getAndIncrement()).build());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handlerErrorConfirmation(ErrorConfirmation e) {
        log.info(idNumber.getAndIncrement() + "Ошибка подтверждения " + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .message(e.getLocalizedMessage()).id(idNumber.getAndIncrement()).build());
    }
}
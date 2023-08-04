package com.example.money_transfer_service_app.handler;

import com.example.money_transfer_service_app.exception.ErrorConfirmation;
import com.example.money_transfer_service_app.exception.ErrorInputData;
import com.example.money_transfer_service_app.exception.ErrorTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestControllerAdvice
public class Advice {

    private final Logger log = Logger.getLogger("Advice_Logger");

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<Map<String, String>> handlerErrorInputData(ErrorInputData e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Ошибка ввода данных", e.getLocalizedMessage());
        log.info(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<Map<String, String>> handlerErrorTransfer(ErrorTransfer e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Ошибка перевода", e.getLocalizedMessage());
        log.info(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errorMap);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<Map<String, String>> handlerErrorConfirmation(ErrorConfirmation e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Ошибка подтверждения", e.getLocalizedMessage());
        log.info(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
    }
}
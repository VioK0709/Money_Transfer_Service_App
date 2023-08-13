package com.example.money_transfer_service_app.controller;

import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RequiredArgsConstructor
@CrossOrigin(origins = "${client_url}")
@RestController
public class TransferController {

    private final TransferService transferService;

    private final Logger log = Logger.getLogger("Controller_Success");

    @PostMapping("/transfer")
    public ResponseEntity postTransfer(@RequestBody DataTransfer transfer) {
        return ResponseEntity.ok(transferService.postTransfer(transfer));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity confirmation(@RequestBody Verification verification) {
        log.info("Транзакция успешно проведена");
        return ResponseEntity.ok(verification);
    }
}

record Verification(String operationId, String code) {
    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 1);
    }
}

package com.example.money_transfer_service_app.controller;

import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
import com.example.money_transfer_service_app.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "${client_url}")
@RestController
@Slf4j
public class TransferController {

    private final TransferService transferService;

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
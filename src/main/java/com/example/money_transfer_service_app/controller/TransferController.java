package com.example.money_transfer_service_app.controller;

import com.example.money_transfer_service_app.model.IdOperation;
import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
import com.example.money_transfer_service_app.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<IdOperation> confirmation(@RequestBody Verification verification) {
        String operationId = transferService.confirmOperation(verification);
        log.info("Транзакция успешно проведена");
        return new ResponseEntity<>(new IdOperation(operationId), HttpStatus.OK);
    }
}

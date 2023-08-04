package com.example.money_transfer_service_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class IdOperation {

    private String idOperation;
}

record IdOperationRecord(String idOperation) {
}
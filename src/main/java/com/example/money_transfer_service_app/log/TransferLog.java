package com.example.money_transfer_service_app.log;

import com.example.money_transfer_service_app.model.DataOperation;

import static com.example.money_transfer_service_app.constant.ConstantApplication.LOG_INFORMATION;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferLog {

    public static String createStringLog(String operationId, DataOperation dataOperation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String format = String.format(LOG_INFORMATION, operationId, LocalDateTime.now().format(formatter),
                dataOperation.getCardFromNumber(), dataOperation.getCardToNumber(),
                dataOperation.getTransferValue(), dataOperation.getTransferFee(), dataOperation.getRemains());
        try (FileWriter writerLogs = new FileWriter("FileLog", true)) {
            writerLogs.write(format);
            return format;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
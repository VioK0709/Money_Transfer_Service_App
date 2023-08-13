package com.example.money_transfer_service_app.log;

import com.example.money_transfer_service_app.model.DataOperation;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferLog {

    public static String createStringLog(String operationId, DataOperation dataOperation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String text = "\nОперация перевод:\n"
                + "Номер операции-> " + operationId + ";\n"
                + "время операции-> " + LocalDateTime.now().format(formatter) + ";\n"
                + "карта отправителя-> " + dataOperation.getCardFromNumber() + ";\n"
                + "карта получателя-> " + dataOperation.getCardToNumber() + ";\n"
                + "сумма перевода в валюте карты отправителя-> " + dataOperation.getTransferValue() + ";\n"
                + "комиссия-> " + dataOperation.getTransferFee() + ";\n"
                + "результат операции-> Транзакция успешно проведена;\n"
                + "остаток на карте отправителя-> " + dataOperation.getRemains() + ".\n";
        createFile(text);
        return text;
    }

    public static String createFile(String text) {
        try (FileWriter writerLogs = new FileWriter("FileLog", true)) {
            writerLogs.write(text);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
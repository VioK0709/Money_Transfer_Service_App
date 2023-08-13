package com.example.money_transfer_service_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import com.example.money_transfer_service_app.model.Card;
import com.example.money_transfer_service_app.repository.TransferRepositoryImpl;

import java.util.Map;

@SpringBootApplication
@RestController
public class MoneyTransferServiceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferServiceAppApplication.class, args);
        System.out.println("Запущен сервис перевода денег!");
        TransferRepositoryImpl.addCard();
        printRepository();
    }

    public static void printRepository() {
        System.out.println("Репозиторий банковских карт:");
        for (Map.Entry<String, Card> cardRepository : TransferRepositoryImpl.cards.entrySet()) {
            System.out.println("Данные карты: " + cardRepository.getValue().printData() + ".");
        }
    }
}
package com.example.money_transfer_service_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceAppApplicationTests {

    private final String HOST = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> backApp = new GenericContainer<>("back-transfer")
            .withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        backApp.start();
    }

    @AfterAll
    public static void setDown() {
        backApp.stop();
    }

    @Test
    void contextBackApp() {
        var portBack = backApp.getMappedPort(5500);
        ResponseEntity<String> forEntityBackApp = restTemplate.getForEntity(HOST + portBack + "/profile", String.class);
        System.out.println("Port MoneyTransferService: " + portBack);
        System.out.println(forEntityBackApp.getBody());
        Assertions.assertTrue(backApp.isRunning());
    }
}
FROM openjdk:17-jdk-slim

EXPOSE 5500

ADD target/MoneyTransferService-0.0.1-SNAPSHOT.jar money_transfer_service.jar

ENTRYPOINT ["java", "-jar", "/money_transfer_service.jar"]
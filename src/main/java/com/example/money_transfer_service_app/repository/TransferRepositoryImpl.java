package com.example.money_transfer_service_app.repository;


import com.example.money_transfer_service_app.exception.ErrorInputData;
import com.example.money_transfer_service_app.model.AmountCard;
import com.example.money_transfer_service_app.model.Card;
import com.example.money_transfer_service_app.model.DataOperation;
import com.example.money_transfer_service_app.model.DataTransfer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransferRepositoryImpl implements TransferRepository {

    public static Map<String, Card> cards = new ConcurrentHashMap<>();

    public TransferRepositoryImpl(Map<String, Card> cardsRepository) {
        TransferRepositoryImpl.cards = cardsRepository;
    }

    public static void addCard() {
        cards.put("7380000000000010",
                new Card("7380000000000010", "01/24", "123",
                        new AmountCard(BigDecimal.valueOf(111_111.11), "RUB")));
        cards.put("7380000000000011",
                new Card("7380000000000011", "02/25", "456",
                        new AmountCard(BigDecimal.valueOf(111.11), "RUB")));
        cards.put("7380000000000012",
                new Card("7380000000000012", "03/26", "789",
                        new AmountCard(BigDecimal.valueOf(111_111_111.11), "RUB")));
    }

    @Override
    public DataOperation transfer(DataTransfer transferRequest) {
        Card currentCard;
        DataOperation newOperation = null;
        for (Map.Entry<String, Card> cardRepoEntry : cards.entrySet()) {
            if (transferRequest.getCardFromNumber().equals(cardRepoEntry.getKey())) {
                currentCard = cardRepoEntry.getValue();
                newOperation = acceptData(currentCard, transferRequest);
            }
        }
        return newOperation;
    }

    @Override
    public boolean confirmOperation(String operationId, DataOperation currentDataOperation) {
        if (operationId != null) {
            String cardFromNumber = currentDataOperation.getCardFromNumber().getCardFromNumber();
            String cardToNumber = currentDataOperation.getCardToNumber();

            Card currentCard = currentDataOperation.getCardFromNumber();
            BigDecimal newValueCardFrom = currentDataOperation.getRemains();
            currentCard.setAmount(new AmountCard(newValueCardFrom, "RUB"));
            cards.put(cardFromNumber, currentCard);
            if (cards.containsKey(cardToNumber)) {
                Card cardTo = cards.get(cardToNumber);
                BigDecimal valueCardTo = cardTo.getAmount().getValue();
                BigDecimal transferValue = currentDataOperation.getTransferValue();
                BigDecimal newValueCardTo = valueCardTo.add(transferValue)
                        .setScale(2, RoundingMode.CEILING);
                cardTo.setAmount(new AmountCard(newValueCardTo, "RUB"));
                cards.put(cardToNumber, cardTo);
            }
            return true;
        }
        return false;
    }

    public static DataOperation acceptData(Card currentCard, DataTransfer transferRequest) {
        DataOperation newOperation;
        String cardToNumber = transferRequest.getCardToNumber();
        if (!(currentCard.getCardFromNumber().equals(cardToNumber))
                && (currentCard.getCardFromCVV().equals(transferRequest.getCardFromCVV()))
                && (currentCard.getCardFromValidTill().equals(transferRequest.getCardFromValidTill()))) {
            BigDecimal currentCardValue = currentCard.getAmount().getValue()
                    .setScale(2, RoundingMode.CEILING);
            BigDecimal transferValue = BigDecimal.valueOf(transferRequest.getAmount().getValue() / 100)
                    .setScale(2, RoundingMode.CEILING);
            BigDecimal sum = transferValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.CEILING);
            BigDecimal newValueCardFrom = currentCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01)))
                    .setScale(2, RoundingMode.CEILING);
            if (newValueCardFrom.compareTo(BigDecimal.valueOf(0.01)
                    .setScale(2, RoundingMode.CEILING)) > 0) {
                newOperation = new DataOperation(currentCard, cardToNumber, transferValue, newValueCardFrom, sum);
                return newOperation;
            } else {
                throw new ErrorInputData("Недостаточно средств");
            }
        } else {
            throw new ErrorInputData("Ошибка ввода данных");
        }
    }

    public static boolean validateCardDate(String cardValid) {
        Date cardDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("MM/yy");
        try {
            cardDate = dateFormat.parse(cardValid);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date todayDate = new Date();
        if (cardDate != null) {
            long diffDate = cardDate.getTime() - todayDate.getTime();
            int month = Integer.parseInt(cardValid.substring(0, 2));
            return ((diffDate >= 0) && (month > 0) & (month < 13));
        }
        return false;
    }
}
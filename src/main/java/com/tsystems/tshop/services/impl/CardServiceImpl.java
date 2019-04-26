package com.tsystems.tshop.services.impl;

import com.tsystems.tshop.domain.Card;
import com.tsystems.tshop.repositories.CardRepository;
import com.tsystems.tshop.components.CardConfirmationCodeStorage;
import com.tsystems.tshop.services.CardService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CardServiceImpl implements CardService {

    // private Card card;
    private final CardRepository cardRepository;
    private final CardConfirmationCodeStorage cardStorage;
    private final ConfirmationSendingService confirmationSendingService;

    public CardServiceImpl(CardRepository cardRepository,
                           CardConfirmationCodeStorage storage,
                           ConfirmationSendingService confirmationSendingService){
        this.cardRepository = cardRepository;
        this.cardStorage = storage;
        this.confirmationSendingService = confirmationSendingService;
    }

    @Override
    public Card getCardByQuery(Card card) {
        Card c = cardRepository.getCardByQuery(card);
        c.setOrdersum(card.getOrdersum());
        if (c.getOrdersum()<=c.getBalance()){
            try {
                cardStorage.put(confirmationSendingService.sendConfirmCode(), c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    @Override
    public void paymentOff(Card card) {
        Card c = cardStorage.get(String.valueOf(card.getConfirmcode()));
        cardRepository.paymentOff(c);
    }
}

package com.tsystems.tshop.components;

import com.tsystems.tshop.domain.Card;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
//@Scope("session")
public class CardConfirmationCodeStorage {

    private final Map<String, Card> storage = new ConcurrentHashMap<>();

    public void put(String confirmationCode, Card card) {
        storage.put(confirmationCode, card);
    }

    public Card get(String confirmationCode) {
        return storage.remove(confirmationCode);
    }

}

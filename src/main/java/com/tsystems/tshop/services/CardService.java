package com.tsystems.tshop.services;

import com.tsystems.tshop.domain.Card;

public interface CardService {

    Card getCardByQuery(Card card);
    void paymentOff(Card card);
}

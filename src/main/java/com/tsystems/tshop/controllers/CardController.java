package com.tsystems.tshop.controllers;


import com.tsystems.tshop.domain.Card;
import com.tsystems.tshop.services.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/cardreq")
public class CardController {

    private final CardService cardService;

    public CardController(final CardService cardService){
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void sendConfirmCode(@RequestBody Card card){
        this.cardService.getCardByQuery(card);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/{confirmcode}")
	@ResponseBody
    public Boolean paymentOff(@PathVariable String confirmcode){
        Card card = new Card();
        card.setConfirmcode(confirmcode);
        this.cardService.paymentOff(card);
        return true;
    }
}

package com.tsystems.tshop.repositories;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.tshop.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@PropertySource("classpath:queries/card-queries.xml")
@Repository
public class CardRepository {

    private Environment env;

    private static final String GET_CARD_BY_QUERY = "getCardByQuery";
    private static final String GET_PAYMENT_OFF_QUERY = "paymentOff";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CardRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          Environment env){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.env = env;
    }

    public Card getCardByQuery(Card card){
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("cardsnumber", card.getCardsnumber())
                .addValue("cardholder", card.getCardholder())
                .addValue("cvv", card.getCvv());
        return namedParameterJdbcTemplate.queryForObject(env.getProperty(GET_CARD_BY_QUERY),sqlParameterSource,
                BeanPropertyRowMapper.newInstance(Card.class));
    }

    public void paymentOff(Card card){
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("idcards", card.getIdcards())
                .addValue("balance", card.getBalance()-card.getOrdersum());
        namedParameterJdbcTemplate.update(env.getProperty(GET_PAYMENT_OFF_QUERY),sqlParameterSource);
    }

}

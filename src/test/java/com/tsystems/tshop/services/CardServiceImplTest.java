package com.tsystems.tshop.services;

import com.tsystems.tshop.components.CardConfirmationCodeStorage;
import com.tsystems.tshop.domain.Card;
import com.tsystems.tshop.repositories.CardRepository;
import com.tsystems.tshop.services.impl.CardServiceImpl;
import com.tsystems.tshop.services.impl.ConfirmationSendingService;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class CardServiceImplTest {

    @Test
    public void testIfCardIsReturnedSuccessfully() {

        final CardConfirmationCodeStorage storage = new CardConfirmationCodeStorage();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);

        Card card = new Card();
        card.setOrdersum(100);
        card.setBalance(500);

        when(namedParameterJdbcTemplate.queryForObject(any(), any(SqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(card);

        CardRepository cardRepository = new CardRepository(namedParameterJdbcTemplate,
                mock(Environment.class));
        CardService cardService = new CardServiceImpl(cardRepository, storage, mock(ConfirmationSendingService.class));
        Card c = cardService.getCardByQuery(card);

        assertThat(c, is(card));
    }

}

package com.tsystems.tshop.repositories;

import com.tsystems.tshop.domain.Order;
import com.tsystems.tshop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@PropertySource("classpath:queries/order-queries.xml")
@Repository
public class OrderRepository {

    @Autowired
    private Environment env;

    private static final String WRITE_ORDER_QUERY = "writeOrder";
    private static final String GET_CLIENT_ORDER_NUMBER_QUERY = "getClientOrderNumber";
    private static final String CHANGE_ORDER_STATUS_QUERY = "changeDeliveryStat";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void writeOrder(final Integer client, final Product[] product){
        final SqlParameterSource sqlParameterSourceOrdernumber = new MapSqlParameterSource()
                .addValue("client", client);
        Integer ordernumber = namedParameterJdbcTemplate.queryForObject(env.getProperty(GET_CLIENT_ORDER_NUMBER_QUERY),sqlParameterSourceOrdernumber,
                Integer.class) + 1;
        for (Product product1: product){
            final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("client", client).addValue("product", product1.getIdgoods())
                    .addValue("ordernumber", ordernumber).addValue("count", product1.getCount())
                    .addValue("iscash", true).addValue("delivery", false)
                    .addValue("ispaid", true);
            namedParameterJdbcTemplate.update(env.getProperty(WRITE_ORDER_QUERY),sqlParameterSource);
        }
    }

    public void changeDeliveryStat(final Order order){
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("client", order.getClient_Id()).addValue("ordernumber",order.getOrdernumber());
        namedParameterJdbcTemplate.update(env.getProperty(CHANGE_ORDER_STATUS_QUERY),sqlParameterSource);
    }
}

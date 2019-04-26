package com.tsystems.tshop.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.tsystems.tshop.domain.Product;

@PropertySource("classpath:queries/product-queries.xml")
@Repository
public class ProductRepository {
	
	private static final String GET_PRODUCT_BY_ID_QUERY = "getProductById";
	private static final String GET_ALL_PRODUCTS_QUERY = "getAllProducts";
	private static final String GET_TOTAL_SALED_PRODUCTS_QUERY = "getAllSaledProducts";
	private static final String WRITE_NEW_PRODUCT_QUERY = "writeNewProduct";
	private static final String CHANGE_INSTOCK_QUERY = "changeStock";
	
	@Autowired
	private Environment env;
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ProductRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public Product getProductById(final Integer id) {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("id", id);
		return namedParameterJdbcTemplate.queryForObject(env.getProperty(GET_PRODUCT_BY_ID_QUERY),
				sqlParameterSource, BeanPropertyRowMapper.newInstance(Product.class));
	}
	
	public List<Product> getProducts() {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		return namedParameterJdbcTemplate.query(env.getProperty(GET_ALL_PRODUCTS_QUERY),
				sqlParameterSource, BeanPropertyRowMapper.newInstance(Product.class));
	}

	public List<Product> getTotalSaledProducts() {
		final SqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		List<Product> products = namedParameterJdbcTemplate.query(env.getProperty(GET_TOTAL_SALED_PRODUCTS_QUERY),
				sqlParameterSource, BeanPropertyRowMapper.newInstance(Product.class));
		ArrayList<Product> productsFinal = new ArrayList<>();
		for (Product product: products){
			System.out.println("=================" + product.getIdgoods());
			Product productFinal = getProductById(product.getIdgoods());
			productFinal.setTotalsaled(product.getTotalsaled());
			productsFinal.add(productFinal);
		}
		return productsFinal;
	}

	public void writeNewProducts(Product[] products){
		for (Product product: products){
			final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
					.addValue("goodsname", product.getGoodsname())
					.addValue("price", product.getPrice())
					.addValue("weight", product.getWeight())
					.addValue("volume", product.getVolume())
					.addValue("instock", product.getInstock())
					.addValue("goodsparameters", product.getGoodsparameters());
			namedParameterJdbcTemplate.update(env.getProperty(WRITE_NEW_PRODUCT_QUERY),sqlParameterSource);
		}
	}

	public void changeStock(Product[] products){
		for (Product product: products){
			final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
					.addValue("idgoods", product.getIdgoods())
					.addValue("countsaled", product.getCount());
			namedParameterJdbcTemplate.update(env.getProperty(CHANGE_INSTOCK_QUERY), sqlParameterSource);
		}

	}
}

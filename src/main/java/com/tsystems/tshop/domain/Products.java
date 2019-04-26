package com.tsystems.tshop.domain;

public class Products {

        private Product[] products;

        public Products(){}

        public Products(final Product[] products){
            super();
            this.products = products;
        }

        public Product[] getProducts() {
            return products;
        }

        public void setProducts(Product[] products) {
            this.products = products;
        }

}

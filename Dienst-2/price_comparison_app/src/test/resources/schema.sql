CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(255) NOT NULL,
                          description VARCHAR(500)
);

CREATE TABLE supermarkets (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              location VARCHAR(255) NOT NULL
);

CREATE TABLE prices (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        product_id BIGINT NOT NULL,
                        supermarket_id BIGINT NOT NULL,
                        regular_price DECIMAL(10,2) NOT NULL,
                        discount_price DECIMAL(10,2),
                        discount_valid_from DATE,
                        discount_valid_to DATE,
                        FOREIGN KEY (product_id) REFERENCES products(id),
                        FOREIGN KEY (supermarket_id) REFERENCES supermarkets(id)
);

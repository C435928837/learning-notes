CREATE TABLE product_stock (
                               product_id VARCHAR(20) PRIMARY KEY,
                               stock INT NOT NULL
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        product_id VARCHAR(20) NOT NULL
);

CREATE TABLE products (
                          product_code VARCHAR(20) PRIMARY KEY,
                          product_name VARCHAR(50) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL
);

INSERT INTO product_stock(product_id, stock) VALUES ('P001', 10);
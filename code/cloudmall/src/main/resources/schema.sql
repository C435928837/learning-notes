CREATE TABLE product_stock (
                               product_id VARCHAR(20) PRIMARY KEY,
                               stock INT NOT NULL
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        product_id VARCHAR(20) NOT NULL
);

INSERT INTO product_stock(product_id, stock) VALUES ('P001', 10);
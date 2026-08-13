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

CREATE TABLE user_orders (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_no VARCHAR(32) NOT NULL,
                             user_id BIGINT NOT NULL,
                             status VARCHAR(20) NOT NULL,
                             created_at TIMESTAMP NOT NULL,
                             amount DECIMAL(10, 2) NOT NULL
);

CREATE UNIQUE INDEX uk_user_orders_order_no
    ON user_orders(order_no);

CREATE INDEX idx_orders_user_status_created
    ON user_orders(user_id, status, created_at);

INSERT INTO user_orders(order_no, user_id, status, created_at, amount)
VALUES
    ('O001', 1001, 'PAID',      '2026-08-10 10:00:00', 99.00),
    ('O002', 1001, 'PAID',      '2026-08-11 11:00:00', 199.00),
    ('O003', 1001, 'CANCELLED', '2026-08-12 12:00:00', 59.00),
    ('O004', 1002, 'PAID',      '2026-08-12 13:00:00', 88.00),
    ('O005', 1002, 'CREATED',   '2026-08-13 14:00:00', 129.00);

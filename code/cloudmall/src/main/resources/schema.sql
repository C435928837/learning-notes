CREATE TABLE products (
                          product_code VARCHAR(20) PRIMARY KEY,
                          product_name VARCHAR(50) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE product_stock (
                               product_id VARCHAR(20) PRIMARY KEY,
                               available_stock INT NOT NULL,
                               locked_stock INT NOT NULL DEFAULT 0
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_no VARCHAR(32) NOT NULL UNIQUE,
                        user_id BIGINT NOT NULL,
                        product_id VARCHAR(20) NOT NULL,
                        product_name VARCHAR(50) NOT NULL,
                        product_price DECIMAL(10, 2) NOT NULL,
                        quantity INT NOT NULL,
                        total_amount DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_orders_user_created_id
    ON orders(user_id, created_at, id);

INSERT INTO products(product_code, product_name, price)
VALUES ('P001', '机械键盘', 299.00);

INSERT INTO product_stock(product_id, available_stock, locked_stock)
VALUES
    ('P001', 10, 0),
    ('P002', 1, 0);

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

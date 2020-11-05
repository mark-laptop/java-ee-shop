CREATE TABLE IF NOT EXISTS Product
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(15, 2) NOT NULL
);

CREATE INDEX IX_Product_name ON Product (name);




CREATE TABLE IF NOT EXISTS Order
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(9) NOT NULL,
    date DATE NOT NULL,
    sum DECIMAL(15, 2)
);

CREATE INDEX IX_Order_number ON Order (number);
CREATE INDEX IX_Order_date ON Order (date);
CREATE INDEX IX_Order_sum ON Order (sum);




CREATE TABLE IF NOT EXISTS Order_item
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    qty DECIMAL(15, 3) NOT NULL,
    CONSTRAINT FK_Order_item_order_id FOREIGN KEY (order_id) REFERENCES Order (id),
    CONSTRAINT FK_Order_item_product_id FOREIGN KEY (product_id) REFERENCES Product (id)
);

CREATE INDEX IX_Order_item_order_id ON Order_item (order_id);
CREATE INDEX IX_Order_item_product_id ON Order_item (product_id);
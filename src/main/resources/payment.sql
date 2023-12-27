CREATE TABLE payment (
                            id BIGSERIAL PRIMARY KEY ,
                            customer_name VARCHAR(100) NOT NULL ,
                            payment_amount int NOT NULL,
                            payment_date timestamp
);
CREATE TABLE IF NOT EXISTS accounts (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        accountId VARCHAR(50) NOT NULL,
                                        amount DOUBLE NOT NULL,
                                        mcc VARCHAR(10) NOT NULL,
                                        merchant VARCHAR(100) NOT NULL
);
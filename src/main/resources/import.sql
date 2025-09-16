-- Buat tabel transaksi
CREATE TABLE IF NOT EXISTS transaction (
                                           id SERIAL PRIMARY KEY,
                                           user_id VARCHAR(50) NOT NULL,
                                           amount NUMERIC(15,2) NOT NULL,
                                           status VARCHAR(20) NOT NULL
);

-- Isi data dummy
-- INSERT INTO transaction (id, user_id, amount, status) VALUES (1, 'U001', 1000.00, 'APPROVED');
-- INSERT INTO transaction (id, user_id, amount, status) VALUES (2, 'U002', 250.50, 'REJECTED');
-- INSERT INTO transaction (id, user_id, amount, status) VALUES (3, 'U003', 780.00, 'PENDING');

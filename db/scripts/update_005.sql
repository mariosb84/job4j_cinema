
ALTER TABLE ticket ADD CONSTRAINT ticket_buy_error UNIQUE (session_id, pos_row, cell);
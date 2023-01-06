
ALTER TABLE tickets ADD CONSTRAINT ticket_buy_error UNIQUE (session_id, pos_row, cell);
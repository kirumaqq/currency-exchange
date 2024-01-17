CREATE TABLE public.exchange_rates
(
    id SERIAL PRIMARY KEY ,
    base_currency_id INT NOT NULL ,
    target_currency_id INT NOT NULL ,
    rate DECIMAL(12, 6) NOT NULL ,
    CONSTRAINT unique_pair UNIQUE (base_currency_id, target_currency_id),
    CONSTRAINT base_cur_fk FOREIGN KEY (base_currency_id) REFERENCES currencies (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION ,
    CONSTRAINT target_cur_fk FOREIGN KEY (base_currency_id) REFERENCES currencies (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
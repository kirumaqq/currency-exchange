CREATE TABLE public.currencies
(
    id SERIAL PRIMARY KEY,
    code VARCHAR NOT NULL ,
    full_name VARCHAR NOT NULL ,
    sign VARCHAR NOT NULL ,
    CONSTRAINT unique_code UNIQUE (code),
    CONSTRAINT iso4217_upper_case CHECK ( code = UPPER(code)),
    CONSTRAINT iso4217_length CHECK ( character_length(code) = 3)
);
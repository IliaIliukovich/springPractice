insert into client (id, email, first_name, last_name, tax_code, address, phone, status)
values ('123ABC','h.schmidt@example.com', 'Schmidt', 'Hans', 'DE123456789',  null, '+49 30 1234567', 'ACTIVE');


insert into account (id, name, type, status, balance, currency_code, client_id)
    values (1, 'Debit', 'CHECKING', 'ACTIVE', 15432, 'EUR', '123ABC'),
        (2, 'Credit', 'LOAN', 'BLOCKED', 8978, 'GBP', '123CDE'),
        (3, 'Deposit', 'DEBIT_CARD', 'INACTIVE',94787, 'USD', '543QWE');
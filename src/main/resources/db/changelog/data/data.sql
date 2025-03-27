-- liquibase formatted sql
-- changeset ilia.serhii:5

insert into	 client (id, last_name, first_name, tax_code, email, address, phone, status)
values  ('b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0','Schmidt', 'Hans', 'DE123456789', 'h.schmidt@example.com', 'Berlin, Germany', '+49 30 1234567', 'ACTIVE'),
        ('20980395-20d0-4ea8-8e4b-de2252a028eb', 'Müller', 'Anna', 'DE987654321', 'a.mueller@example.com', 'Munich, Germany', '+49 89 7654321', 'INACTIVE'),
        ('9cd1b6c5-06d7-4f6c-9959-3856b1b51045', 'Klein', 'Peter', 'DE567890123', 'p.klein@example.com', 'Hamburg, Germany', '+49 40 6789012', 'BLOCKED'),
        ('5e659d3c-3925-457d-ae41-ca69001fb11c', 'Schneider', 'Maria', 'DE456789012', 'm.schneider@example.com', 'Frankfurt, Germany', '+49 69 1234567', 'INACTIVE'),
        ('b03dbcfc-d047-49a7-acbb-f3b1329e1fee', 'Fischer', 'Lukas', 'DE234567890', 'l.fischer@example.com', 'Stuttgart, Germany', '+49 711 9876543', 'INACTIVE'),
        ('bb10eec7-e408-4f11-9603-ec3c4a371512', 'Weber', 'Sophie', 'DE890123456', 's.weber@example.com', 'Cologne, Germany', '+49 221 4567890', 'ACTIVE'),
        ('7d1388c1-04df-4473-b15b-fe614594aa3c', 'Meyer', 'Max', 'DE345678901', 'm.meyer@example.com', 'Düsseldorf, Germany', '+49 211 2345678', 'BLOCKED'),
        ('3d18c400-a7b3-4bb1-bb29-8f566a43eca1', 'Wagner', 'Julia', 'DE678901234', 'j.wagner@example.com', 'Dresden, Germany', '+49 351 8765432', 'ACTIVE'),
        ('d07ec73f-702a-479c-a930-f51596d9b899', 'Becker', 'Anna', 'DE789012345', 'p.becker@example.com', 'Leipzig, Germany', '+49 341 7654321', 'INACTIVE'),
        ('f6bc3ef4-4894-42f2-9c7b-66b9a8b80c7c', 'Hoffmann', 'Clara', 'DE012345678', 'c.hoffmann@example.com', 'Bremen, Germany', '+49 421 1234567', 'ACTIVE');

insert into account (id, name, type, status, balance, currency_code, client_id)
    values (1, 'Debit', 'CHECKING', 'ACTIVE', 1000, 'EUR', 'b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0'),
        (2, 'Deposit', 'LOAN', 'ACTIVE', 2000, 'CNY', '20980395-20d0-4ea8-8e4b-de2252a028eb'),
        (3, 'Credit', 'LOAN', 'ACTIVE', 3000, 'JPY', '5e659d3c-3925-457d-ae41-ca69001fb11c'),
        (4, 'Debit', 'LOAN', 'BLOCKED', 4000, 'RUB', 'b03dbcfc-d047-49a7-acbb-f3b1329e1fee'),
        (5, 'Credit', 'LOAN', 'ACTIVE', 5000, 'GBP', 'bb10eec7-e408-4f11-9603-ec3c4a371512'),
        (6, 'Deposit', 'DEBIT_CARD', 'INACTIVE',3000, 'USD', '9cd1b6c5-06d7-4f6c-9959-3856b1b51045');

insert into transaction(id, type, amount, description, status, from_account_id, to_account_id, currency_code)
values ( 'f6bc3ef4-4894-42f2-9c7b-66b9a8b80e7c','CASH', 100,'Text', 'NEW', 1, 2, 'EUR' ),
 ('f2bc3ef4-4894-42f2-9c7b-66b9a8b80c7c','CASH', 233,'Text2', 'NEW', 2, 3, 'CNY'),
 ('f6bc5ef4-4894-42f2-9c7b-66b9a8b80c7c','CASH', 45343,'Text3', 'NEW', 3, 1, 'JPY');
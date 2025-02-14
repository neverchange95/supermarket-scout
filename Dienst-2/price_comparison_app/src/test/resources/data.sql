INSERT INTO products (id, name, category, description) VALUES (1, 'Vollmilch', 'Milchprodukte', 'Frische Vollmilch mit 1,5% Fettgehalt.');

INSERT INTO supermarkets (id, name, location) VALUES (1, 'Edeka', 'Berlin');
INSERT INTO supermarkets (id, name, location) VALUES (2, 'Rewe', 'Berlin');

INSERT INTO prices (id, product_id, supermarket_id, regular_price, discount_price, discount_valid_from, discount_valid_to)
VALUES (1, 1, 1, 1.50, 1.20, DATE '2025-01-01', DATE '2025-01-31');

INSERT INTO prices (id, product_id, supermarket_id, regular_price, discount_price, discount_valid_from, discount_valid_to)
VALUES (2, 1, 2, 1.55, NULL, NULL, NULL);

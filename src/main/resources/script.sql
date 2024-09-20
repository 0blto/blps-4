CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

INSERT INTO products (name, description) VALUES
                                             ('Vetements Oversized T-Shirt', 'Black oversized T-shirt with Vetements logo print'),
                                             ('Balenciaga Track Sneakers', 'Multicolor track sneakers with Balenciaga branding'),
                                             ('Carol Christian Poell Leather Jacket', 'Distressed black leather jacket with avant-garde design'),
                                             ('Rick Owens Geobasket Sneakers', 'High-top sneakers with signature Rick Owens silhouette'),
                                             ('Maison Margiela Tabi Boots', 'Split-toe leather boots in black with signature tabi design'),
                                             ('Raf Simons Oversized Hoodie', 'White oversized hoodie with Raf Simons graphics'),
                                             ('Comme des Garçons Play T-Shirt', 'White cotton T-shirt with heart logo from Comme des Garçons Play line'),
                                             ('Yohji Yamamoto Wide-Leg Pants', 'Black wide-leg wool trousers with minimalist design'),
                                             ('Alyx Chest Rig Bag', 'Utility-style chest rig with signature Alyx rollercoaster buckle'),
                                             ('Jil Sander Cashmere Sweater', 'Beige cashmere sweater with minimalist cut and design');

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        product_id BIGINT NOT NULL,
                        customer_name VARCHAR(255),
                        status VARCHAR(50),
                        amount BIGINT,
                        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);


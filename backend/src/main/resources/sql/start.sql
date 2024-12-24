-- Create a table for categories (if it doesn't exist)

INSERT INTO attachements(id, filepath, extension, content_type, filename, saved_name) VALUES
           (1, 'uploads\stores.png','.png','image/png','store.png','store.png'),
           (2, 'uploads\product.jpg','.jpg','image/jpg','product.jpg','store.jpg');
ALTER SEQUENCE attachements_id_seq RESTART WITH 3;


INSERT INTO categories (id, name,icon) VALUES
                                         (1, 'Other', 'fa-star'),
                                         (2, 'Smartphones', 'fa-mobile-screen'),
                                         (3, 'Laptops', 'fa-laptop'),
                                         (4, 'Tablets', 'fa-tablet-screen-button'),
                                         (5, 'PC/Gaming', 'fa-gamepad'),
                                         (6, 'E-Bikes', 'fa-person-biking'),
                                         (7, '3D Printers/CNC', 'fa-cube'),
                                         (8, 'TV/Projectors', 'fa-film'),
                                         (9, 'Personal Care', 'fa-user'),
                                         (10, 'Security', 'fa-eye'),
                                         (11, 'RC-Drones', 'fa-jet-fighter'),
                                         (12, 'Sound', 'fa-headphones'),
                                         (13, 'Tools', 'fa-wrench'),
                                         (14, 'Photo/Video', 'fa-camera'),
                                         (15, 'Fashion/Clothes', 'fa-shirt'),
                                         (16, 'Auto/Moto', 'fa-car'),
                                         (17, 'Wearables', 'fa-clock'),
                                         (18, 'Sports & Outdoors', 'fa-futbol'),
                                         (19, 'Electronics', 'fa-lightbulb'),
                                         (20, 'Books/E-Books', 'fa-book'),
                                         (21, 'Home/Garden', 'fa-house-chimney');



ALTER SEQUENCE categories_id_seq RESTART WITH 22;



INSERT INTO stores (id,name,siteurl,image_id) VALUES
                                               (1,'Amazon','https://www.amazon.de/',1),
                                               (2,'Banggood','https://www.banggood.com/',1),
                                               (3,'Kotsovolos','https://www.kotsovolos.gr/',1),
                                               (4,'Ebay','https://www.ebay.com/',1),
                                               (5,'Geekbuying','https://www.geekbuying.com/',1),
                                               (6,'Temu','https://www.temu.com/',1),
                                               (7,'Aliexpress','https://www.aliexpress.com/',1);

ALTER SEQUENCE stores_id_seq RESTART WITH 8;




-- Generate 100 dummy product inserts
DO $$
  BEGIN
    FOR i IN 1..100 LOOP
        INSERT INTO products (name, description, coupon, url, price, category_id, store_id, attachment_id)
        VALUES (
                 'Product ' || i, -- Name
                 'Description of product ' || i || '. This is a longer description to provide some context.', -- Description
                 "Exclusive Coupon", -- Coupon (30% chance of having a coupon)
                 'https://example.com/product/' || i, -- URL
                 round((random() * 1000)::numeric, 2), -- Price (random between 0 and 1000, rounded to 2 decimal places)
                 (SELECT category_id FROM categories ORDER BY random() LIMIT 1), -- Random category_id
                 (SELECT store_id FROM stores ORDER BY random() LIMIT 1),
                2

               );
      END LOOP;
  END $$;


-- Example of the products table schema (adapt as needed)
CREATE TABLE IF NOT EXISTS products (
                                      product_id SERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      description TEXT,
                                      coupon VARCHAR(255),
                                      url VARCHAR(255),
                                      price DECIMAL(10, 2) NOT NULL,
                                      category_id INTEGER REFERENCES categories(category_id),
                                      store_id INTEGER REFERENCES stores(store_id),
                                      attachment_id INTEGER REFERENCES attachments(attachment_id);

);

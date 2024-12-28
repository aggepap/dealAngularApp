-- Create a table for categories (if it doesn't exist)

INSERT INTO users(username,uuid, password, role, created_at) VALUES
  ('admin@email.com','randomuuid','$2a$11$PBnrkfuCMYhdm.sw2F5mS.WrjBjlHKNVxKWs/i2R0WJ7NTOZQZzAG','ADMIN',current_timestamp );
ALTER SEQUENCE users_id_seq RESTART WITH 2;


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
                                                (1,'Amazon','https://www.amazon.de/',null),
                                                (2,'Banggood','https://www.banggood.com/',null),
                                                (3,'Kotsovolos','https://www.kotsovolos.gr/',null),
                                                (4,'Ebay','https://www.ebay.com/',null),
                                                (5,'Geekbuying','https://www.geekbuying.com/',null),
                                                (6,'Temu','https://www.temu.com/',null),
                                                (7,'Aliexpress','https://www.aliexpress.com/',null),
                                                (8,'Eshop.gr','https://www.www.eshop.gr/',null);

ALTER SEQUENCE stores_id_seq RESTART WITH 8;




-- Generate 100 dummy product inserts
DO $$
  BEGIN
    FOR i IN 1..100 LOOP
        INSERT INTO products (name, description, coupon, url, price, category_id, store_id, created_at)
        VALUES (
                 'Product ' || i, -- Name
                 'Description of product ' || i || '. This is a longer description to provide some context.', -- Description
                 'Exclusive Coupon',
                 'https://example.com/product/' || i, -- URL
                 round((random() * 1000)::numeric, 2), -- Price (random between 0 and 1000, rounded to 2 decimal places)
                 (SELECT id FROM categories ORDER BY random() LIMIT 1),
                 (SELECT id FROM stores ORDER BY random() LIMIT 1),
                 current_timestamp

               );
      END LOOP;
  END $$;


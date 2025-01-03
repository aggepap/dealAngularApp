
INSERT INTO users(username,uuid, password, role, created_at) VALUES
  ('admin@email.com','randomuuid','$2a$11$PBnrkfuCMYhdm.sw2F5mS.WrjBjlHKNVxKWs/i2R0WJ7NTOZQZzAG','ADMIN',current_timestamp );



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

INSERT INTO attachments(id,created_at,updated_at,content_type,extension,file_path,filename,saved_name)
VALUES
  (1,'2024-12-28 20:23:16.00746','2024-12-28 20:23:16.00746','image/webp','.webp','.\\uploads\\7a02f333-d3c6-4e49-99e6-a51f3c720145.webp','GPD-Pocket-3-Handheld-Laptop-PC-EU-Plug-526997-1._p1_.webp','7a02f333-d3c6-4e49-99e6-a51f3c720145.webp'),
  (2,'2024-12-29 01:19:03.141584','2024-12-29 01:19:03.141584','image/jpeg','.jpg','.\\uploads\\4e6fd5ed-1b6f-4911-8689-21f3cc6f1bc7.jpg','1.jpg','4e6fd5ed-1b6f-4911-8689-21f3cc6f1bc7.jpg'),
  (3,'2024-12-29 01:20:01.626092','2024-12-29 01:20:01.626092','image/jpeg','.jpg','.\\uploads\\fde2afbc-0ffb-421f-9478-a5da9502c4e6.jpg','1.jpg','fde2afbc-0ffb-421f-9478-a5da9502c4e6.jpg'),
  (4,'2024-12-29 01:21:01.573281','2024-12-29 01:21:01.573281','image/jpeg','.jpeg','.\\uploads\\04c8ef3d-a592-48cb-b413-1b5414c7b467.jpeg','3.jpeg','04c8ef3d-a592-48cb-b413-1b5414c7b467.jpeg'),
  (5,'2024-12-29 01:21:51.447557','2024-12-29 01:21:01.573281','image/webp','.webp','.\\uploads\\8131f779-f163-4841-af9e-9b4ee06b8b7c.webp','4.webp','8131f779-f163-4841-af9e-9b4ee06b8b7c.webp'),
  (6,'2024-12-29 01:22:21.557031','2024-12-29 01:22:21.557031','image/webp','.webp','.\\uploads\\2a9dea48-f283-4bea-83a7-2618cf2e3b86.webp','mx-master-3s-mouse-top-view-graphite.webp','2a9dea48-f283-4bea-83a7-2618cf2e3b86.webp'),
  (7,'2024-12-29 01:23:03.602016','2024-12-29 01:23:03.602016','image/jpeg','.jpg','.\\uploads\\34d26098-510c-4a9a-b939-e985fd738274.jpg','6.jpg','34d26098-510c-4a9a-b939-e985fd738274.jpg'),
  (8,'2024-12-29 01:23:58.653375','2024-12-29 01:23:58.653375','image/jpeg','.jpeg','.\\uploads\\774e1c20-1bab-4b60-9635-a73e590ff18e.jpeg','7.jpeg','774e1c20-1bab-4b60-9635-a73e590ff18e.jpeg'),
  (9,'2024-12-29 01:24:30.072288','2024-12-29 01:24:30.072288','image/jpeg','.jpg','.\\uploads\\b355e7c1-61ef-44ce-b835-bbf9140626f6.jpg','8.jpg','b355e7c1-61ef-44ce-b835-bbf9140626f6.jpg'),
  (10,'2024-12-29 01:25:07.939627','2024-12-29 01:25:07.939627','image/jpeg','.jpg','.\\uploads\\9fea3808-e1c8-4463-8ebd-d87d2fdd8301.jpg','9.jpg','9fea3808-e1c8-4463-8ebd-d87d2fdd8301.jpg'),
  (11,'2024-12-29 01:25:47.123329','2024-12-29 01:25:47.123329','image/jpeg','.jpg','.\\uploads\\5479ed0b-9385-490f-86c0-af702b347462.jpg','10.jpg','5479ed0b-9385-490f-86c0-af702b347462.jpg'),
  (12,'2024-12-29 01:26:19.300586','2024-12-29 01:26:19.300586','image/jpeg','.jpg','.\\uploads\\574e92d8-14fc-4d5f-af40-c4bbc55676ff.jpg','11.jpg','574e92d8-14fc-4d5f-af40-c4bbc55676ff.jpg'),
  (13,'2024-12-29 01:27:17.103796','2024-12-29 01:27:17.103796','image/jpeg','.jpg','.\\uploads\\e19e91f2-bc74-493b-bbc6-a4c640dedbeb.jpg','12.jpg','e19e91f2-bc74-493b-bbc6-a4c640dedbeb.jpg'),
  (14,'2024-12-29 01:28:05.118971','2024-12-29 01:28:05.118971','image/jpeg','.jpg','.\\uploads\\80893c20-7fe6-4201-8644-1475066db2d6.jpg','13.jpg','80893c20-7fe6-4201-8644-1475066db2d6.jpg'),
  (15,'2024-12-29 01:28:33.863361','2024-12-29 01:28:33.863361','image/webp','.webp','.\\uploads\\c155cc30-5fd4-419d-8cac-019fa89a3484.webp','14.webp','c155cc30-5fd4-419d-8cac-019fa89a3484.webp'),
  (16,'2024-12-29 01:29:10.206623','2024-12-29 01:29:10.206623','image/jpeg','.jpg','.\\uploads\\1953e5db-4bcd-44cd-a542-1327c8a2eab8.jpg','15.jpg','1953e5db-4bcd-44cd-a542-1327c8a2eab8.jpg'),
  (17,'2024-12-29 01:30:06.771289','2024-12-29 01:30:06.771289','image/webp','.webp','.\\uploads\\12328bbe-13c5-47ac-9ff8-8784abbc1f40.webp','16.webp','12328bbe-13c5-47ac-9ff8-8784abbc1f40.webp'),
  (18,'2024-12-29 01:31:17.02024','2024-12-29 01:31:17.02024','image/webp','.webp','.\\uploads\\0a7d4720-b065-4068-8097-9232bf1f5ec9.webp','17.webp','0a7d4720-b065-4068-8097-9232bf1f5ec9.webp'),
  (19,'2024-12-29 01:31:42.955082','2024-12-29 01:31:42.955082','image/jpeg','.jpg','.\\uploads\\b57be99a-9a34-4226-8dce-528b6e83c752.jpg','18.jpg','b57be99a-9a34-4226-8dce-528b6e83c752.jpg'),
  (20,'2024-12-29 01:32:08.152278','2024-12-29 01:32:08.152278','image/jpeg','.jpg','.\\uploads\\c78cfbe7-6307-4698-80d3-a2a705158632.jpg','19.jpg','c78cfbe7-6307-4698-80d3-a2a705158632.jpg'),
  (21,'2024-12-29 01:32:41.233364','2024-12-29 01:32:41.233364','image/webp','.webp','.\\uploads\\e671e98e-bdb4-45bc-bcca-a129057f6420.webp','20.webp','e671e98e-bdb4-45bc-bcca-a129057f6420.webp'),
  (22,'2024-12-29 01:33:03.911049','2024-12-29 01:33:03.911049','image/png','.png','.\\uploads\\705789a7-b6af-4269-a6ee-f5a45f14f889.png','21.png','705789a7-b6af-4269-a6ee-f5a45f14f889.png'),
  (23,'2024-12-29 01:33:31.981402','2024-12-29 01:33:31.981402','image/webp','.webp','.\\uploads\\70796675-83c1-4f4e-be29-8609ff3d7c69.webp','22.webp','70796675-83c1-4f4e-be29-8609ff3d7c69.webp'),
  (24,'2024-12-29 01:34:19.382397','2024-12-29 01:34:19.382397','image/webp','.webp','.\\uploads\\9f515162-2038-4bcd-a665-14a07ffb3f20.webp','23.webp','9f515162-2038-4bcd-a665-14a07ffb3f20.webp'),
  (25,'2024-12-29 01:34:43.003024','2024-12-29 01:34:43.003024','image/jpeg','.jpg','.\\uploads\\08f85524-f217-48df-b475-4209f8d9c5a1.jpg','24.jpg','08f85524-f217-48df-b475-4209f8d9c5a1.jpg'),
  (26,'2024-12-29 01:35:24.224123','2024-12-29 01:35:24.224123','image/webp','.webp','.\\uploads\\9821e657-d036-4505-a12f-2b066b1b5f21.webp','25.webp','9821e657-d036-4505-a12f-2b066b1b5f21.webp'),
  (27,'2024-12-29 01:36:00.583906','2024-12-29 01:36:00.583906','image/jpeg','.jpg','.\\uploads\\1fcdc258-8e63-4104-a2a6-9e183d8f369c.jpg','26.jpg','1fcdc258-8e63-4104-a2a6-9e183d8f369c.jpg'),
  (28,'2024-12-29 01:36:31.914549','2024-12-29 01:36:31.914549','image/png','.png','.\\uploads\\b29f8494-f85d-4c0f-a434-44d1391f6393.png','27.png','b29f8494-f85d-4c0f-a434-44d1391f6393.png'),
  (29,'2024-12-29 01:37:06.760463','2024-12-29 01:37:06.760463','image/webp','.webp','.\\uploads\\074bb532-8b51-482a-a057-764311ea7e02.webp','28.webp','074bb532-8b51-482a-a057-764311ea7e02.webp'),
  (30,'2024-12-29 01:37:44.235706','2024-12-29 01:37:44.235706','image/webp','.webp','.\\uploads\\20294066-9bcf-4260-a7dd-1bc5c249a95b.webp','29.webp','20294066-9bcf-4260-a7dd-1bc5c249a95b.webp'),
  (31,'2024-12-29 01:39:27.297556','2024-12-29 01:39:27.297556','image/png','.png','.\\uploads\\b3d3dd38-307b-4c28-afb3-15e4197a1adc.png','30.png','b3d3dd38-307b-4c28-afb3-15e4197a1adc.png'),
  (32,'2024-12-29 01:40:05.48913','2024-12-29 01:40:05.48913','image/png','.png','.\\uploads\\6902f8dd-ebc9-4bd9-89b1-2229182ac448.png','31.png','6902f8dd-ebc9-4bd9-89b1-2229182ac448.png'),
  (33,'2024-12-29 01:40:26.95135','2024-12-29 01:40:26.95135','image/jpeg','.jpg','.\\uploads\\bd656c2c-0eb0-488e-85b5-915c7be4b08f.jpg','32.jpg','bd656c2c-0eb0-488e-85b5-915c7be4b08f.jpg'),
  (34,'2024-12-29 01:40:49.079842','2024-12-29 01:40:49.079842','image/jpeg','.jpeg','.\\uploads\\104cb667-3d7c-457d-b1ae-2b2ee9330725.jpeg','33.jpeg','104cb667-3d7c-457d-b1ae-2b2ee9330725.jpeg'),
  (35,'2024-12-29 01:41:15.657489','2024-12-29 01:41:15.657489','image/jpeg','.jpg','.\\uploads\\a9c7037e-244a-4c27-b93f-e79336d1c170.jpg','34.jpg','a9c7037e-244a-4c27-b93f-e79336d1c170.jpg'),
  (36,'2024-12-29 01:41:38.626735','2024-12-29 01:41:38.626735','image/jpeg','.jpg','.\\uploads\\f6517a1f-42eb-49dc-8ba7-51641d560ef9.jpg','35.jpg','f6517a1f-42eb-49dc-8ba7-51641d560ef9.jpg'),
  (37,'2024-12-29 01:42:04.115068','2024-12-29 01:42:04.115068','image/jpeg','.jpg','.\\uploads\\a530faf2-dce8-4c51-ba80-a58c6d7e853f.jpg','36.jpg','a530faf2-dce8-4c51-ba80-a58c6d7e853f.jpg'),
  (38,'2024-12-29 01:42:39.451237','2024-12-29 01:42:39.451237','image/png','.png','.\\uploads\\a4153607-fe72-4090-800e-2e33d8b26464.png','37.png','a4153607-fe72-4090-800e-2e33d8b26464.png'),
  (39,'2024-12-29 01:43:11.711339','2024-12-29 01:43:11.711339','image/png','.png','.\\uploads\\b8733f91-b01e-488c-bdef-270d28865dc5.png','38.png','b8733f91-b01e-488c-bdef-270d28865dc5.png'),
  (40,'2024-12-29 01:43:35.424565','2024-12-29 01:43:35.424565','image/jpeg','.jpg','.\\uploads\\54d5fd83-a6c2-4ebe-a96b-86c48b258a52.jpg','39.jpg','54d5fd83-a6c2-4ebe-a96b-86c48b258a52.jpg'),
  (41,'2024-12-29 01:46:52.038784','2024-12-29 01:46:52.038784','image/jpeg','.jpg','.\\uploads\\c6ceec7e-810d-4514-b414-95092c8ae282.jpg','40.jpg','c6ceec7e-810d-4514-b414-95092c8ae282.jpg'),
  (42,'2024-12-29 01:47:46.57405','2024-12-29 01:47:46.57405','image/webp','.webp','.\\uploads\\e4800b98-f54f-4978-87ec-74fe9a500e98.webp','41.webp','e4800b98-f54f-4978-87ec-74fe9a500e98.webp'),
  (43,'2024-12-29 01:48:14.463898','2024-12-29 01:48:14.463898','image/webp','.webp','.\\uploads\\f213738c-b474-4f2c-b961-4e453bf462ff.webp','42.webp','f213738c-b474-4f2c-b961-4e453bf462ff.webp'),
  (44,'2024-12-29 01:48:36.021664','2024-12-29 01:48:36.021664','image/jpeg','.jpg','.\\uploads\\f6a3ece7-f638-45c3-9f76-1282f58aa3e2.jpg','43.jpg','f6a3ece7-f638-45c3-9f76-1282f58aa3e2.jpg'),
  (45,'2024-12-29 01:49:17.163035','2024-12-29 01:49:17.163035','image/webp','.webp','.\\uploads\\17c6876c-4ad6-401a-a92b-c866c9e4b2d8.webp','44.webp','17c6876c-4ad6-401a-a92b-c866c9e4b2d8.webp'),
  (46,'2024-12-29 01:52:09.228341','2024-12-29 01:52:09.228341','image/jpeg','.jpg','.\\uploads\\7c3f422d-fb37-49ad-99d6-dac979fa9113.jpg','45.jpg','7c3f422d-fb37-49ad-99d6-dac979fa9113.jpg'),
  (47,'2024-12-29 01:52:45.921426','2024-12-29 01:52:45.921426','image/jpeg','.jpg','.\\uploads\\81a6c76c-1ca0-49d5-be1b-1074aacd493b.jpg','46.jpg','81a6c76c-1ca0-49d5-be1b-1074aacd493b.jpg'),
  (48,'2024-12-29 01:53:17.618413','2024-12-29 01:53:17.618413','image/jpeg','.jpg','.\\uploads\\5a7f31d5-289d-49b5-b4a9-361d3f384869.jpg','47.jpg','5a7f31d5-289d-49b5-b4a9-361d3f384869.jpg'),
  (49,'2024-12-29 01:53:50.774055','2024-12-29 01:53:50.774055','image/jpeg','.jpg','.\\uploads\\1f946c94-9fb4-411e-a74d-177aefb87f40.jpg','48.jpg','1f946c94-9fb4-411e-a74d-177aefb87f40.jpg'),
  (50,'2024-12-29 01:54:17.119319','2024-12-29 01:54:17.119319','image/jpeg','.jpg','.\\uploads\\4b42577c-cef1-400a-a161-b582df841a85.jpg','49.jpg','4b42577c-cef1-400a-a161-b582df841a85.jpg'),
  (51,'2024-12-29 01:54:49.380593','2024-12-29 01:54:49.380593','image/jpeg','.jpg','.\\uploads\\0460bd23-068b-40a6-8b04-a2bcb7412013.jpg','50.jpg','0460bd23-068b-40a6-8b04-a2bcb7412013.jpg'),
  (52,'2024-12-29 01:55:14.431241','2024-12-29 01:55:14.431241','image/webp','.webp','.\\uploads\\858bb79f-00fa-405c-9082-24ef113e0fa0.webp','51.webp','858bb79f-00fa-405c-9082-24ef113e0fa0.webp'),
  (53,'2024-12-29 01:55:38.8838','2024-12-29 01:55:38.8838','image/jpeg','.jpeg','.\\uploads\\295ef20d-fa35-4aff-8ce7-f0b9ec0dd6a2.jpeg','52.jpeg','295ef20d-fa35-4aff-8ce7-f0b9ec0dd6a2.jpeg'),
  (54,'2024-12-29 01:56:32.350048','2024-12-29 01:56:32.350048','image/webp','.webp','.\\uploads\\bf2f3443-3705-40f1-b1dd-7a0e78c4587d.webp','53.webp','bf2f3443-3705-40f1-b1dd-7a0e78c4587d.webp'),
  (55,'2024-12-29 01:57:15.575208','2024-12-29 01:57:15.575208','image/jpeg','.jpg','.\\uploads\\c39d90e6-ba79-4562-ae7b-9ed59eb2cf62.jpg','54.jpg','c39d90e6-ba79-4562-ae7b-9ed59eb2cf62.jpg'),
  (56,'2024-12-29 01:57:38.806721','2024-12-29 01:57:38.806721','image/jpeg','.jpg','.\\uploads\\28b2c825-a9a4-40c6-b8e8-10dd289f2366.jpg','55.jpg','28b2c825-a9a4-40c6-b8e8-10dd289f2366.jpg'),
  (57,'2024-12-29 01:58:06.615234','2024-12-29 01:58:06.615234','image/jpeg','.jpg','.\\uploads\\e28d90d1-320a-40c7-9384-88bfc612fc1d.jpg','56.jpg','e28d90d1-320a-40c7-9384-88bfc612fc1d.jpg'),
  (58,'2024-12-29 01:58:33.144654','2024-12-29 01:58:33.144654','image/jpeg','.jpg','.\\uploads\\f4cb5f32-2d7c-48ea-b833-5a118271b9cb.jpg','57.jpg','f4cb5f32-2d7c-48ea-b833-5a118271b9cb.jpg'),
  (60,'2024-12-29 02:25:27.084471','2024-12-29 02:25:27.084471','image/png','.png','.\\uploads\\4b9aeb4a-9450-46dd-ace7-09086cf13e24.png','amazon.png','4b9aeb4a-9450-46dd-ace7-09086cf13e24.png'),
  (61,'2024-12-29 02:25:38.01498','2024-12-29 02:25:38.01498','image/jpeg','.jpg','.\\uploads\\b2c218ce-818d-451d-9eb9-9846618873d8.jpg','eshopgr.jpg','b2c218ce-818d-451d-9eb9-9846618873d8.jpg'),
  (62,'2024-12-29 02:25:51.103736','2024-12-29 02:25:51.103736','image/png','.png','.\\uploads\\8a615885-08c0-4853-88ef-b1ae9c363d12.png','aliexpress.png','8a615885-08c0-4853-88ef-b1ae9c363d12.png'),
  (63,'2024-12-29 02:26:01.930408','2024-12-29 02:26:01.930408','image/jpeg','.jpg','.\\uploads\\9c6f777d-96d1-4b2f-b570-c364a287fb7f.jpg','temu.jpg','9c6f777d-96d1-4b2f-b570-c364a287fb7f.jpg'),
  (64,'2024-12-29 02:26:22.598966','2024-12-29 02:26:22.598966','image/jpeg','.jpg','.\\uploads\\e89e491f-3979-48d2-94cb-0aacaeb7c40d.jpg','geekbuying.jpg','e89e491f-3979-48d2-94cb-0aacaeb7c40d.jpg'),
  (65,'2024-12-29 02:26:38.189801','2024-12-29 02:26:38.189801','image/png','.png','.\\uploads\\ea69daa5-fbc1-465c-9f8f-0f513f33fce8.png','ebay.png','ea69daa5-fbc1-465c-9f8f-0f513f33fce8.png'),
  (66,'2024-12-29 02:26:47.695414','2024-12-29 02:26:47.695414','image/png','.png','.\\uploads\\43e7ab78-1dd5-4fcf-bcb1-f76d328c43ab.png','Kotsovolos.png','43e7ab78-1dd5-4fcf-bcb1-f76d328c43ab.png'),
  (67,'2024-12-29 02:26:58.650169','2024-12-29 02:26:58.650169','image/png','.png','.\\uploads\\164b86c5-812f-48df-9ceb-4b2d138da791.png','banggood.png','164b86c5-812f-48df-9ceb-4b2d138da791.png');
ALTER SEQUENCE attachments_id_seq RESTART WITH 68;


INSERT INTO stores (id,name,siteurl,image_id) VALUES
                                                (1,'Amazon','https://www.amazon.de/',60),
                                                (2,'Banggood','https://www.banggood.com/',67),
                                                (3,'Kotsovolos','https://www.kotsovolos.gr/',66),
                                                (4,'Ebay','https://www.ebay.com/',65),
                                                (5,'Geekbuying','https://www.geekbuying.com/',64),
                                                (6,'Temu','https://www.temu.com/',63),
                                                (7,'Aliexpress','https://www.aliexpress.com/',62),
                                                (8,'Eshop.gr','https://www.www.eshop.gr/',61);

ALTER SEQUENCE stores_id_seq RESTART WITH 9;

CREATE INDEX idx_product_categories_category_id ON products (category_id);
CREATE INDEX idx_product_store_store_id ON products (store_id);
CREATE INDEX idx_attachments_id ON attachments (id);
CREATE INDEX idx_categories_id ON categories (id);
CREATE INDEX idx_products_id ON products (id);
CREATE INDEX idx_stores_id ON stores (id);
CREATE INDEX idx_users_id ON users (id);
CREATE INDEX idx_categories_name ON categories (name);
CREATE INDEX idx_products_name ON products (name);
CREATE INDEX idx_stores_name ON stores (name);
CREATE INDEX idx_users_username ON users (username);



--dummy product inserts
INSERT INTO products (id, name, description, coupon, url, price, category_id, store_id, created_at,image_id,updated_at)
VALUES
  (1, 'Apple Watch Series 8', 'Premium smartwatch with advanced health features.', 'WATCH20', 'https://www.apple.com/watch-series-8', 499.00, 17, 8, current_timestamp,3,current_timestamp),
  (2, 'Magic Keyboard with Touch ID and Numeric Keypad', 'Sleek keyboard with built-in fingerprint sensor.', 'KEYBOARD10', 'https://www.apple.com/magic-keyboard', 149.00, 19, 2, current_timestamp,2,current_timestamp),
  (3, 'iRobot Roomba i7+ (E5154)', 'Self-emptying robotic vacuum cleaner.', 'VACUUM5', 'https://www.irobot.com/en-us/roomba/i7', 799.00, 21, 3, current_timestamp,4,current_timestamp),
  (4, 'Sony WH-1000XM5', 'Industry-leading noise-canceling headphones.', 'AUDIO25', 'https://www.sony.com/electronics/headphones/wh-1000xm5', 399.00, 12, 4, current_timestamp,5,current_timestamp),
  (5, 'Logitech MX Master 3S', 'High-performance wireless mouse with excellent ergonomics.', 'MOUSE15', 'https://www.logitech.com/en-us/products/mice/mx-master-3s.html', 99.99, 19, 5, current_timestamp,6,current_timestamp),
  (6, 'Apple AirTag (4-pack)', 'Bluetooth trackers to locate your belongings.', 'FINDIT10', 'https://www.apple.com/airtag', 99.00, 10, 5, current_timestamp,7,current_timestamp),
  (7, 'Garmin Vivosmart 5', 'Fitness tracker with advanced health monitoring.', 'SCALE20', 'https://www.garmin.com/en-US/p/678739', 149.00, 17, 3, current_timestamp,8,current_timestamp),
  (8, 'Arlo Pro 4 Spotlight Camera', 'Wireless security camera with powerful spotlight.', 'SECURE5', 'https://www.arlo.com/en-us/products/arlo-pro-4-spotlight-camera', 249.00, 10, 5, current_timestamp,9,current_timestamp),
  (9, 'Anker 521 Magnetic Portable Charger', 'Compact and powerful portable charger for iPhones.', 'MAGSAFE15', 'https://www.anker.com/products/anker-521-magnetic-portable-charger', 59.99, 19, 2, current_timestamp,10,current_timestamp),
  (10, 'Nintendo Switch OLED Model', 'Portable gaming console with vibrant display.', 'SWITCH10', 'https://www.nintendo.com/switch', 349.99, 5, 5, current_timestamp,11,current_timestamp),
  (11, 'Samsung T7 Shield', 'Rugged and portable SSD for fast data transfer.', 'SSD20', 'https://www.samsung.com/us/mobile-storage/t7-shield', 99.99, 19, 1, current_timestamp,12,current_timestamp),
  (12, 'Dyson V15 Detect', 'Powerful cordless vacuum with laser dust detection.', 'PVACUUM15', 'https://www.dyson.com/v15-detect', 699.00, 21, 7, current_timestamp,13,current_timestamp),
  (13, 'Apple Watch SE', 'Affordable smartwatch with essential features.', 'WATCH5', 'https://www.apple.com/watch-se', 249.00, 17, 8, current_timestamp,14,current_timestamp),
  (14, 'Samsung Galaxy Tab S8', 'High-performance Android tablet with stunning display.', 'TAB10', 'https://www.samsung.com/us/tablets/galaxy-tab-s8', 699.00, 4, 4, current_timestamp,15,current_timestamp),
  (15, 'Apple MacBook Air (M2)', 'Lightweight and powerful laptop with M2 chip.', 'LAPTOP15', 'https://www.apple.com/macbook-air', 1199.00, 3, 7, current_timestamp,16,current_timestamp),
  (16, 'Bose SoundLink Flex', 'Portable Bluetooth speaker with rugged design.', 'SPEAKER20', 'https://www.bose.com/en_us/products/speakers/portable-speakers/soundlink-flex.html', 149.00, 12, 6, current_timestamp,17,current_timestamp),
  (17, 'LG C2 Series OLED TV', 'Exceptional picture quality with OLED technology.', 'TV10', 'https://www.lg.com/us/tvs/lg-c2-series-oled-tv', 1499.00, 8, 2, current_timestamp,18,current_timestamp),
  (18, 'DJI Mini 3 Pro', 'Compact and powerful drone with 4K video recording.', 'DRONE5', 'https://www.dji.com/mini-3-pro', 699.00, 11, 1, current_timestamp,19,current_timestamp),
  (19, 'Samsung Galaxy Buds2 Pro', 'Premium wireless earbuds with excellent sound quality.', 'EARBUDS15', 'https://www.samsung.com/us/audio/galaxy-buds2-pro', 199.00, 12, 3, current_timestamp,20,current_timestamp),
  (20, 'PlayStation 5', 'Powerful gaming console with stunning graphics.', 'CONSOLE10', 'https://www.playstation.com/en-us/ps5', 499.00, 5, 8, current_timestamp,21,current_timestamp),
  (21, 'BenQ TK800M', 'Home theater projector with 4K HDR support.', 'PROJECTOR20', 'https://www.benq.com/en-us/projector/home-theater/tk800m.html', 1999.00, 8, 4, current_timestamp,22,current_timestamp),
  (22, 'Instant Pot Duo Crisp + Air Fryer', 'Multi-cooker with air frying capabilities.', 'POT15', 'https://www.instantpot.com/duo-crisp-air-fryer', 149.95, 21, 6, current_timestamp,23,current_timestamp),
  (23, 'Vitamix A2300', 'High-performance blender for smoothies and more.', 'BLEND10', 'https://www.vitamix.com/blenders/a2300', 449.00, 21, 1, current_timestamp,24,current_timestamp),
  (24, 'Breville Precision Brewer Thermal', 'Coffee maker that brews coffee to the perfect temperature.', 'COFFEE5', 'https://www.brevilleusa.com/us/en/products/coffee-and-specialty/precision-brewer-thermal.html', 299.00, 21, 5, current_timestamp,25,current_timestamp),
  (25, 'Philips Sonicare DiamondClean 9000', 'High-end electric toothbrush with advanced sonic technology.', 'TOOTHBRUSH15', 'https://www.philips.com/en-us/c-p/oral-care/sonicare-diamondclean-9000-hx9911/91/product-details', 299.00, 9, 2, current_timestamp,26,current_timestamp),
  (26, 'Coway Airmega 400S', 'High-performance air purifier for large rooms.', 'AIR10', 'https://www.cowayusa.com/airmega-400s', 499.00, 21, 7, current_timestamp,27,current_timestamp),
  (27, 'Nest Learning Thermostat', 'Smart thermostat that learns your preferences.', 'THERMOSTAT5', 'https://www.google.com/nest/thermostat', 129.00, 21, 3, current_timestamp,28,current_timestamp),
  (28, 'iRobot Roomba j7+', 'Robotic vacuum that avoids obstacles.', 'ROBOT5', 'https://www.irobot.com/en-us/roomba/j7', 899.00, 21, 6, current_timestamp,29,current_timestamp),
  (29, 'Fitbit Charge 5', 'Fitness tracker with built-in GPS and stress management tools.', 'FITNESS10', 'https://www.fitbit.com/us/en/products/charge-5', 149.95, 17, 1, current_timestamp,30,current_timestamp),
  (30, 'Bose QuietComfort Earbuds II', 'Premium noise-canceling earbuds with improved sound quality.', 'HEADPHONES5', 'https://www.bose.com/',254.3,12,6, current_timestamp,31,current_timestamp),
  (31, 'Apple iPhone 14 Pro', 'Flagship smartphone with pro-level camera system.', 'IPHONE15', 'https://www.apple.com/iphone-14-pro', 999.00, 2, 8, current_timestamp,32,current_timestamp),
  (32, 'Samsung Galaxy S23 Ultra', 'Powerful Android smartphone with impressive zoom capabilities.', 'GALAXY10', 'https://www.samsung.com/us/smartphones/galaxy-s23-ultra', 1199.00, 2, 1, current_timestamp,33,current_timestamp),
  (33, 'Google Pixel 7 Pro', 'Excellent smartphone with the best Android experience.', 'PIXEL5', 'https://store.google.com/us/product/pixel_7_pro', 899.00, 2, 3, current_timestamp,34,current_timestamp),
  (34, 'Apple MacBook Pro 14', 'High-performance laptop with M2 Pro chip.', 'MBP15', 'https://www.apple.com/macbook-pro-14', 1999.00, 3, 7, current_timestamp,35,current_timestamp),
  (35, 'Dell XPS 13 Plus', 'Premium ultrabook with a sleek and modern design.', 'DELL10', 'https://www.dell.com/en-us/shop/laptops/xps/xps-13-plus/spd/xps-13-plus-laptop', 1499.00, 3, 2, current_timestamp,36,current_timestamp),
  (36, 'Lenovo Legion 5 Pro', 'Powerful gaming laptop for demanding titles.', 'LEGION15', 'https://www.lenovo.com/us/en/p/laptops/legion-laptops/legion-5-pro/legion-5-pro-16ach6h/product.html', 1699.00, 3, 5, current_timestamp,37,current_timestamp),
  (37, 'Samsung Galaxy Tab S9+', 'High-end Android tablet with stunning display.', 'TAB20', 'https://www.samsung.com/us/tablets/galaxy-tab-s9', 899.00, 4, 6, current_timestamp,38,current_timestamp),
  (38, 'iPad Pro 12.9-inch', 'Powerful tablet for creative professionals.', 'IPAD15', 'https://www.apple.com/ipad-pro', 1099.00, 4, 8, current_timestamp,39,current_timestamp),
  (39, 'PlayStation 5 Digital Edition', 'Powerful gaming console with no disc drive.', 'PS5D10', 'https://www.playstation.com/en-us/ps5', 399.00, 5, 1, current_timestamp,40,current_timestamp),
  (40, 'Logitech G Pro X Superlight', 'Lightweight and high-performance gaming mouse.', 'GPRO5', 'https://www.logitechg.com/en-us/products/gaming-mice/g-pro-x-superlight.html', 149.99, 5, 4, current_timestamp,41,current_timestamp),
  (41, 'VanMoof S5', 'High-tech electric bike with integrated theft prevention.', 'VAMOOF10', 'https://www.vanmoof.com/en_US/s5', 3498.00, 6, 2, current_timestamp,42,current_timestamp),
  (42, 'Rad Power Bikes RadRunner 2', 'Versatile electric bike for cargo and commuting.', 'RAD20', 'https://www.radpowerbikes.com/products/radrunner-2', 1499.00, 6, 3, current_timestamp,43,current_timestamp),
  (43, 'Anycubic Vyper', 'Easy-to-use 3D printer for beginners.', '3DPRINT15', 'https://www.anycubic.com/products/vyper', 269.00, 7, 5, current_timestamp,44,current_timestamp),
  (44, 'Creality Ender 3 S1 Pro', 'Popular 3D printer with excellent print quality.', 'ENDER10', 'https://www.creality.com/products/ender-3-s1-pro', 399.00, 7, 6, current_timestamp,45,current_timestamp),
  (45, 'Sony Bravia XR A95K OLED', 'Top-of-the-line OLED TV with stunning picture quality.', 'TV20', 'https://www.sony.com/electronics/tvs/a95k/overview', 3999.00, 8, 7, current_timestamp,46,current_timestamp),
  (46, 'BenQ TK850i', '4K HDR projector for home theater enthusiasts.', 'PROJECTOR15', 'https://www.benq.com/en-us/projector/home-theater/tk850i.html', 2999.00, 8, 2, current_timestamp,47,current_timestamp),
  (47, 'Philips Norelco OneBlade', 'Versatile grooming tool for face and body.', 'ONEBLADE10', 'https://www.usa.philips.com/c-p/grooming/oneblade-face-body/oneblade-pro-qp2630-40/91/product-details', 49.99, 9, 1, current_timestamp,48,current_timestamp),
  (48, 'Foreo Luna 3', 'Facial cleansing and massage device.', 'LUNA15', 'https://www.foreo.com/us/luna-3', 199.00, 9, 4, current_timestamp,49,current_timestamp),
  (49, 'Ring Video Doorbell 4', 'Smart doorbell with HD video and two-way audio.', 'RING10', 'https://www.ring.com/products/doorbell-4', 199.00, 10, 3, current_timestamp,50,current_timestamp),
  (50, 'Arlo Pro 4', 'Wireless security camera system with advanced features.', 'ARLO15', 'https://www.arlo.com/en-us/products/arlo-pro-4', 499.00, 10, 6, current_timestamp,51,current_timestamp),
  (51, 'DJI Mavic 3', 'High-end drone with incredible camera capabilities.', 'MAVIC10', 'https://www.dji.com/mavic-3', 2199.00, 11, 5, current_timestamp,52,current_timestamp),
  (52, 'Holy Stone HS720E', 'Affordable drone with 4K camera for beginners.', 'HS720E5', 'https://www.amazon.com/Holy-Stone-HS720E-Foldable-Quadcopter/dp/B07Q9F8G8S', 129.99, 11, 8, current_timestamp,53,current_timestamp),
  (53, 'Bose QuietComfort Earbuds III', 'Premium noise-canceling earbuds with improved sound quality.', 'BOSE15', 'https://www.bose.com/en_us/products/headphones/earbuds/quietcomfort-earbuds-ii.html', 299.00, 12, 1, current_timestamp,54,current_timestamp),
  (54, 'Sony SRS-XB43', 'Portable Bluetooth speaker with powerful sound.', 'SONYXB10', 'https://www.sony.com/electronics/speakers/srs-xb43', 199.00, 12, 4, current_timestamp,55,current_timestamp),
  (55, 'DeWalt DCD796D2 20V Max Drill/Driver', 'Powerful and versatile cordless drill.', 'DEWALT10', 'https://www.dewalt.com/products/power-tools/drills/dcd796d2-20v-max-xr-lithium-ion-drill-driver-kit', 149.00, 13, 3, current_timestamp,56,current_timestamp),
  (56, 'Makita XGT 40V Max Brushless Cordless Impact Driver', 'High-performance impact driver for demanding tasks.', 'MAKITA15', 'https://www.makitatools.com/products/xgt-40v-max-brushless-cordless-impact-driver-xdt11z', 249.00, 13, 6, current_timestamp,57,current_timestamp),
  (57, 'Sony Alpha 7 IV', 'Professional mirrorless camera with excellent video capabilities.', 'A7IV10', 'https://www.sony.com/electronics/cameras/alpha-7-iv', 2499.00, 14, 2, current_timestamp,58,current_timestamp);
ALTER SEQUENCE products_id_seq RESTART WITH 58;

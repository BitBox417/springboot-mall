
-- 檢查是否存在名為 trevorDB2 的資料庫，如果不存在則建立
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'trevorDB2')
    CREATE DATABASE trevorDB2;
GO
--	使用 trevorDB2 資料庫
USE trevorDB2;


--	會員功能

----	檢查是否存在名為 members 的資料表，如果不存在則建立
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'members')
    CREATE TABLE members (
        memberid INT IDENTITY(1,1) PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,--登入帳號，唯一性
        password VARCHAR(500) NOT NULL,--登入密碼
        nickname VARCHAR(50) NOT NULL,--會員名稱，可以重複
        admin BIT DEFAULT 0,--是否為管理員，預設為false
		deleted BIT DEFAULT 0--是否標記刪除，預設為false
		,
		email              VARCHAR(256) ,
		created_date       DATETIME NOT NULL DEFAULT GETDATE(),
		last_modified_date DATETIME NOT NULL DEFAULT GETDATE()
    );

---- 檢查 members 資料表是否為空，如果為空則插入三筆測試數據（密碼為經過BCrypt雜湊的＂123456＂）
IF NOT EXISTS (SELECT TOP 1 * FROM members)
    INSERT INTO members (username, password, nickname, admin) VALUES 
	('admin', '$2a$10$.UAoeaAVeH8vhPsxHaw1I.teyo3iBunZllqraM1EmHQJwk1CkwD8u', 'Master', 1),
	('user1', '$2a$10$.UAoeaAVeH8vhPsxHaw1I.teyo3iBunZllqraM1EmHQJwk1CkwD8u', 'Mary', 0),
	('user2', '$2a$10$.UAoeaAVeH8vhPsxHaw1I.teyo3iBunZllqraM1EmHQJwk1CkwD8u', 'Jack', 0);


SELECT * FROM members



-------------------------------- Shopping Mall



CREATE TABLE product
(
    product_id         INT          NOT NULL PRIMARY KEY IDENTITY(1,1),
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       DATETIME     NOT NULL DEFAULT GETDATE(),
    last_modified_date DATETIME     NOT NULL DEFAULT GETDATE()
);




INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('蘋果（澳洲）', 'FOOD', 'https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg', 30, 10, '這是來自澳洲的蘋果！', '2022-03-19 17:00:00', '2022-03-22 18:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('蘋果（日本北海道）', 'FOOD', 'https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_1280.jpg', 300, 5, '這是來自日本北海道的蘋果！', '2022-03-19 18:30:00', '2022-03-19 18:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('好吃又鮮甜的蘋果橘子', 'FOOD', 'https://cdn.pixabay.com/photo/2021/07/30/04/17/orange-6508617_1280.jpg', 10, 50, null, '2022-03-20 09:00:00', '2022-03-24 15:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Toyota', 'CAR', 'https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg', 100000, 5, null, '2022-03-20 09:20:00', '2022-03-20 09:20:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('BMW', 'CAR', 'https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg', 500000, 3, '渦輪增壓，直列4缸，DOHC雙凸輪軸，16氣門', '2022-03-20 12:30:00', '2022-03-20 12:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Benz', 'CAR', 'https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg', 600000, 2, null, '2022-03-21 20:10:00', '2022-03-22 10:50:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Tesla', 'CAR', 'https://cdn.pixabay.com/photo/2021/01/15/16/49/tesla-5919764_1280.jpg', 450000, 5, '世界最暢銷的充電式汽車', '2022-03-21 23:30:00', '2022-03-21 23:30:00');




SELECT TOP 2 * FROM product;






SELECT *
FROM (
         SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 0)) AS RowNum, *
         FROM product
     ) AS RowConstrainedResult
WHERE RowNum > 3
ORDER BY RowNum
OFFSET 0 ROWS
    FETCH NEXT 2 ROWS ONLY;




--order

CREATE TABLE [order]
(
    order_id           INT       NOT NULL PRIMARY KEY IDENTITY,
    memberid            INT       NOT NULL,
    total_amount       INT       NOT NULL, -- 訂單總花費
    created_date       DATETIME  NOT NULL,
    last_modified_date DATETIME  NOT NULL
);

CREATE TABLE order_item
(
    order_item_id INT NOT NULL PRIMARY KEY IDENTITY,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL, -- 商品數量
    amount        INT NOT NULL  -- 商品花費
);


INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) VALUES (6, 100110, '2022-06-02 16:51:49', '2022-06-02 16:51:49');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 4, 2, 60);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 6, 5, 50);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 7, 1, 100000);


-- Order 1
INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) 
VALUES (2, 500, '2022-06-01 10:30:00', '2022-06-01 10:30:00');

-- Order 1 Items
INSERT INTO order_item (order_id, product_id, quantity, amount) 
VALUES (1, 1, 2, 60), -- 2 units of product with ID 1
       (1, 3, 1, 300); -- 1 unit of product with ID 3

-- Order 2
INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) 
VALUES (3, 700, '2022-06-02 09:45:00', '2022-06-02 09:45:00');

-- Order 2 Items
INSERT INTO order_item (order_id, product_id, quantity, amount) 
VALUES (2, 2, 3, 180), -- 3 units of product with ID 2
       (2, 5, 2, 120); -- 2 units of product with ID 5

-- Order 3
INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) 
VALUES (4, 300, '2022-06-03 14:20:00', '2022-06-03 14:20:00');

-- Order 3 Items
INSERT INTO order_item (order_id, product_id, quantity, amount) 
VALUES (3, 4, 1, 100), -- 1 unit of product with ID 4
       (3, 6, 1, 200); -- 1 unit of product with ID 6

-- Order 4
INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) 
VALUES (5, 1000, '2022-06-04 18:00:00', '2022-06-04 18:00:00');

-- Order 4 Items
INSERT INTO order_item (order_id, product_id, quantity, amount) 
VALUES (4, 3, 2, 600), -- 2 units of product with ID 3
       (4, 7, 1, 400); -- 1 unit of product with ID 7

-- Order 5
INSERT INTO [order] (memberid, total_amount, created_date, last_modified_date) 
VALUES (6, 150, '2022-06-05 11:10:00', '2022-06-05 11:10:00');

-- Order 5 Items
INSERT INTO order_item (order_id, product_id, quantity, amount) 
VALUES (5, 1, 1, 30), -- 1 unit of product with ID 1
       (5, 2, 2, 120); -- 2 units of product with ID 2




	   SELECT order_id, memberid, total_amount, created_date, last_modified_date FROM [order] WHERE 1=1
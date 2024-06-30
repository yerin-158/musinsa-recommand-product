-- 브랜드 데이터 삽입
INSERT INTO brands (name, status, created_at, updated_at) VALUES
('Brand Alpha', 'EXPOSED', NOW(), NOW()),
('Brand Beta', 'EXPOSED', NOW(), NOW()),
('Brand Gamma', 'EXPOSED', NOW(), NOW()),
('Brand Delta', 'EXPOSED', NOW(), NOW()),
('Brand Epsilon', 'EXPOSED', NOW(), NOW()),
('Brand Zeta', 'EXPOSED', NOW(), NOW()),
('Brand Eta', 'EXPOSED', NOW(), NOW()),
('Brand Theta', 'EXPOSED', NOW(), NOW()),
('Brand Iota', 'EXPOSED', NOW(), NOW()),
('Brand Kappa', 'EXPOSED', NOW(), NOW());

-- 카테고리 데이터 삽입
INSERT INTO categories (name, created_at, updated_at) VALUES
('상의', NOW(), NOW()),
('아우터', NOW(), NOW()),
('바지', NOW(), NOW()),
('스니커즈', NOW(), NOW()),
('가방', NOW(), NOW()),
('모자', NOW(), NOW()),
('양말', NOW(), NOW()),
('악세서리', NOW(), NOW());

-- 상품 데이터 삽입
-- Brand Alpha
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(1, 1, 'Alpha Top 1', 10200, 'EXPOSED', NOW(), NOW()),
(1, 1, 'Alpha Top 2', 10550, 'EXPOSED', NOW(), NOW()),
(1, 2, 'Alpha Outer 1', 25500, 'EXPOSED', NOW(), NOW()),
(1, 2, 'Alpha Outer 2', 25800, 'EXPOSED', NOW(), NOW()),
(1, 3, 'Alpha Bottom 1', 15300, 'EXPOSED', NOW(), NOW()),
(1, 3, 'Alpha Bottom 2', 15750, 'EXPOSED', NOW(), NOW()),
(1, 4, 'Alpha Sneakers 1', 30200, 'EXPOSED', NOW(), NOW()),
(1, 4, 'Alpha Sneakers 2', 30600, 'EXPOSED', NOW(), NOW()),
(1, 5, 'Alpha Bag 1', 40200, 'EXPOSED', NOW(), NOW()),
(1, 5, 'Alpha Bag 2', 40850, 'EXPOSED', NOW(), NOW()),
(1, 6, 'Alpha Hat 1', 8200, 'EXPOSED', NOW(), NOW()),
(1, 6, 'Alpha Hat 2', 8500, 'EXPOSED', NOW(), NOW()),
(1, 7, 'Alpha Socks 1', 5100, 'EXPOSED', NOW(), NOW()),
(1, 7, 'Alpha Socks 2', 5300, 'EXPOSED', NOW(), NOW()),
(1, 8, 'Alpha Accessory 1', 7200, 'EXPOSED', NOW(), NOW()),
(1, 8, 'Alpha Accessory 2', 7500, 'EXPOSED', NOW(), NOW());

-- Brand Beta
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(2, 1, 'Beta Top 1', 12200, 'EXPOSED', NOW(), NOW()),
(2, 1, 'Beta Top 2', 12500, 'EXPOSED', NOW(), NOW()),
(2, 2, 'Beta Outer 1', 23200, 'EXPOSED', NOW(), NOW()),
(2, 2, 'Beta Outer 2', 23800, 'EXPOSED', NOW(), NOW()),
(2, 3, 'Beta Bottom 1', 17300, 'EXPOSED', NOW(), NOW()),
(2, 3, 'Beta Bottom 2', 17750, 'EXPOSED', NOW(), NOW()),
(2, 4, 'Beta Sneakers 1', 32200, 'EXPOSED', NOW(), NOW()),
(2, 4, 'Beta Sneakers 2', 32600, 'EXPOSED', NOW(), NOW()),
(2, 5, 'Beta Bag 1', 42200, 'EXPOSED', NOW(), NOW()),
(2, 5, 'Beta Bag 2', 42850, 'EXPOSED', NOW(), NOW()),
(2, 6, 'Beta Hat 1', 9200, 'EXPOSED', NOW(), NOW()),
(2, 6, 'Beta Hat 2', 9500, 'EXPOSED', NOW(), NOW()),
(2, 7, 'Beta Socks 1', 6100, 'EXPOSED', NOW(), NOW()),
(2, 7, 'Beta Socks 2', 6300, 'EXPOSED', NOW(), NOW()),
(2, 8, 'Beta Accessory 1', 8200, 'EXPOSED', NOW(), NOW()),
(2, 8, 'Beta Accessory 2', 8500, 'EXPOSED', NOW(), NOW());

-- Brand Gamma
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(3, 1, 'Gamma Top 1', 14200, 'EXPOSED', NOW(), NOW()),
(3, 1, 'Gamma Top 2', 14500, 'EXPOSED', NOW(), NOW()),
(3, 2, 'Gamma Outer 1', 21200, 'EXPOSED', NOW(), NOW()),
(3, 2, 'Gamma Outer 2', 21800, 'EXPOSED', NOW(), NOW()),
(3, 3, 'Gamma Bottom 1', 19300, 'EXPOSED', NOW(), NOW()),
(3, 3, 'Gamma Bottom 2', 19750, 'EXPOSED', NOW(), NOW()),
(3, 4, 'Gamma Sneakers 1', 34200, 'EXPOSED', NOW(), NOW()),
(3, 4, 'Gamma Sneakers 2', 34600, 'EXPOSED', NOW(), NOW()),
(3, 5, 'Gamma Bag 1', 44200, 'EXPOSED', NOW(), NOW()),
(3, 5, 'Gamma Bag 2', 44850, 'EXPOSED', NOW(), NOW()),
(3, 6, 'Gamma Hat 1', 10200, 'EXPOSED', NOW(), NOW()),
(3, 6, 'Gamma Hat 2', 10500, 'EXPOSED', NOW(), NOW()),
(3, 7, 'Gamma Socks 1', 7100, 'EXPOSED', NOW(), NOW()),
(3, 7, 'Gamma Socks 2', 7300, 'EXPOSED', NOW(), NOW()),
(3, 8, 'Gamma Accessory 1', 9200, 'EXPOSED', NOW(), NOW()),
(3, 8, 'Gamma Accessory 2', 9500, 'EXPOSED', NOW(), NOW());

-- Brand Delta
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(4, 1, 'Delta Top 1', 16200, 'EXPOSED', NOW(), NOW()),
(4, 1, 'Delta Top 2', 16500, 'EXPOSED', NOW(), NOW()),
(4, 2, 'Delta Outer 1', 22200, 'EXPOSED', NOW(), NOW()),
(4, 2, 'Delta Outer 2', 22800, 'EXPOSED', NOW(), NOW()),
(4, 3, 'Delta Bottom 1', 20300, 'EXPOSED', NOW(), NOW()),
(4, 3, 'Delta Bottom 2', 20750, 'EXPOSED', NOW(), NOW()),
(4, 4, 'Delta Sneakers 1', 36200, 'EXPOSED', NOW(), NOW()),
(4, 4, 'Delta Sneakers 2', 36600, 'EXPOSED', NOW(), NOW()),
(4, 5, 'Delta Bag 1', 46200, 'EXPOSED', NOW(), NOW()),
(4, 5, 'Delta Bag 2', 46850, 'EXPOSED', NOW(), NOW()),
(4, 6, 'Delta Hat 1', 11200, 'EXPOSED', NOW(), NOW()),
(4, 6, 'Delta Hat 2', 11500, 'EXPOSED', NOW(), NOW()),
(4, 7, 'Delta Socks 1', 8100, 'EXPOSED', NOW(), NOW()),
(4, 7, 'Delta Socks 2', 8300, 'EXPOSED', NOW(), NOW()),
(4, 8, 'Delta Accessory 1', 10200, 'EXPOSED', NOW(), NOW()),
(4, 8, 'Delta Accessory 2', 10500, 'EXPOSED', NOW(), NOW());

-- Brand Epsilon
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(5, 1, 'Epsilon Top 1', 18200, 'EXPOSED', NOW(), NOW()),
(5, 1, 'Epsilon Top 2', 18500, 'EXPOSED', NOW(), NOW()),
(5, 2, 'Epsilon Outer 1', 24200, 'EXPOSED', NOW(), NOW()),
(5, 2, 'Epsilon Outer 2', 24800, 'EXPOSED', NOW(), NOW()),
(5, 3, 'Epsilon Bottom 1', 22300, 'EXPOSED', NOW(), NOW()),
(5, 3, 'Epsilon Bottom 2', 22750, 'EXPOSED', NOW(), NOW()),
(5, 4, 'Epsilon Sneakers 1', 38200, 'EXPOSED', NOW(), NOW()),
(5, 4, 'Epsilon Sneakers 2', 38600, 'EXPOSED', NOW(), NOW()),
(5, 5, 'Epsilon Bag 1', 48200, 'EXPOSED', NOW(), NOW()),
(5, 5, 'Epsilon Bag 2', 48850, 'EXPOSED', NOW(), NOW()),
(5, 6, 'Epsilon Hat 1', 12200, 'EXPOSED', NOW(), NOW()),
(5, 6, 'Epsilon Hat 2', 12500, 'EXPOSED', NOW(), NOW()),
(5, 7, 'Epsilon Socks 1', 9100, 'EXPOSED', NOW(), NOW()),
(5, 7, 'Epsilon Socks 2', 9300, 'EXPOSED', NOW(), NOW()),
(5, 8, 'Epsilon Accessory 1', 11200, 'EXPOSED', NOW(), NOW()),
(5, 8, 'Epsilon Accessory 2', 11500, 'EXPOSED', NOW(), NOW());

-- Brand Zeta
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(6, 1, 'Zeta Top 1', 20200, 'EXPOSED', NOW(), NOW()),
(6, 1, 'Zeta Top 2', 20500, 'EXPOSED', NOW(), NOW()),
(6, 2, 'Zeta Outer 1', 25200, 'EXPOSED', NOW(), NOW()),
(6, 2, 'Zeta Outer 2', 25800, 'EXPOSED', NOW(), NOW()),
(6, 3, 'Zeta Bottom 1', 24300, 'EXPOSED', NOW(), NOW()),
(6, 3, 'Zeta Bottom 2', 24750, 'EXPOSED', NOW(), NOW()),
(6, 4, 'Zeta Sneakers 1', 40200, 'EXPOSED', NOW(), NOW()),
(6, 4, 'Zeta Sneakers 2', 40600, 'EXPOSED', NOW(), NOW()),
(6, 5, 'Zeta Bag 1', 50200, 'EXPOSED', NOW(), NOW()),
(6, 5, 'Zeta Bag 2', 50850, 'EXPOSED', NOW(), NOW()),
(6, 6, 'Zeta Hat 1', 13200, 'EXPOSED', NOW(), NOW()),
(6, 6, 'Zeta Hat 2', 13500, 'EXPOSED', NOW(), NOW()),
(6, 7, 'Zeta Socks 1', 10100, 'EXPOSED', NOW(), NOW()),
(6, 7, 'Zeta Socks 2', 10300, 'EXPOSED', NOW(), NOW()),
(6, 8, 'Zeta Accessory 1', 12200, 'EXPOSED', NOW(), NOW()),
(6, 8, 'Zeta Accessory 2', 12500, 'EXPOSED', NOW(), NOW());

-- Brand Eta
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(7, 1, 'Eta Top 1', 22200, 'EXPOSED', NOW(), NOW()),
(7, 1, 'Eta Top 2', 22500, 'EXPOSED', NOW(), NOW()),
(7, 2, 'Eta Outer 1', 27200, 'EXPOSED', NOW(), NOW()),
(7, 2, 'Eta Outer 2', 27800, 'EXPOSED', NOW(), NOW()),
(7, 3, 'Eta Bottom 1', 26300, 'EXPOSED', NOW(), NOW()),
(7, 3, 'Eta Bottom 2', 26750, 'EXPOSED', NOW(), NOW()),
(7, 4, 'Eta Sneakers 1', 42200, 'EXPOSED', NOW(), NOW()),
(7, 4, 'Eta Sneakers 2', 42600, 'EXPOSED', NOW(), NOW()),
(7, 5, 'Eta Bag 1', 52200, 'EXPOSED', NOW(), NOW()),
(7, 5, 'Eta Bag 2', 52850, 'EXPOSED', NOW(), NOW()),
(7, 6, 'Eta Hat 1', 14200, 'EXPOSED', NOW(), NOW()),
(7, 6, 'Eta Hat 2', 14500, 'EXPOSED', NOW(), NOW()),
(7, 7, 'Eta Socks 1', 11100, 'EXPOSED', NOW(), NOW()),
(7, 7, 'Eta Socks 2', 11300, 'EXPOSED', NOW(), NOW()),
(7, 8, 'Eta Accessory 1', 13200, 'EXPOSED', NOW(), NOW()),
(7, 8, 'Eta Accessory 2', 13500, 'EXPOSED', NOW(), NOW());

-- Brand Theta
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(8, 1, 'Theta Top 1', 24200, 'EXPOSED', NOW(), NOW()),
(8, 1, 'Theta Top 2', 24500, 'EXPOSED', NOW(), NOW()),
(8, 2, 'Theta Outer 1', 28200, 'EXPOSED', NOW(), NOW()),
(8, 2, 'Theta Outer 2', 28800, 'EXPOSED', NOW(), NOW()),
(8, 3, 'Theta Bottom 1', 28300, 'EXPOSED', NOW(), NOW()),
(8, 3, 'Theta Bottom 2', 28750, 'EXPOSED', NOW(), NOW()),
(8, 4, 'Theta Sneakers 1', 44200, 'EXPOSED', NOW(), NOW()),
(8, 4, 'Theta Sneakers 2', 44600, 'EXPOSED', NOW(), NOW()),
(8, 5, 'Theta Bag 1', 54200, 'EXPOSED', NOW(), NOW()),
(8, 5, 'Theta Bag 2', 54850, 'EXPOSED', NOW(), NOW()),
(8, 6, 'Theta Hat 1', 15200, 'EXPOSED', NOW(), NOW()),
(8, 6, 'Theta Hat 2', 15500, 'EXPOSED', NOW(), NOW()),
(8, 7, 'Theta Socks 1', 12100, 'EXPOSED', NOW(), NOW()),
(8, 7, 'Theta Socks 2', 12300, 'EXPOSED', NOW(), NOW()),
(8, 8, 'Theta Accessory 1', 14200, 'EXPOSED', NOW(), NOW()),
(8, 8, 'Theta Accessory 2', 14500, 'EXPOSED', NOW(), NOW());

-- Brand Iota
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(9, 1, 'Iota Top 1', 26400, 'EXPOSED', NOW(), NOW()),
(9, 1, 'Iota Top 2', 26700, 'EXPOSED', NOW(), NOW()),
(9, 2, 'Iota Outer 1', 28700, 'EXPOSED', NOW(), NOW()),
(9, 2, 'Iota Outer 2', 29300, 'EXPOSED', NOW(), NOW()),
(9, 3, 'Iota Bottom 1', 32000, 'EXPOSED', NOW(), NOW()),
(9, 3, 'Iota Bottom 2', 32450, 'EXPOSED', NOW(), NOW()),
(9, 4, 'Iota Sneakers 1', 45200, 'EXPOSED', NOW(), NOW()),
(9, 4, 'Iota Sneakers 2', 45600, 'EXPOSED', NOW(), NOW()),
(9, 5, 'Iota Bag 1', 53200, 'EXPOSED', NOW(), NOW()),
(9, 5, 'Iota Bag 2', 53850, 'EXPOSED', NOW(), NOW()),
(9, 6, 'Iota Hat 1', 16700, 'EXPOSED', NOW(), NOW()),
(9, 6, 'Iota Hat 2', 17000, 'EXPOSED', NOW(), NOW()),
(9, 7, 'Iota Socks 1', 14000, 'EXPOSED', NOW(), NOW()),
(9, 7, 'Iota Socks 2', 14200, 'EXPOSED', NOW(), NOW()),
(9, 8, 'Iota Accessory 1', 15400, 'EXPOSED', NOW(), NOW()),
(9, 8, 'Iota Accessory 2', 15700, 'EXPOSED', NOW(), NOW());

-- Brand Kappa
INSERT INTO products (brand_id, category_id, name, price, status, created_at, updated_at) VALUES
(10, 1, 'Kappa Top 1', 27700, 'EXPOSED', NOW(), NOW()),
(10, 1, 'Kappa Top 2', 28000, 'EXPOSED', NOW(), NOW()),
(10, 2, 'Kappa Outer 1', 31200, 'EXPOSED', NOW(), NOW()),
(10, 2, 'Kappa Outer 2', 31800, 'EXPOSED', NOW(), NOW()),
(10, 3, 'Kappa Bottom 1', 30700, 'EXPOSED', NOW(), NOW()),
(10, 3, 'Kappa Bottom 2', 31150, 'EXPOSED', NOW(), NOW()),
(10, 4, 'Kappa Sneakers 1', 47200, 'EXPOSED', NOW(), NOW()),
(10, 4, 'Kappa Sneakers 2', 47600, 'EXPOSED', NOW(), NOW()),
(10, 5, 'Kappa Bag 1', 59200, 'EXPOSED', NOW(), NOW()),
(10, 5, 'Kappa Bag 2', 59850, 'EXPOSED', NOW(), NOW()),
(10, 6, 'Kappa Hat 1', 17400, 'EXPOSED', NOW(), NOW()),
(10, 6, 'Kappa Hat 2', 17700, 'EXPOSED', NOW(), NOW()),
(10, 7, 'Kappa Socks 1', 14500, 'EXPOSED', NOW(), NOW()),
(10, 7, 'Kappa Socks 2', 14700, 'EXPOSED', NOW(), NOW()),
(10, 8, 'Kappa Accessory 1', 16000, 'EXPOSED', NOW(), NOW()),
(10, 8, 'Kappa Accessory 2', 16300, 'EXPOSED', NOW(), NOW());

-- 가격 통계 데이터 삽입
-- Brand Alpha
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(1, 1, 2, 1, 10550, 10200, NOW(), NOW()),
(1, 2, 4, 3, 25800, 25500, NOW(), NOW()),
(1, 3, 6, 5, 15750, 15300, NOW(), NOW()),
(1, 4, 8, 7, 30600, 30200, NOW(), NOW()),
(1, 5, 10, 9, 40850, 40200, NOW(), NOW()),
(1, 6, 12, 11, 8500, 8200, NOW(), NOW()),
(1, 7, 14, 13, 5300, 5100, NOW(), NOW()),
(1, 8, 16, 15, 7500, 7200, NOW(), NOW());

-- Brand Beta
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(2, 1, 18, 17, 12500, 12200, NOW(), NOW()),
(2, 2, 20, 19, 23800, 23200, NOW(), NOW()),
(2, 3, 22, 21, 17750, 17300, NOW(), NOW()),
(2, 4, 24, 23, 32600, 32200, NOW(), NOW()),
(2, 5, 26, 25, 42850, 42200, NOW(), NOW()),
(2, 6, 28, 27, 9500, 9200, NOW(), NOW()),
(2, 7, 30, 29, 6300, 6100, NOW(), NOW()),
(2, 8, 32, 31, 8500, 8200, NOW(), NOW());

-- Brand Gamma
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(3, 1, 34, 33, 14500, 14200, NOW(), NOW()),
(3, 2, 36, 35, 21800, 21200, NOW(), NOW()),
(3, 3, 38, 37, 19750, 19300, NOW(), NOW()),
(3, 4, 40, 39, 34600, 34200, NOW(), NOW()),
(3, 5, 42, 41, 44850, 44200, NOW(), NOW()),
(3, 6, 44, 43, 10500, 10200, NOW(), NOW()),
(3, 7, 46, 45, 7300, 7100, NOW(), NOW()),
(3, 8, 48, 47, 9500, 9200, NOW(), NOW());

-- Brand Delta
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(4, 1, 50, 49, 16500, 16200, NOW(), NOW()),
(4, 2, 52, 51, 22800, 22200, NOW(), NOW()),
(4, 3, 54, 53, 20750, 20300, NOW(), NOW()),
(4, 4, 56, 55, 36600, 36200, NOW(), NOW()),
(4, 5, 58, 57, 46850, 46200, NOW(), NOW()),
(4, 6, 60, 59, 11500, 11200, NOW(), NOW()),
(4, 7, 62, 61, 8300, 8100, NOW(), NOW()),
(4, 8, 64, 63, 10500, 10200, NOW(), NOW());

-- Brand Epsilon
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(5, 1, 66, 65, 18500, 18200, NOW(), NOW()),
(5, 2, 68, 67, 24800, 24200, NOW(), NOW()),
(5, 3, 70, 69, 22750, 22300, NOW(), NOW()),
(5, 4, 72, 71, 38600, 38200, NOW(), NOW()),
(5, 5, 74, 73, 48850, 48200, NOW(), NOW()),
(5, 6, 76, 75, 12500, 12200, NOW(), NOW()),
(5, 7, 78, 77, 9300, 9100, NOW(), NOW()),
(5, 8, 80, 79, 11500, 11200, NOW(), NOW());

-- Brand Zeta
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(6, 1, 82, 81, 20500, 20200, NOW(), NOW()),
(6, 2, 84, 83, 25800, 25200, NOW(), NOW()),
(6, 3, 86, 85, 24750, 24300, NOW(), NOW()),
(6, 4, 88, 87, 40600, 40200, NOW(), NOW()),
(6, 5, 90, 89, 50850, 50200, NOW(), NOW()),
(6, 6, 92, 91, 13500, 13200, NOW(), NOW()),
(6, 7, 94, 93, 10300, 10100, NOW(), NOW()),
(6, 8, 96, 95, 12500, 12200, NOW(), NOW());

-- Brand Eta
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(7, 1, 98, 97, 22500, 22200, NOW(), NOW()),
(7, 2, 100, 99, 27800, 27200, NOW(), NOW()),
(7, 3, 102, 101, 26750, 26300, NOW(), NOW()),
(7, 4, 104, 103, 42600, 42200, NOW(), NOW()),
(7, 5, 106, 105, 52850, 52200, NOW(), NOW()),
(7, 6, 108, 107, 14500, 14200, NOW(), NOW()),
(7, 7, 110, 109, 11300, 11100, NOW(), NOW()),
(7, 8, 112, 111, 13500, 13200, NOW(), NOW());

-- Brand Theta
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(8, 1, 114, 113, 24500, 24200, NOW(), NOW()),
(8, 2, 116, 115, 28800, 28200, NOW(), NOW()),
(8, 3, 118, 117, 28750, 28300, NOW(), NOW()),
(8, 4, 120, 119, 44600, 44200, NOW(), NOW()),
(8, 5, 122, 121, 54850, 54200, NOW(), NOW()),
(8, 6, 124, 123, 15500, 15200, NOW(), NOW()),
(8, 7, 126, 125, 12300, 12100, NOW(), NOW()),
(8, 8, 128, 127, 14500, 14200, NOW(), NOW());

-- Brand Iota
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(9, 1, 130, 129, 26700, 26400, NOW(), NOW()),
(9, 2, 132, 131, 29300, 28700, NOW(), NOW()),
(9, 3, 134, 133, 32450, 32000, NOW(), NOW()),
(9, 4, 136, 135, 45600, 45200, NOW(), NOW()),
(9, 5, 138, 137, 53850, 53200, NOW(), NOW()),
(9, 6, 140, 139, 17000, 16700, NOW(), NOW()),
(9, 7, 142, 141, 14200, 14000, NOW(), NOW()),
(9, 8, 144, 143, 15700, 15400, NOW(), NOW());

-- Brand Kappa
INSERT INTO price_statistics (brand_id, category_id, highest_price_product_id, lowest_price_product_id, highest_price, lowest_price, created_at, updated_at) VALUES
(10, 1, 146, 145, 28000, 27700, NOW(), NOW()),
(10, 2, 148, 147, 31800, 31200, NOW(), NOW()),
(10, 3, 150, 149, 31150, 30700, NOW(), NOW()),
(10, 4, 152, 151, 47600, 47200, NOW(), NOW()),
(10, 5, 154, 153, 59850, 59200, NOW(), NOW()),
(10, 6, 156, 155, 17700, 17400, NOW(), NOW()),
(10, 7, 158, 157, 14700, 14500, NOW(), NOW()),
(10, 8, 160, 159, 16300, 16000, NOW(), NOW());

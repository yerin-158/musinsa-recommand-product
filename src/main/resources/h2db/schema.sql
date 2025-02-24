-- Drop existing tables if they exist
DROP TABLE IF EXISTS price_statistics;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS categories;

-- 브랜드 테이블
CREATE TABLE IF NOT EXISTS brands (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- pk
    name VARCHAR(40) NOT NULL UNIQUE,  -- unique
    status VARCHAR(20) NOT NULL,  -- enum: NOT_EXPOSED, EXPOSED, ADMIN_HIDDEN, DELETED
    created_at DATETIME NOT NULL,  -- 생성일
    updated_at DATETIME NOT NULL  -- 수정일
);

-- 상품 테이블
CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- pk
    brand_id BIGINT NOT NULL,  -- 브랜드 아이디
    category_id BIGINT,  -- 카테고리 아이디
    name VARCHAR(100),  -- 상품명
    price INT,  -- 가격
    status VARCHAR(25) NOT NULL,  -- enum: DRAFT, EXPOSED, ADMIN_HIDDEN, SOLD_OUT, DELETED
    created_at DATETIME NOT NULL,  -- 생성일
    updated_at DATETIME NOT NULL  -- 수정일
);
CREATE INDEX IF NOT EXISTS idx_products_brand_id ON products(brand_id);  -- 단일 인덱스

-- 카테고리 테이블
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- pk
    name VARCHAR(50) NOT NULL,  -- 프론트에 출력될 이름
    created_at DATETIME NOT NULL,  -- 생성일
    updated_at DATETIME NOT NULL  -- 수정일
);

-- 가격 통계 테이블
CREATE TABLE IF NOT EXISTS price_statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- pk
    brand_id BIGINT NOT NULL,  -- 브랜드 id
    category_id BIGINT NOT NULL,  -- 카테고리 id
    highest_price_product_id BIGINT,  -- 제일 비싼 상품 id
    lowest_price_product_id BIGINT,  -- 제일 저렴한 상품 id
    highest_price INT, -- 제일 비싼 상품 가격
    lowest_price INT, -- 제일 저렴한 상품 가격
    created_at DATETIME NOT NULL,  -- 생성일
    updated_at DATETIME NOT NULL,  -- 수정일
    UNIQUE (brand_id, category_id) -- 유니크 (brand_id, category_id)
);
CREATE INDEX IF NOT EXISTS idx_price_statistics_brand_id ON price_statistics(brand_id);  -- 단일 인덱스
CREATE INDEX IF NOT EXISTS idx_price_statistics_category_id ON price_statistics(category_id);  -- 단일 인덱스
CREATE INDEX IF NOT EXISTS idx_category_lowest_price ON price_statistics (category_id, lowest_price);

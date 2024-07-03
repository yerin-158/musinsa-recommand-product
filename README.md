# 손예린 무신사 과제 (24.07.03)
***
## 스펙
### 서버
- java
- spring boot 3
- spring-mvc
- h2db
- jpa
- mapstruct
- (test) rest-assured

### 프론트
- react
- typescript
- axios
- functional component

***
## Swagger
http://localhost:8080/musinsa-api-docs/v1/swagger-ui/index.html
***
## 서버 실행
빌드 시 테스트가 함께 실행됩니다.
```
./gradlew clean build
java -jar build/libs/musinsa-recommand-product-0.0.1-SNAPSHOT.jar
```

### 서버 확인
```
curl -XGET http://localhost:8080/api/v1/categories
```

## 프론트 실행
```
cd frontend
npm i
npm start
```
***
# 데이터베이스 스키마

이 문서는 Musinsa Recommend Product 애플리케이션의 데이터베이스 스키마에 대해 설명합니다.

## 테이블

데이터베이스는 네 개의 주요 테이블로 구성됩니다:

1. **brands**
2. **products**
3. **categories**
4. **price_statistics**

### 1. brands

브랜드 정보를 저장하는 테이블입니다.

| 컬럼명       | 데이터 타입 | 제약 조건                               | 설명                      |
|--------------|-------------|-----------------------------------------|---------------------------|
| id           | BIGINT      | NOT NULL AUTO_INCREMENT PRIMARY KEY     | 기본 키                   |
| name         | VARCHAR(40) | NOT NULL UNIQUE                         | 브랜드 이름 (고유)        |
| status       | VARCHAR(20) | NOT NULL                                | 브랜드 상태               |
| created_at   | DATETIME    | NOT NULL                                | 생성일                    |
| updated_at   | DATETIME    | NOT NULL                                | 수정일                    |

### 2. products

상품 정보를 저장하는 테이블입니다.

| 컬럼명       | 데이터 타입 | 제약 조건                               | 설명                      |
|--------------|-------------|-----------------------------------------|---------------------------|
| id           | BIGINT      | NOT NULL AUTO_INCREMENT PRIMARY KEY     | 기본 키                   |
| brand_id     | BIGINT      | NOT NULL                                | 외래 키 (brands)          |
| category_id  | BIGINT      |                                         | 외래 키 (categories)      |
| name         | VARCHAR(100)|                                         | 상품명                    |
| price        | INT         |                                         | 가격                      |
| status       | VARCHAR(25) | NOT NULL                                | 상품 상태                 |
| created_at   | DATETIME    | NOT NULL                                | 생성일                    |
| updated_at   | DATETIME    | NOT NULL                                | 수정일                    |

인덱스:

- `idx_products_brand_id` on `brand_id`

### 3. categories

카테고리 정보를 저장하는 테이블입니다.

| 컬럼명       | 데이터 타입 | 제약 조건                               | 설명                      |
|--------------|-------------|-----------------------------------------|---------------------------|
| id           | BIGINT      | NOT NULL AUTO_INCREMENT PRIMARY KEY     | 기본 키                   |
| name         | VARCHAR(50) | NOT NULL                                | 카테고리 이름             |
| created_at   | DATETIME    | NOT NULL                                | 생성일                    |
| updated_at   | DATETIME    | NOT NULL                                | 수정일                    |

### 4. price_statistics

각 브랜드와 카테고리별 가격 통계를 저장하는 테이블입니다.

| 컬럼명                   | 데이터 타입 | 제약 조건                               | 설명                              |
|--------------------------|-------------|-----------------------------------------|-----------------------------------|
| id                       | BIGINT      | AUTO_INCREMENT PRIMARY KEY              | 기본 키                           |
| brand_id                 | BIGINT      | NOT NULL                                | 외래 키 (brands)                  |
| category_id              | BIGINT      | NOT NULL                                | 외래 키 (categories)              |
| highest_price_product_id | BIGINT      |                                         | 최고가 상품 ID                    |
| lowest_price_product_id  | BIGINT      |                                         | 최저가 상품 ID                    |
| highest_price            | INT         |                                         | 최고가                            |
| lowest_price             | INT         |                                         | 최저가                            |
| created_at               | DATETIME    | NOT NULL                                | 생성일                            |
| updated_at               | DATETIME    | NOT NULL                                | 수정일                            |

인덱스:

- `idx_price_statistics_brand_id` on `brand_id`
- `idx_price_statistics_category_id` on `category_id`
- `idx_category_lowest_price` on `category_id, lowest_price`


### 설명

- `brands` 테이블은 브랜드에 대한 정보를 저장합니다.
- `products` 테이블은 각 브랜드의 상품 정보를 저장하며, 카테고리 정보도 포함합니다.
- `categories` 테이블은 상품의 카테고리를 정의합니다.
- `price_statistics` 테이블은 각 브랜드와 카테고리 조합에 대한 최고가 및 최저가 상품 정보를 저장합니다.

모든 테이블은 `created_at` 및 `updated_at` 컬럼을 통해 레코드의 생성 및 수정 시간을 추적합니다.

## 초기화 스크립트

데이터베이스 스키마 및 데이터를 초기화하는 데 사용되는 `schema.sql` 및 `data.sql` 스크립트가 제공됩니다.

- `schema.sql`: 테이블 및 인덱스를 생성하는 SQL 문을 포함합니다.
- `data.sql`: 테이블에 초기 데이터를 삽입하는 SQL 문을 포함합니다.

애플리케이션 구성(`application.yml`)은 이러한 스크립트가 실행되어 테스트를 위한 인메모리 H2 데이터베이스를 설정하도록 보장합니다.

```yml
spring:
  application:
    name: musinsa-recommand-product
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: admin
    password: admin
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
      path: /h2-console
  sql:
    init:
      schema-locations: classpath:h2db/schema.sql
      data-locations: classpath:h2db/data.sql


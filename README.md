# 손예린 무신사 과제 (24.07.03)
### ✅ 1.스웨거 2.통합테스트 코드 3.웹프론트 클라이언트로 테스트 해 보실 수 있습니다.
![image](https://github.com/yerin-158/musinsa-recommand-product/assets/63404226/3e9afeb1-07fc-4b2d-96bc-b04eb7e12a02)

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
- react, functional component
- node v16.16.0
- npm 8.11.0
- typescript
- axios
- css, HTML

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

## Swagger
http://localhost:8080/musinsa-api-docs/v1/swagger-ui/index.html

## 프론트 실행
```
cd frontend
npm i
npm start
```

### 프론트 진입
http://localhost:3000/

***
# 기능 및 API
## ✅ 기능 설명
프로젝트에서는 다음과 같은 주요 기능을 추가하였습니다:
1. **(어드민) 브랜드 추가 및 관리**
    1. 새로운 브랜드를 추가
    2. 브랜드 상태 변경 (노출/미노출)
    3. 전체 브랜드 확인
2. **(어드민) 상품 추가 및 관리**
    1. 특정 브랜드에 상품을 추가
    2. 상품명, 가격, 카테고리 수정
    3. (조건 만족 시) 상품 삭제
    4. 특정 브랜드의 모든 상품을 조회
3. **카테고리 관리**
    1. 모든 카테고리를 조회하는 기능을 구현하였습니다.
4. **추천 제품 조회**
    1. 브랜드 상관없이 최저가로 이루어진 상품 세트와 총 금액 조회
    2. 특정 브랜드 선택 시 해당 브랜드의 최저가 상품들로 이루어진 상품 세트와 총 금액 조회
    3. 카테고리별로 가장 비싼 상품과 가장 저렴한 상품을 조회
5. **(backend 구현) 가격 통계 관리**
    1. 브랜드, 카테고리별로 가장 비싼 상품과 가장 저렴한 상품을 관리하고 요청 시 빠르게 응답할 수 있게 처리
    2. 유저에게는 노출 중인 브랜드의 상품만 추천 대상이 되도록 관리

## ✅ 추가 구현 범위
1. Integration test (rest-assured 사용)
2. Frontend 페이지 구현

## ✅ 상세 구현
1. 브랜드 추가시 'NOT_EXPOSED' 상태로 저장되며 각 카테고리당 상품이 최소 하나가 존재하기 전까지 'EXPOSED'로 변경할 수 없습니다.
2. **'EXPOSED(노출)' 상태가 아닌 브랜드는 [스토어]에 노출되지 않습니다.**
    1. 브랜드 목록에 미노출
    2. 가장 저렴한 상품 세트에 해당 브랜드의 상품은 포함될 수 없음
    3. 카테고리별 최대/최소가격 상품에 포함될 수 없음
3. 카테고리당 상품이 최소 하나씩 추가되면 'EXPOSED'로 변경할 수 있습니다.
4. 그 외에도 브랜드와 상품은 각자 상태를 가지고 있습니다.
5. 상품이 비노출 상태로 변경되는 경우, '카테고리당 최사 하나의 상품이 존재'해야한다는 조건이 있어 한 브랜드의 각 카테고리별 마지막 상품은 비노출로 변경이 불가합니다.
6. 가격 통계 테이블은 (브랜드, 카테고리)를 unique로 사용하며 최대/최소값을 가져올 때 빠르게 서치 할 수 있습니다. (아래 테이블 설계 참고)

## Admin Product API

### [POST] /admin/api/v1/brands
**브랜드 추가**
- 새로운 브랜드를 추가합니다.
- 요청 본문: `AdminBrandAddRequest`
- 응답 본문: `AdminBrandResponse`

### [GET] /admin/api/v1/brands
**모든 브랜드 조회**
- 모든 브랜드를 가져옵니다. (페이징X)
- 응답 본문: `List<AdminBrandResponse>`

### [GET] /admin/api/v1/brands/{id}
**브랜드 조회**
- ID로 브랜드를 조회합니다.
- 응답 본문: `AdminBrandResponse`

### [PUT] /admin/api/v1/brands/{id}/status
**브랜드의 상태 변경**
- 브랜드 ID로 특정 브랜드의 상태를 변경합니다.
- 요청 본문: `AdminBrandStatusModifyRequest`
- 응답 본문: `AdminBrandResponse`

### [POST] /admin/api/v1/brands/{id}/products
**브랜드에 제품 추가**
- 브랜드 ID로 제품을 추가합니다.
- 요청 본문: `AdminProductAddRequest`
- 응답 본문: `AdminProductResponse`

### [GET] /admin/api/v1/brands/{id}/products/{productId}
**브랜드의 특정 제품 조회**
- 브랜드 ID와 제품 ID로 특정 제품을 조회합니다.
- 응답 본문: `AdminProductResponse`

### [GET] /admin/api/v1/brands/{id}/products
**브랜드의 모든 제품 조회**
- 브랜드 ID로 모든 제품을 조회합니다.
- 응답 본문: `PageResponse<AdminProductResponse>`

### [PUT] /admin/api/v1/brands/{id}/products/{productId}
**브랜드의 특정 제품 수정**
- 브랜드 ID와 제품 ID로 특정 제품을 수정합니다.
- 요청 본문: `AdminProductModifyRequest`
- 응답 본문: `AdminProductResponse`

### [PUT] /admin/api/v1/brands/{id}/products/{productId}/status
**브랜드의 특정 제품 상태 수정**
- 브랜드 ID와 제품 ID로 특정 제품의 상태를 수정합니다.
- 요청 본문: `AdminProductStatusModifyRequest`
- 응답 본문: `AdminProductResponse`

### [DELETE] /admin/api/v1/brands/{id}/products/{productId}
**브랜드의 특정 제품 삭제**
- 브랜드 ID와 제품 ID로 특정 제품을 삭제합니다.
- 응답 본문: `AdminProductResponse`

## Category API

### [GET] /api/v1/categories
**카테고리 리스트 가져오기**
- 모든 카테고리를 조회합니다.
- 응답 본문: `List<CategorySimpleResponse>`

## Product API

### [GET] /api/v1/products/summary
**카테고리별 제품 요약 정보 조회**
- 카테고리 ID와 가격 유형을 기준으로 제품 요약 정보를 조회합니다.
- 응답 본문: `ProductByCategoryResponse`

## Recommend API

### [GET] /api/v1/recommend/products
**추천 제품 조회**
- 특정 조건에 따라 추천 제품 목록을 조회합니다.
- 응답 본문: `ProductSetResponse`

# 데이터베이스 스키마

Musinsa Recommend Product 애플리케이션의 데이터베이스 스키마에 대해 설명합니다.

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
```

# 패키지 구조
패키지 구조에 대해 설명합니다.
```
.
├── java
│   └── com
│       └── example
│           └── musinsarecommandproduct
│               ├── MusinsaRecommandProductApplication.java
│               ├── configuration
│               │   └── WebConfig.java
│               ├── controller
│               │   ├── admin
│               │   │   ├── AdminBrandController.java
│               │   │   ├── dto
│               │   │   ├── interfaces
│               │   │   └── mapper
│               │   └── store
│               │       ├── CategoryController.java
│               │       ├── ProductController.java
│               │       ├── RecommendController.java
│               │       ├── dto
│               │       ├── interfaces
│               │       └── mapper
│               ├── entitie
│               │   ├── BaseEntity.java
│               │   ├── Brand.java
│               │   ├── Category.java
│               │   ├── PriceStatistics.java
│               │   ├── Product.java
│               │   └── specs
│               │       ├── BrandSpecs.java
│               │       ├── PriceStatisticsSpecs.java
│               │       └── ProductSpecs.java
│               ├── enums
│               │   ├── BrandStatus.java
│               │   ├── PriceType.java
│               │   └── ProductStatus.java
│               ├── exception
│               │   ├── BadRequestException.java
│               │   └── BadRequestType.java
│               ├── handler
│               │   └── CustomExceptionHandler.java
│               ├── repository
│               │   ├── BrandRepository.java
│               │   ├── CategoryRepository.java
│               │   ├── PriceStatisticsRepository.java
│               │   └── ProductRepository.java
│               └── service
│                   ├── admin
│                   │   ├── AdminBrandService.java
│                   │   ├── AdminPriceStatisticsService.java
│                   │   ├── AdminProductService.java
│                   │   └── validator
│                   └── store
│                       ├── BrandService.java
│                       ├── CategoryService.java
│                       ├── PriceStatisticsService.java
│                       ├── ProductFacade.java
│                       ├── ProductService.java
│                       └── RecommendService.java
└── resources
    ├── application.yml
    ├── h2db
    │   ├── data.sql
    │   └── schema.sql
    ├── static
    └── templates
```

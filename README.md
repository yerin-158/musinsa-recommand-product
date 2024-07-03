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

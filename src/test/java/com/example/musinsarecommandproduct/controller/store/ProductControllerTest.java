package com.example.musinsarecommandproduct.controller.store;

import com.example.musinsarecommandproduct.IntegrationTest;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductControllerTest extends IntegrationTest {

  @Test
  @Description("카테고리 1(상의)의 가장 저렴한 상품과 가장 비싼 상품을 가져오는 api")
  public void best_case_test1() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("categoryId", 1)
        .queryParam("priceType", List.of(PriceType.LOW, PriceType.HIGH))
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("category.id", equalTo(1))
        .body("category.name", equalTo("상의"))
        .body("lowestPriceProducts.$", hasSize(1))
        .body("lowestPriceProducts[0].category", notNullValue())
        .body("lowestPriceProducts[0].brand", notNullValue())
        .body("lowestPriceProducts[0].product", notNullValue())
        .body("highestPriceProducts.$", hasSize(1))
        .body("highestPriceProducts[0].category", notNullValue())
        .body("highestPriceProducts[0].brand", notNullValue())
        .body("highestPriceProducts[0].product", notNullValue());
  }

  @Test
  @Description("카테고리 1(상의)의 가장 저렴한 상품과 가장 비싼 상품을 가져오는 api - size 5개 지정")
  public void best_case_test2() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("categoryId", 1)
        .queryParam("priceType", List.of(PriceType.LOW, PriceType.HIGH))
        .queryParam("size", 5)
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("category.id", equalTo(1))
        .body("category.name", equalTo("상의"))
        .body("lowestPriceProducts.$", hasSize(5))
        .body("highestPriceProducts.$", hasSize(5));
  }

  @Test
  @Description("카테고리 1(상의)의 가장 저렴한 상품만 가져오는 api")
  public void best_case_test3() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("categoryId", 1)
        .queryParam("priceType", List.of(PriceType.LOW))
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("category.id", equalTo(1))
        .body("category.name", equalTo("상의"))
        .body("lowestPriceProducts.$", hasSize(1))
        .body("lowestPriceProducts[0].category", notNullValue())
        .body("lowestPriceProducts[0].brand", notNullValue())
        .body("lowestPriceProducts[0].product", notNullValue())
        .body("highestPriceProducts.$", hasSize(0));
  }

  @Test
  @Description("카테고리 1(상의)의 가장 비싼 상품만 가져오는 api")
  public void best_case_test4() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("categoryId", 1)
        .queryParam("priceType", List.of(PriceType.HIGH))
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("category.id", equalTo(1))
        .body("category.name", equalTo("상의"))
        .body("lowestPriceProducts.$", hasSize(0))
        .body("highestPriceProducts.$", hasSize(1))
        .body("highestPriceProducts[0].category", notNullValue())
        .body("highestPriceProducts[0].brand", notNullValue())
        .body("highestPriceProducts[0].product", notNullValue());
  }

  @Test
  @Description("categoryId를 입력하지 않은 경우 에러 발생")
  public void required_query_param_test1() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("priceType", List.of(PriceType.LOW, PriceType.HIGH))
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.INVALID_REQUEST.getMessage()))
        .body("code", equalTo(BadRequestType.INVALID_REQUEST.getCode()));
  }

  @Test
  @Description("priceType 입력하지 않은 경우 에러 발생")
  public void required_query_param_test2() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("categoryId", 1)
    .when()
        .log().uri()
        .get("/api/v1/products/summary")
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.INVALID_REQUEST.getMessage()))
        .body("code", equalTo(BadRequestType.INVALID_REQUEST.getCode()));

  }

}

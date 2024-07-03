package com.example.musinsarecommandproduct.controller.store;

import com.example.musinsarecommandproduct.IntegrationTest;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecommendControllerTest extends IntegrationTest {

  @Test
  @Description("브랜드 선택없이 최저값으로만 구성된 셋트를 가져오는 api")
  public void best_case_test1() {
    Response response = given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/api/v1/recommend/lowest-price-set")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract().response();

    // 각 카테고리들이 중복없이 하나씩 들어있음
    List<Integer> categoryIds = response.jsonPath().getList("products.category.id");
    Set<Integer> uniqueCategoryIds = new HashSet<>(categoryIds);
    assertEquals(categoryIds.size(), uniqueCategoryIds.size());

    // 각 상품들의 합 확인
    List<Integer> prices = response.jsonPath().getList("products.product.price");
    int sumPrice = response.jsonPath().getInt("sumPrice");
    int calculatedSumPrice = prices.stream().mapToInt(Integer::intValue).sum();
    assertEquals(sumPrice, calculatedSumPrice);

    // 각 상품들의 브랜드가 단일 브랜드가 아님을 확인
    List<Integer> brandIds = response.jsonPath().getList("products.brand.id");
    Set<Integer> uniqueBrandIds = new HashSet<>(brandIds);
    assertTrue(uniqueBrandIds.size() > 1);
  }

  @Test
  @Description("브랜드 선택하여 최저값으로 구성된 셋트를 가져오는 api")
  public void best_case_test2() {
    Integer brandId = 1;

    Response response = given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("byBrand", true)
        .queryParam("brandId", brandId)
    .when()
        .log().uri()
        .get("/api/v1/recommend/lowest-price-set")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract().response();

    // 각 카테고리들이 중복없이 하나씩 들어있음
    List<Integer> categoryIds = response.jsonPath().getList("products.category.id");
    Set<Integer> uniqueCategoryIds = new HashSet<>(categoryIds);
    assertEquals(categoryIds.size(), uniqueCategoryIds.size());

    // 각 상품들의 합 확인
    List<Integer> prices = response.jsonPath().getList("products.product.price");
    int sumPrice = response.jsonPath().getInt("sumPrice");
    int calculatedSumPrice = prices.stream().mapToInt(Integer::intValue).sum();
    assertEquals(sumPrice, calculatedSumPrice);

    // 각 상품들의 브랜드가 지정한 브랜드임을 확인
    List<Integer> brandIds = response.jsonPath().getList("products.brand.id");
    Set<Integer> uniqueBrandIds = new HashSet<>(brandIds);
    assertEquals(uniqueBrandIds.size(), 1);
    assertEquals(uniqueBrandIds.toArray()[0], brandId);
  }

  @Test
  @Description("브랜드 선택하는 옵션이나, 브랜드 아이디가 포함되지 않는 경우 에러 응답")
  public void required_query_param_test1() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("byBrand", true)
    .when()
        .log().uri()
        .get("/api/v1/recommend/lowest-price-set")
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.INVALID_REQUEST.getMessage()))
        .body("code", equalTo(BadRequestType.INVALID_REQUEST.getCode()));
  }

}

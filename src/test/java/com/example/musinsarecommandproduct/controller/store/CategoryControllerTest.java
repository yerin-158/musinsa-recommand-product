package com.example.musinsarecommandproduct.controller.store;

import com.example.musinsarecommandproduct.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CategoryControllerTest extends IntegrationTest {

  @Test
  @Description("카테고리 리스트를 모두 가져온다.")
  public void find_categories() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/api/v1/categories")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("", hasSize(8));
  }

}

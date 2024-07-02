package com.example.musinsarecommandproduct.controller.admin;

import com.example.musinsarecommandproduct.IntegrationTest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductStatusModifyRequest;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.BrandStatus;
import com.example.musinsarecommandproduct.enums.ProductStatus;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminBrandControllerTest extends IntegrationTest {

  private static final Map<String, String> brandNames = Map.of(
      "unique", "unique_test_brand",
      "duplicated", "Brand Alpha"
  );

  private static final Brand savedBrand = new Brand();
  private static final Category savedCategory = new Category();
  private static final Product savedProduct = new Product();
  private static AdminProductAddRequest adminProductAddRequest;

  @BeforeAll
  public static void init() {
    savedBrand.setId(1L);
    savedBrand.setName("Brand Alpha");
    savedBrand.setStatus(BrandStatus.EXPOSED);

    savedCategory.setId(1L);
    savedCategory.setName("상의");

    savedProduct.setId(1L);
    savedProduct.setBrandId(1L);
    savedProduct.setName("Alpha Top 1");
    savedProduct.setCategoryId(1L);
    savedProduct.setPrice(10200);
    savedProduct.setStatus(ProductStatus.EXPOSED);

    adminProductAddRequest = new AdminProductAddRequest(
        savedCategory.getId(), "New-Test-Product", 10000, ProductStatus.EXPOSED);
  }

  /** GET /admin/api/v1/brands */
  @Test
  @DirtiesContext
  @Description("저장된 모든 브랜드를 가져올 수 있다.")
  public void get_brand_list_test() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/admin/api/v1/brands")
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("", hasSize(10))
        .log().all();
  }

  /** POST /admin/api/v1/brands */
  @Test
  @DirtiesContext
  @Description("신규 브랜드를 추가할 수 있다.")
  public void add_brand_success_test() {
    AdminBrandAddRequest request = new AdminBrandAddRequest(brandNames.get("unique"));

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .post("/admin/api/v1/brands")
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(brandNames.get("unique")))
        .body("status", equalTo(BrandStatus.NOT_EXPOSED.name()))
        .log().all();
  }

  @Test
  @DirtiesContext
  @Description("신규 브랜드 추가 시도 시, 브랜드 이름이 동일한 경우 400에러가 발생한다.")
  public void add_brand_duplicated_name_test() {
    AdminBrandAddRequest request = new AdminBrandAddRequest(brandNames.get("duplicated"));

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .post("/admin/api/v1/brands")
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.DUPLICATE_BRAND_NAME.getMessage()))
        .body("code", equalTo(BadRequestType.DUPLICATE_BRAND_NAME.getCode()));
  }

  /** GET /admin/api/v1/brands/{id} */
  @Test
  @DirtiesContext
  @Description("특정 브랜드 정보를 가져 올 수 있다.")
  public void get_brand_success_test() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/admin/api/v1/brands/{id}", savedBrand.getId())
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo(savedBrand.getId().intValue()))
        .body("name", equalTo(savedBrand.getName()))
        .body("status", equalTo(savedBrand.getStatus().name()));
  }

  @Test
  @DirtiesContext
  @Description("잘못된 brandId 전달 시 400 에러가 발생한다.")
  public void get_brand_invalid_id_test() {
    Long invalidBrandId = 999L;

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/admin/api/v1/brands/{id}", invalidBrandId)
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.NOT_FOUND_BRAND.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_BRAND.getCode()));
  }

  /** POST /admin/api/v1/brands/{id}/products */
  @Test
  @DirtiesContext
  @Description("특정 브랜드에 상품을 추가 할 수 있다.")
  public void add_product_success() {
    given()
        .contentType(ContentType.JSON)
        .body(adminProductAddRequest)
    .when()
        .log().uri()
        .post("/admin/api/v1/brands/{id}/products", savedBrand.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.name", equalTo(adminProductAddRequest.name()))
        .body("product.price", equalTo(adminProductAddRequest.price()))
        .body("product.status", equalTo(ProductStatus.EXPOSED.name()))
        .body("brand.id", equalTo(savedBrand.getId().intValue()))
        .body("brand.name", equalTo(savedBrand.getName()))
        .body("brand.status", equalTo(savedBrand.getStatus().name()))
        .body("category.id", equalTo(savedCategory.getId().intValue()))
        .body("category.name", equalTo(savedCategory.getName()));
  }

  @Test
  @DirtiesContext
  @Description("상품 추가 시도 시 브랜드가 존재하지 않으면 400 에러가 발생한다.")
  public void get_brand_invalid_brand_id_test() {
    Long invalidBrandId = 999L;

    given()
        .contentType(ContentType.JSON)
        .body(adminProductAddRequest)
    .when()
        .log().uri()
        .post("/admin/api/v1/brands/{id}/products", invalidBrandId)
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.NOT_FOUND_BRAND.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_BRAND.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("상품 추가 시도 시 카테고리가 존재하지 않으면 400 에러가 발생한다.")
  public void get_brand_invalid_category_id_test() {
    Long invalidCategoryId = 999L;
    AdminProductAddRequest invalidCategoryRequest = new AdminProductAddRequest(invalidCategoryId, "New-Test-Product", 10000, ProductStatus.EXPOSED);

    given()
        .contentType(ContentType.JSON)
        .body(invalidCategoryRequest)
    .when()
        .log().uri()
        .post("/admin/api/v1/brands/{id}/products", savedBrand.getId())
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.NOT_FOUND_CATEGORY.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_CATEGORY.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("상품 추가 시도 시 임시저장 상태가 아니라면 필수 값이 없을 때 400 에러가 발생한다.")
  public void fail_add_product_have_not_required_fields() {}

  @Test
  @DirtiesContext
  @Description("상품 추가 시도 시 임시저장 상태라면 필수 값이 없어도 정상적으로 저장된다.")
  public void add_draft_product_have_not_required_fields_success() {}

  /** GET /admin/api/v1/brands/{id}/products/{productId} */
  @Test
  @DirtiesContext
  @Description("특정 상품을 가져올 수 있다.")
  public void get_product_success_test() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.id", equalTo(savedProduct.getId().intValue()))
        .body("product.name", equalTo(savedProduct.getName()))
        .body("product.status", equalTo(savedProduct.getStatus().name()))
        .body("product.price", equalTo(savedProduct.getPrice().intValue()))
        .body("product.brandId", equalTo(savedProduct.getBrandId().intValue()))
        .body("product.categoryId", equalTo(savedProduct.getCategoryId().intValue()))
        .body("product.createdAt", notNullValue())
        .body("product.updatedAt", notNullValue());
  }

  /** GET /admin/api/v1/brands/{id}/products */
  @Test
  @DirtiesContext
  @Description("특정 브랜드의 상품을 페이징하여 가져올 수 있다.")
  public void get_product_list_success_test() {
    Response response = given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .get("/admin/api/v1/brands/{id}/products", savedBrand.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("page", equalTo(0))
        .body("size", equalTo(20))
        .body("content", notNullValue())
        .body("content[0].brand.id", equalTo(savedBrand.getId().intValue()))
        .body("content[0].brand.name", equalTo(savedBrand.getName()))
        .extract().response();;


    // 각 상품들의 브랜드가 지정한 브랜드임을 확인
    List<Integer> brandIds = response.jsonPath().getList("content.brand.id");
    Set<Integer> uniqueBrandIds = new HashSet<>(brandIds);
    assertEquals(uniqueBrandIds.size(), 1);
    assertEquals(uniqueBrandIds.toArray()[0], savedBrand.getId().intValue());
  }

  /** PUT /admin/api/v1/brands/{id}/products/{productId} */
  @Test
  @DirtiesContext
  @Description("특정 상품의 이름을 변경할 수 있다.")
  public void update_product_success_test1() {
    AdminProductModifyRequest request = new AdminProductModifyRequest("updated-name", savedProduct.getPrice());

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.name", equalTo(request.name())) // 변경된 이름
        .body("product.price", equalTo(savedProduct.getPrice())) // 기존 가격
        .body("product.status", equalTo(ProductStatus.EXPOSED.name()))
        .body("brand.id", equalTo(savedProduct.getId().intValue()))
        .body("brand.name", equalTo(savedBrand.getName()))
        .body("brand.status", equalTo(savedBrand.getStatus().name()))
        .body("category.id", equalTo(savedProduct.getId().intValue()))
        .body("category.name", equalTo(savedCategory.getName()));
  }

  @Test
  @DirtiesContext
  @Description("특정 상품의 가격을 변경할 수 있다.")
  public void update_product_success_test2() {
    AdminProductModifyRequest request = new AdminProductModifyRequest(savedProduct.getName(), 99999);

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.name", equalTo(savedProduct.getName())) // 기존 이름
        .body("product.price", equalTo(request.price().intValue())) // 변경된 가격
        .body("product.status", equalTo(ProductStatus.EXPOSED.name()))
        .body("brand.id", equalTo(savedProduct.getId().intValue()))
        .body("brand.name", equalTo(savedBrand.getName()))
        .body("brand.status", equalTo(savedBrand.getStatus().name()))
        .body("category.id", equalTo(savedProduct.getId().intValue()))
        .body("category.name", equalTo(savedCategory.getName()));
  }

  @Test
  @DirtiesContext
  @Description("상품 수정 시도 시 상품이 존재하지 않으면 400 에러가 발생한다.")
  public void update_product_not_found() {
    Long invalidId = 9999L;
    AdminProductModifyRequest request = new AdminProductModifyRequest(savedProduct.getName(), 99999);

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), invalidId)
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.NOT_FOUND_PRODUCT.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_PRODUCT.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("상품 수정 변경 시도 시 브랜드가 존재하지 않으면 400 에러가 발생한다.")
  public void update_product_not_found_brand() {
    Long invalidId = 9999L;
    AdminProductModifyRequest request = new AdminProductModifyRequest(savedProduct.getName(), 99999);

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}", invalidId, savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.NOT_FOUND_BRAND.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_BRAND.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("삭제된 상품의 수정 변경 시도 시 400 에러가 발생한다.")
  public void update_fail_deleted_product() {
    Long targetBrandId = 1L;
    Long targetProductId = 5L;

    // 테스트 위해 데이터 삭제
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .log().uri()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());


    // 수정 시도
    AdminProductModifyRequest request = new AdminProductModifyRequest("deleted", 1000);

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}", targetBrandId, targetProductId)
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("message", equalTo(BadRequestType.ALREADY_DELETED_PRODUCT.getMessage()))
        .body("code", equalTo(BadRequestType.ALREADY_DELETED_PRODUCT.getCode()));
  }


  /** PUT /admin/api/v1/brands/{id}/products/{productId}/status */
  @Test
  @DirtiesContext
  @Description("특정 상품의 상태를 ADMIN_HIDDEN로 변경할 수 있다.")
  public void product_status_update_adminHidden_success() {
    AdminProductStatusModifyRequest request = new AdminProductStatusModifyRequest(ProductStatus.ADMIN_HIDDEN);

    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}/status", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.name", equalTo(savedProduct.getName()))
        .body("product.price", equalTo(savedProduct.getPrice()))
        .body("product.status", equalTo(ProductStatus.ADMIN_HIDDEN.name()))
        .body("brand.id", equalTo(savedProduct.getId().intValue()))
        .body("brand.name", equalTo(savedBrand.getName()))
        .body("brand.status", equalTo(savedBrand.getStatus().name()))
        .body("category.id", equalTo(savedProduct.getId().intValue()))
        .body("category.name", equalTo(savedCategory.getName()));
  }

  @Test
  @DirtiesContext
  @Description("특정 상품의 상태를 EXPOSED로 변경할 수 있다.")
  public void product_status_update_exposed_success() {
    // ADMIN_HIDDEN 상태로 변경
    AdminProductStatusModifyRequest setting = new AdminProductStatusModifyRequest(ProductStatus.ADMIN_HIDDEN);
    given()
        .contentType(ContentType.JSON)
        .body(setting)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}/status", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.status", equalTo(ProductStatus.ADMIN_HIDDEN.name()));

    // 다시 EXPOSED로 변경
    AdminProductStatusModifyRequest request = new AdminProductStatusModifyRequest(ProductStatus.EXPOSED);
    given()
        .contentType(ContentType.JSON)
        .body(request)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}/status", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("product.name", equalTo(savedProduct.getName()))
        .body("product.price", equalTo(savedProduct.getPrice()))
        .body("product.status", equalTo(ProductStatus.EXPOSED.name()))
        .body("brand.id", equalTo(savedProduct.getId().intValue()))
        .body("brand.name", equalTo(savedBrand.getName()))
        .body("brand.status", equalTo(savedBrand.getStatus().name()))
        .body("category.id", equalTo(savedProduct.getId().intValue()))
        .body("category.name", equalTo(savedCategory.getName()));
  }

  @Test
  @DirtiesContext
  @Description("특정 상품의 상태를 DRAFT로 변경 시도 시 400 에러가 발생한다.")
  public void fail_product_status_update_to_draft() {
    AdminProductStatusModifyRequest setting = new AdminProductStatusModifyRequest(ProductStatus.DRAFT);
    given()
        .contentType(ContentType.JSON)
        .body(setting)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}/status", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DirtiesContext
  @Description("해당 카테고리에 유일한 상품이 EXPOSED가 아닌 상태로의 변경 시도 시 400 에러가 발생한다.")
  public void fail_update_not_exposed_last_product_in_category() {
    // 테스트를 위해 삭제처리
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("product.id", equalTo(savedBrand.getId().intValue()))
        .body("product.status", equalTo(ProductStatus.DELETED.name()));

    // TEST: 해당 브랜드의 특정 카테고리에 노출되는 상품없도록 상태 변경
    AdminProductStatusModifyRequest setting = new AdminProductStatusModifyRequest(ProductStatus.SOLD_OUT);
    given()
        .contentType(ContentType.JSON)
        .body(setting)
    .when()
        .log().uri()
        .put("/admin/api/v1/brands/{id}/products/{productId}/status", savedProduct.getBrandId(), savedProduct.getId() + 1)
    .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(BadRequestType.LAST_PRODUCT_IN_CATEGORY.getMessage()))
        .body("code", equalTo(BadRequestType.LAST_PRODUCT_IN_CATEGORY.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("삭제된 상품의 상태 변경 시도 시 400 에러가 발생한다.")
  public void fail_update_deleted_product_status() {}

  /** DELETE /admin/api/v1/brands/{id}/products/{productId} */
  @Test
  @DirtiesContext
  @Description("상품을 삭제 할 수 있다.")
  public void delete_product_success() {
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("product.id", equalTo(savedBrand.getId().intValue()))
        .body("product.status", equalTo(ProductStatus.DELETED.name()));
  }

  @Test
  @DirtiesContext
  @Description("삭제된 상품의 삭제 시도 시 400 에러가 발생한다.")
  public void fail_delete_already_deleted_product() {
    // 테스트를 위해 데이터 삭제
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("product.id", equalTo(savedBrand.getId().intValue()))
        .body("product.status", equalTo(ProductStatus.DELETED.name()));

    // test: 다시 삭제 시도
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(BadRequestType.ALREADY_DELETED_PRODUCT.getMessage()))
        .body("code", equalTo(BadRequestType.ALREADY_DELETED_PRODUCT.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("해당 카테고리에 유일한 상품을 삭제 시도 시 400 에러가 발생한다.")
  public void fail_delete_last_product_in_category() {
    // 테스트를 위해 데이터 삭제
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId())
    .then()
        .statusCode(HttpStatus.OK.value())
        .body("product.id", equalTo(savedBrand.getId().intValue()))
        .body("product.status", equalTo(ProductStatus.DELETED.name()));

    // TEST: 같은 카테고리의 다른 상품 삭제
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedProduct.getBrandId(), savedProduct.getId() + 1)
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(BadRequestType.LAST_PRODUCT_IN_CATEGORY.getMessage()))
        .body("code", equalTo(BadRequestType.LAST_PRODUCT_IN_CATEGORY.getCode()));
  }

  @Test
  @DirtiesContext
  @Description("상품의 삭제 시도 시 상품이 존재하지 않으면 400 에러가 발생한다.")
  public void fail_delete_not_exist_product() {
    Long invalidProductId = 999L;

    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .delete("/admin/api/v1/brands/{id}/products/{productId}", savedBrand.getId(), invalidProductId)
    .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(BadRequestType.NOT_FOUND_PRODUCT.getMessage()))
        .body("code", equalTo(BadRequestType.NOT_FOUND_PRODUCT.getCode()));
  }


  /** 상품 추가, 변경, 삭제 시 순위 변경 확인 */

}

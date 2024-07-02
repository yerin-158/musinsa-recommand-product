package com.example.musinsarecommandproduct.exception;

import lombok.Getter;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Getter
public enum BadRequestType {

  INVALID_REQUEST("ETC-1", "잘못된 요청입니다."),
  INVALID_DATA("ETC-2", "올바르지 않은 데이터입니다."), //이거 다른 Exception으로 처리해야하지 않나?

  NOT_FOUND_PRODUCT("PRD-1", "상품이 존재하지 않습니다."),
  ALREADY_DELETED_PRODUCT("PRD-2", "이미 삭제된 상품입니다."),
  PRODUCT_NAME_IS_REQUIRED("PRD-3", "상품 이름은 필수값입니다."),
  PRODUCT_STATUS_IS_REQUIRED("PRD-4", "상품 상태는 필수값입니다."),
  PRODUCT_CATEGORY_ID_IS_REQUIRED("PRD-5", "카테고리 ID는 필수값입니다."),
  PRODUCT_PRICE_IS_REQUIRED("PRD-6", "상품 가격은 필수값입니다."),
  PRODUCT_PRICE_CANNOT_BE_ZERO("PRD-7", "상품 가격은 0이 될 수 없습니다."),
  PRODUCT_PRICE_CANNOT_BE_NEGATIVE("PRD-8", "상품 가격은 음수일 수 없습니다."),
  DELETED_PRODUCT_CANNOT_MODIFY("PRD-9", "삭제 처리된 상품은 수정할 수 없습니다."),
  PRODUCT_CANNOT_ROLLBACK_TO_DRAFT("PRD-10", "상품은 임시저장 상태로 변경할 수 없습니다."),


  NOT_FOUND_BRAND("BRD-1", "브랜드가 존재하지 않습니다."),
  DUPLICATE_BRAND_NAME("BRD-2", "브랜드 이름이 중복됩니다."),
  BRAND_NAME_IS_REQUIRED("BRD-3", "브랜드 이름은 필수값입니다."),

  NOT_FOUND_CATEGORY("CATE-1", "카테고리가 존재하지 않습니다."),
  LAST_PRODUCT_IN_CATEGORY("CATE-2", "카테고리에는 최소한 하나의 상품이 있어야 합니다."),

  INVALID_PRICE_STATISTICS("STAT-1", "가격 정보가 유효하지 않습니다.\n최저가 또는 최고가가 설정되지 않았습니다."),

  ;

  private String code;
  private String message;

  BadRequestType(String code, String message) {
    this.code = code;
    this.message = message;
  }

}

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

  NOT_FOUND_PRODUCT("PRD-1", "상품이 존재하지 않습니다."),
  ALREADY_DELETED_PRODUCT("PRD-2", "이미 삭제된 상품입니다."),

  NOT_FOUND_BRAND("BRD-1", "브랜드가 존재하지 않습니다."),
  DUPLICATE_BRAND_NAME("BRD-2", "브랜드 이름이 중복됩니다."),

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

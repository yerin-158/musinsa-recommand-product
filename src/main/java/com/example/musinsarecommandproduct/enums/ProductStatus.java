package com.example.musinsarecommandproduct.enums;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public enum ProductStatus {

  DRAFT, // 임시저장
  EXPOSED, // 노출(판매중)
  ADMIN_HIDDEN, // 관리자에 의한 숨김처리
  SOLD_OUT, // 품절
  DELETED, // 삭제
  ;

  public boolean isExposed() {
    return this.equals(ProductStatus.EXPOSED);
  }

}

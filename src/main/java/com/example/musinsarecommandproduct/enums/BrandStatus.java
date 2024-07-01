package com.example.musinsarecommandproduct.enums;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public enum BrandStatus {

  NOT_EXPOSED, // 비어있는 카테고리가 있어 노출 불가함
  EXPOSED, // 노출가능
  ADMIN_HIDDEN, // 관리자가 숨김처리
  DELETED, // 삭제됨
  ;

  public Boolean isExposed() {
    return this.equals(BrandStatus.EXPOSED);
  }

}

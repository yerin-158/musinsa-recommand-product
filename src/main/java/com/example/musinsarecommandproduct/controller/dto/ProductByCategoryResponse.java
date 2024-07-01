package com.example.musinsarecommandproduct.controller.dto;

import java.util.List;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public record ProductByCategoryResponse(CategorySimpleResponse category, List<ProductResponse> lowestPriceProducts, List<ProductResponse> highestPriceProducts) {
}

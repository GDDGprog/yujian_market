package com.yujian.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 类别热门商品参数接收
 */
@Data
public class ProductPromoParam {

    @NotBlank
    private String categoryName;
}

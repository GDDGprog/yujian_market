package com.yujian.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartSaveParam {

    @JsonProperty("product_id")
    @NotNull
    private Integer productId;

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}

package com.yujian.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车查询接收参数
 */
@Data
public class CarListParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}

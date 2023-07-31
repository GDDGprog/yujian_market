package com.yujian.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yujian.pojo.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 地址接收值1param
 */
@Data
public class AddressParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;
}

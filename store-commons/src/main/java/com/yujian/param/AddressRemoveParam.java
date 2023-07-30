package com.yujian.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressRemoveParam {

    @NotNull
    private Integer id;
}

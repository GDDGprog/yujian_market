package com.yujian.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数实体
 */
@Data
public class UserLoginParam {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}

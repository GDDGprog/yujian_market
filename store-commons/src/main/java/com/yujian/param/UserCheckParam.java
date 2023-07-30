package com.yujian.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO: 要使用 jsr 303注解 进行参数校验
 * @NotBlank 字符串不能为空 和 null
 * @NotNull 字符串不能为null
 * @NotEmpty 集合类型 集合长度不能为0
 */
@Data
public class UserCheckParam {

    @NotBlank
    private String userName; // 注意 : 参数名称要等于前端传递的JSON key 的名称!
}

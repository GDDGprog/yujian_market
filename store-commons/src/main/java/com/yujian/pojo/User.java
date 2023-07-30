package com.yujian.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {

    public static final long serialVersionUID = 1L;

    @JsonProperty("user_id") // jackson的注解,用于 属性格式化
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @Length(min = 6)
    private String userName;

    @NotNull
    // @JsonIgnore : 忽略属性 , 不生成json,也不接受 json数据

    // @JsonInclude(JsonInclude.Include.NON_NULL) + null 当这个值不为null的时候生成json,为null的时候不生成json,不影响接受json
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPhonenumber;
}

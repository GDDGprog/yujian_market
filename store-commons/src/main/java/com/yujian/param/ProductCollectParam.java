package com.yujian.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 收藏调用商品传递的参数
 */
@Data
public class ProductCollectParam {

    @NotEmpty
    private List<Integer> productIds;
}

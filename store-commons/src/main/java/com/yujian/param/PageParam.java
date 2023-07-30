package com.yujian.param;

import lombok.Data;

/**
 * 分页属性
 */
@Data
public class PageParam {

    private Integer currentPage = 1; //默认值为1

    private Integer pageSize = 15;//默认值为15
}

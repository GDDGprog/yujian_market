package com.yujian.category.service;

import com.yujian.param.ProductHotParam;
import com.yujian.utils.R;

public interface CategoryService {
    /**
     * 根据类别名称,查询类别对象
     * @param categoryName
     * @return
     */
    R byName(String categoryName);

    /**
     * 根据传入的热门类别名称集合! 返回类别对应的id集合
     * @param productHotParam
     * @return
     */
    R hotsCategory(ProductHotParam productHotParam);

    /**
     * 查询类别数据进行返回
     * @return r 类别数据结合
     */
    R list();
}

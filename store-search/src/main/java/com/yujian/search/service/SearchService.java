package com.yujian.search.service;

import com.yujian.param.ProductSearchParam;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface SearchService {

    /**
     * 根据关键字和分页进行数据库数据查询
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);
}

package com.yujian.clients;

import com.yujian.param.ProductSearchParam;
import com.yujian.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "search-service")
public interface SearchClient {

    /**
     * 搜索服务 商品查询
     *
     */
    @PostMapping("/search/product")
    R search(@RequestBody ProductSearchParam productSearchParam);

}

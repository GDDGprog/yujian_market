package com.yujian.search.controller;

import com.yujian.param.ProductSearchParam;
import com.yujian.search.service.SearchService;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam) {
        return searchService.search(productSearchParam);

    }
}

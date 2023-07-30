package com.yujian.product.controller;

import com.yujian.param.ProductCollectParam;
import com.yujian.product.service.ProductService;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductCollectController {

    @Autowired
    private ProductService productService;

    @PostMapping("collect/list")
    public R productIds(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result){
        if (result.hasErrors()){
            return R.ok("没有收藏数据!!!");
        }
        return productService.ids(productCollectParam.getProductIds());
    }
}

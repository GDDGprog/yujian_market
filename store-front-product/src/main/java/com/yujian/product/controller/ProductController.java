package com.yujian.product.controller;

import com.yujian.param.*;
import com.yujian.product.service.ProductService;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public R promp(@RequestBody @Validated ProductPromoParam productPromoParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("数据查询失败!!!");
        }
        return productService.promo(productPromoParam.getCategoryName());
    }

    @PostMapping("/hots")
    public R hots(@RequestBody ProductHotParam productHotParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("数据查询失败!!!");
        }
        return productService.hots(productHotParam);
    }

    @PostMapping("category/list")
    public R clist(){
        return productService.clist();
    }

    @PostMapping("bycategory")
    public R bycategory(@RequestBody @Validated ProductByCategoryParam productByCategoryParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("商品类别查询失败!!!");
        }
        return productService.bycategory(productByCategoryParam);
    }

    @PostMapping("all")
    public R all(@RequestBody @Validated ProductByCategoryParam productByCategoryParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("商品类别查询失败!!!");
        }
        return productService.bycategory(productByCategoryParam);
    }

    @PostMapping("/detail")
    public R detail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("商品详情查询失败!!!");
        }
        return productService.detail(productIdParam.getProductID());
    }

    @PostMapping("/pictures")
    public R pictures(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("商品图片详情查询失败!!!");
        }
        return productService.picture(productIdParam.getProductID());
    }

    @PostMapping("/search")
    public R search(@RequestBody ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);

    }
}

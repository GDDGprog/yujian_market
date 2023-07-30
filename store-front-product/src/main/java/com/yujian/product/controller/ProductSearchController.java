package com.yujian.product.controller;

import com.yujian.pojo.Product;
import com.yujian.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }
}

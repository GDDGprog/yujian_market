package com.yujian.cart.controller;

import com.yujian.cart.service.CartService;
import com.yujian.param.CarListParam;
import com.yujian.param.CartSaveParam;
import com.yujian.pojo.Cart;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("save")
    public R save(@RequestBody CartSaveParam cartSaveParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("核心查参数为null,添加失败");
        }
        return cartService.save(cartSaveParam);
    }

    @PostMapping("list")
    public R list(@RequestBody @Validated CarListParam carListParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("购物车数据查询失败");
        }
        return cartService.list(carListParam.getUserId());
    }

    @PostMapping("update")
    public R update(@RequestBody Cart cart){
        return cartService.update(cart);
    }

    @PostMapping("remove")
    public R remove(@RequestBody Cart cart){
        return cartService.remove(cart);
    }
}

package com.yujian.cart.service;

import com.yujian.param.CarListParam;
import com.yujian.param.CartSaveParam;
import com.yujian.pojo.Cart;
import com.yujian.utils.R;

public interface CartService {
    /**
     * 购物侧数据添加方法
     * @param cartSaveParam
     * @return
     */
    R save(CartSaveParam cartSaveParam);

    /**
     * 返回购物车数据
     * @param userId
     * @return 确保要返回一个数组
     */
    R list(Integer userId);

    /**
     * 更新购物车业务
     * @param cart
     * @return
     */
    R update(Cart cart);

    /**
     * 删除购物车数据
     * @param cart
     * @return
     */
    R remove(Cart cart);
}

package com.yujian.order.controller;

import com.yujian.order.service.OrderService;
import com.yujian.param.OrderParam;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单的controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public R save(@RequestBody OrderParam orderParam) {
        return orderService.save(orderParam);
    }

    @PostMapping("/list")
    public R list(@RequestBody @Validated OrderParam orderParam, BindingResult result) {
        if (result.hasErrors()) {
            return R.fail("参数异常,查询失败");
        }
        return orderService.list(orderParam.getUserId());
    }
}

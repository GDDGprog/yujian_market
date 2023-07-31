package com.yujian.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yujian.param.OrderParam;
import com.yujian.pojo.Order;
import com.yujian.utils.R;

public interface OrderService extends IService<Order> {

    /**
     * 进行订单数据保存
     * @param orderParam
     * @return
     */
    R save(OrderParam orderParam);

    /**
     * 分组查询订单数据
     * @param userId
     * @return
     */
    R list(Integer userId);
}

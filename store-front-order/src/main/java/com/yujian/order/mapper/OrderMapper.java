package com.yujian.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yujian.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

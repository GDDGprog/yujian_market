package com.yujian.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujian.clients.ProductClient;
import com.yujian.order.mapper.OrderMapper;
import com.yujian.order.service.OrderService;
import com.yujian.param.OrderParam;
import com.yujian.param.ProductCollectParam;
import com.yujian.pojo.Order;
import com.yujian.pojo.Product;
import com.yujian.to.OrderToProduct;
import com.yujian.utils.R;
import com.yujian.vo.CartVo;
import com.yujian.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 进行订单数据保存
     *     1.将购物车数据转成订单数据
     *     2.进行订单数据的批量插入
     *     3.商品库存修改消息
     *     4.发送购物车库存修改消息
     * @param orderParam
     * @return
     */
    @Transactional
    @Override
    public R save(OrderParam orderParam) {

        //准备数据
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();

        //生成数据
        Integer userId = orderParam.getUserId();
        long orderId = System.currentTimeMillis();

        for (CartVo product : orderParam.getProducts()) {
            cartIds.add(product.getId()); //保存删除购物车项的id
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setNum(product.getNum());
            orderToProduct.setProductId(product.getProductID());
            orderToProducts.add(orderToProduct); // 商品服务修改的数据

            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductId(product.getProductID());
            order.setProductNum(product.getNum());
            order.setProductPrice(product.getPrice());
            orderList.add(order);

        }
        //订单数据批量保存
        saveBatch(orderList);

        //发送购物车消息
        /**
         * 交换机 : topic.ex
         * routingKey : clear.cart
         * 消息 : 清空的购物车id集合
         */
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);
        //发送商品服务消息
        /**
         * 交换机 : topic.ex
         * routingKey : sub.cart
         * 消息 : 商品id和减库存数据集合
         */
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);

        R ok = R.ok("订单生成成功");

        return ok;
    }

    /**
     * 分组查询订单数据
     *      1. 查询用户对应的全部订单
     *      2. 利用stream进行订单分组 orderId
     *      3. 查询订单的全部商品集合 , 并stream组成map
     *      4. 封装返回OrderVo 对象
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Order> list = list(queryWrapper);

        //分组
        Map<Long, List<Order>> orderMap = list.stream().collect(Collectors.groupingBy(Order::getOrderId));

        //查询商品数据
        List<Integer> productIds = list.stream().map(Order::getProductId).collect(Collectors.toList());

        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();

        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order,orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }

        R ok = R.ok("订单数据获取成功",result);
        return ok;
    }
}

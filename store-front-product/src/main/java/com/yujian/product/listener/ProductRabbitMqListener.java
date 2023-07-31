package com.yujian.product.listener;

import com.yujian.product.service.ProductService;
import com.yujian.to.OrderToProduct;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品的mq消息监听
 */
public class ProductRabbitMqListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "sub.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "sub.number"
            )
    )
    public void subNumber(List<OrderToProduct> orderToProducts) {
        productService.subNumber(orderToProducts);
    }
}

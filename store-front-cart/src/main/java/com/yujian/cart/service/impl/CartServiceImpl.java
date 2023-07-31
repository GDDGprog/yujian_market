package com.yujian.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.cart.mapper.CartMapper;
import com.yujian.cart.service.CartService;
import com.yujian.clients.ProductClient;
import com.yujian.param.CarListParam;
import com.yujian.param.CartSaveParam;
import com.yujian.param.ProductCollectParam;
import com.yujian.param.ProductIdParam;
import com.yujian.pojo.Cart;
import com.yujian.pojo.Product;
import com.yujian.utils.R;
import com.yujian.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartMapper cartMapper;

    /**
     * 购物侧数据添加方法
     * @param cartSaveParam
     * @return
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        //查询商品数据
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);
        if (product == null) {
            return R.fail("商品不存在");
        }
        //检查库存
        if (product.getProductNum() == 0) {
            R r = R.ok("库存不足");
            r.setCode("003");
            return r;
        }

        //检查是否添加过
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartSaveParam.getUserId());
        queryWrapper.eq("product_id", cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart != null){
            //证明购物车存在
            //在原有的基础上+1
            cart.setNum(cart.getNum() + 1);
            cartMapper.updateById(cart);
            //返回002提示即可
            R ok = R.ok("购物车存在该商品,数量+1");
            ok.setCode("002");
            return ok;
        }

        //添加购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setProductId(cartSaveParam.getProductId());
        cart.setUserId(cartSaveParam.getUserId());
        int insert = cartMapper.insert(cart);

        CartVo cartVo = new CartVo(product, cart);
        return R.ok("购物车数据添加成功!",cartVo);
    }

    /**
     * 返回购物车数据
     * @param userId
     * @return 确保要返回一个数组
     */
    @Override
    public R list(Integer userId) {
        //1. 用户id查询,购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> list = cartMapper.selectList(queryWrapper);

        //2. 判断是否存在,不存在,返回一个空集合
        if (list.size() == 0 || list == null){
            list = new ArrayList<>();
            return R.ok("购物车空空如也",list);
        }
        //3. 存在则获取商品id集合,并且调用商品服务查询
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : list) {
            productIds.add(cart.getProductId());
        }

        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);

        //商品集合 -> 商品map 商品的id = key 商品 = value
        //jdk8 stream
        Map<Integer, Product> collect = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));

        //4. 进行vo封装
        List<CartVo> cartVoList = new ArrayList<>();

        for (Cart cart : list) {
            CartVo cartVo = new CartVo(collect.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        R r = R.ok("数据库查询成功", cartVoList);
        return r;
    }

    /**
     * 更新购物车业务
     * 1.查询商品数据
     * 2.判断库存是否可用
     * 3.正常修改即可
     * @param cart
     * @return
     */
    @Override
    public R update(Cart cart) {
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdParam);

        //判断库存
        if (cart.getNum() > product.getProductNum()) {
            return R.fail("库存不足");
        }

        //修改数据库
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
        Cart newCart = cartMapper.selectOne(queryWrapper);

        newCart.setNum(cart.getNum());

        int update = cartMapper.updateById(newCart);
        return R.ok("修改购物车数量成功");
    }

    /**
     * 删除购物车数据
     * @param cart
     * @return
     */
    @Override
    public R remove(Cart cart) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());

        int delete = cartMapper.delete(queryWrapper);
        return R.ok("购物车数据删除成功!!",delete);
    }

    @Override
    public void claerIds(List<Integer> cartIds) {
        cartMapper.deleteBatchIds(cartIds);

    }
}

package com.yujian.product.service;

import com.yujian.param.ProductByCategoryParam;
import com.yujian.param.ProductHotParam;
import com.yujian.param.ProductSearchParam;
import com.yujian.pojo.Product;
import com.yujian.utils.R;

import java.util.List;

public interface ProductService {
    /**
     * 单类别名称,查询热门商品 最多7条数据
     * @param categoryName 类别名称
     * @return
     */
    R promo(String categoryName);

    /**
     * 多类别热门商品查询 根据类别名称集合! 至多查询7条数据
     * @param productHotParam
     * @return
     */
    R hots(ProductHotParam productHotParam);

    /**
     * 查询类别商品集合
     * @return
     */
    R clist();

    /**
     * 通用性业务!
     *  1. 传入了类别id,根据id查询并分页
     *  2. 没有传入类别id,查询全部
     * @param productByCategoryParam
     * @return
     */
    R bycategory(ProductByCategoryParam productByCategoryParam);

    /**
     * 根据商品id查询商品详情
     * @param productID
     * @return
     */
    R detail(Integer productID);

    /**
     * 查询商品对应的图片详情结合
     * @param productID
     * @return
     */
    R picture(Integer productID);

    /**
     * 搜索服务调用,获取全部商品数据!
     * 进行同步!
     * @return 商品数据集合
     */
    List<Product> allList();

    /**
     * 搜索业务,需要调用搜索服务
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据商品id集合查询商品信息
     * @param productIds
     * @return
     */
    R ids(List<Integer> productIds);

    /**
     * 根据商品id,查询商品id集合
     * @param productIds
     * @return
     */
    List<Product> cartList(List<Integer> productIds);
}
package com.yujian.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yujian.clients.CategoryClient;
import com.yujian.clients.SearchClient;
import com.yujian.param.*;
import com.yujian.pojo.Category;
import com.yujian.pojo.Picture;
import com.yujian.pojo.Product;
import com.yujian.product.mapper.PictureMapper;
import com.yujian.product.mapper.ProductMapper;
import com.yujian.product.service.ProductService;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    //引入 feign客户端需要在启动类 添加配置注解
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private PictureMapper pictureMapper;

    /**
     * 单类别名称 查询热门商品 最多7条数据
     *      1. 根据类别名称 调用 feign 客户端 访问类别服务获取类别的数据
     *      2. 成功 继续根据类别id查询商品数据 [热门 销售量倒叙 查询7]
     *      3. 结果封装即可
     * @param categoryName 类别名称
     * @return
     */
    @Cacheable(value = "list.product",key = "#categoryName",cacheManager = "cacheManagerDay")
    @Override
    public R promo(String categoryName) {

        R r = categoryClient.byName(categoryName);

        if (r.getCode().equals(R.FAIL_CODE)){
            log.info("类别查询失败!!!");
            return r;
        }
        //Category category = (Category) r.getData();
        //Integer categoryId = category.getCategoryId();

        LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) r.getData();
        Integer categoryId = (Integer) map.get("category_id");

        //封装查询
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        wrapper.orderByDesc("product_sales");

        Page<Product> page = new Page<>(1, 7);

        //返回的是包装数据! 内部有对应的商品集合,也有分页的参数 例如 : 总条数,总页数等等
        page = productMapper.selectPage(page,wrapper);
        List<Product> productList = page.getRecords(); // 指定页的数据
        long total = page.getTotal();

        log.info(String.valueOf(productList));
        return R.ok("数据查询成功",productList);
    }

    /**
     * 多类别热门商品查询 根据类别名称集合! 至多查询7条数据
     *      1.调用类别服务
     *      2. 类别集合id查询商品
     *      3. 结果集封装即可
     * @param productHotParam 类别名称集合
     * @return
     */
    //此处默认不写cacheManager属性,会自动找到CacheConfiguration默认的cacheManagerHour
    @Cacheable(value = "list.product",key = "#productHotParam.categoryName"/*,cacheManager = "cacheManagerDay"*/)
    @Override
    public R hots(ProductHotParam productHotParam) {
        //调用 类别服务
        R r = categoryClient.hotsCategory(productHotParam);
        if (r.getCode() == R.FAIL_CODE){
            log.info("类别查询失败!!!",r.getMsg());
            return r;
        }
        List<Object> ids = (List<Object>) r.getData();

        //根据类别id查询商品
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.in("category_id",ids);
        wrapper.orderByDesc("product_sales");

        Page<Product> page = new Page<>(1, 7);

        //返回的是包装数据! 内部有对应的商品集合,也有分页的参数 例如 : 总条数,总页数等等
        page = productMapper.selectPage(page,wrapper);

        List<Product> productList = page.getRecords(); // 指定页的数据
        long total = page.getTotal();

        log.info(String.valueOf(productList));
        return R.ok("数据查询成功",productList);
    }

    /**
     * 查询类别商品集合
     * @return
     */
    @Override
    public R clist() {
        R list = categoryClient.list();
        log.info(String.valueOf(list.getData()));
        return list;
    }

    /**
     * 通用性业务!
     *  1. 传入了类别id,根据id查询并分页
     *  2. 没有传入类别id,查询全部
     * @param productByCategoryParam
     * @return
     */
    @Cacheable(value = "list.product",key = "#productByCategoryParam.categoryID+'-'+#productByCategoryParam.currentPage+'-'+#productByCategoryParam.pageSize")
    @Override
    public R bycategory(ProductByCategoryParam productByCategoryParam) {
        List<Integer> categoryID = productByCategoryParam.getCategoryID();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (!categoryID.isEmpty()) {
            wrapper.in("category_id", categoryID);
        }
        IPage<Product> page = new Page<>(productByCategoryParam.getCurrentPage(),productByCategoryParam.getPageSize());
        page = productMapper.selectPage(page,wrapper);
        R ok = R.ok("数据查询成功", page.getRecords(),page.getTotal());
        return ok;
    }

    /**
     * 根据商品id查询商品详情
     * @param productID
     * @return
     */
    @Cacheable(value = "product",key = "#productID")
    @Override
    public R detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        R ok = R.ok(product);
        log.info(ok.getCode());
        return ok;
    }

    /**
     * 查询商品对应的图片详情结合
     * @param productID
     * @return
     */
    @Cacheable(value = "picture",key = "#productID")
    @Override
    public R picture(Integer productID) {
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productID);

        List<Picture> pictures = pictureMapper.selectList(wrapper);
        R ok = R.ok(pictures);
        return ok;
    }

    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public List<Product> allList() {
        List<Product> productList = productMapper.selectList(null);
        log.info(String.valueOf(productList.size()));
        return productList;
    }

    /**
     * 搜索业务,需要调用搜索服务
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        R r = searchClient.search(productSearchParam);
        return r;
    }

    /**
     * 根据商品id集合查询商品信息
     * @param productIds
     * @return
     */
    @Cacheable(value = "list.product",key = "#productIds")
    @Override
    public R ids(List<Integer> productIds) {

        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.in("product_id",productIds);

        List<Product> productList = productMapper.selectList(wrapper);
        R r = R.ok("数据查询成功", productList);
        log.info(String.valueOf(productList));
        return r;
    }

    /**
     * 根据商品id,查询商品id集合
     * @param productIds
     * @return
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.in("product_id",productIds);
        return productMapper.selectList(wrapper);
    }
}

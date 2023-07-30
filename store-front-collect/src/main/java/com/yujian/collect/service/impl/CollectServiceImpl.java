package com.yujian.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.clients.ProductClient;
import com.yujian.collect.mapper.CollectMapper;
import com.yujian.collect.service.CollectService;
import com.yujian.param.ProductCollectParam;
import com.yujian.pojo.Collect;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ProductClient productClient;

    @Override
    public R save(Collect collect) {
        //1.先查询是否存在
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",collect.getUserId());
        wrapper.eq("product_id",collect.getProductId());
        Long count = collectMapper.selectCount(wrapper);

        if (count > 0){
            return R.fail("收藏已经添加,无需继续添加");
        }
        //2. 不存在进行添加
        //补充下时间
        collect.setCollectTime(System.currentTimeMillis());
        int insert = collectMapper.insert(collect);
        log.info("collect:{}",collect);
        return R.ok("收藏添加成功!!!");
    }

    /**
     * 根据用户id查询商品信息集合
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.select("product_id");

        List<Object> objects = collectMapper.selectObjs(queryWrapper);

        ProductCollectParam productCollectParam = new ProductCollectParam();

        List<Integer> ids = new ArrayList<>();
        for (Object o : objects) {
            ids.add((Integer) o);
        }
        productCollectParam.setProductIds(ids);

        R r = productClient.productIds(productCollectParam);
        log.info("collect:{}",r);
        return r;
    }

    /**
     * 根据用户id和商品id删除收藏数据
     * @param collect
     * @return
     */
    @Override
    public R remove(Collect collect) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",collect.getUserId());
        queryWrapper.eq("product_id",collect.getProductId());

        int delete = collectMapper.delete(queryWrapper);
        log.info(String.valueOf(delete));
        return R.ok("收藏移除成功!!!");
    }
}

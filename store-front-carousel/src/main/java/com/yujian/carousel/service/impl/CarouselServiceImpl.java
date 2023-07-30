package com.yujian.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.carousel.mapper.CarouselMapper;
import com.yujian.carousel.service.CarouselService;
import com.yujian.pojo.Carousel;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询优先级最高的六条轮播图数据
     *    按照优先级 查询数据库数据
     *    我们使用stream流,进行内存数据切割,保留6条数据!
     * @return
     */
    @Cacheable(value = "list.carousel", key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public R list() {
        QueryWrapper<Carousel> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("priority");
        List<Carousel> list = carouselMapper.selectList(wrapper);
        List<Carousel> collect = list.stream().limit(6).collect(Collectors.toList());
        R ok = R.ok(collect);
        log.info("ok:{}",ok);
        return ok;
    }
}

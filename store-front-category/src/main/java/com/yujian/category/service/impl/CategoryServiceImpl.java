package com.yujian.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.category.mapper.CategoryMapper;
import com.yujian.category.service.CategoryService;
import com.yujian.param.ProductHotParam;
import com.yujian.pojo.Category;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据类别名称,查询类别对象
     * @param categoryName
     * @return
     */
    @Override
    public R byName(String categoryName) {

        //封装查询参数
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name",categoryName);

        //查询数据库
        Category category = categoryMapper.selectOne(wrapper);
        //结果封装
        if (category == null) {
            log.info("类别查询失败!!!");
            return R.fail("类别查询失败!!!");
        }
        return R.ok("类别查询成功!!!",category);
    }

    /**
     * 根据传入的热门类别名称集合! 返回类别对应的id集合
     * @param productHotParam
     * @return
     */
    @Override
    public R hotsCategory(ProductHotParam productHotParam) {
        //封装查询数据库
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.in("category_name",productHotParam.getCategoryName());
        wrapper.select("category_id");

        //查询数据库
        List<Object> ids = categoryMapper.selectObjs(wrapper);

        R ok = R.ok("热门类别id查询成功!!!", ids);
        log.info("热门类别id查询成功!!!");
        return ok;
    }

    /**
     * 查询类别数据进行返回
     * @return r 类别数据结合
     */
    @Override
    public R list() {
        List<Category> categoryList = categoryMapper.selectList(null);
        R ok = R.ok("类别数据查询成功!!!", categoryList);
        log.info("类别数据查询成功!!!");
        return ok;
    }
}

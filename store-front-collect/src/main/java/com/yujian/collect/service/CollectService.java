package com.yujian.collect.service;

import com.yujian.pojo.Collect;
import com.yujian.utils.R;

public interface CollectService {
    /**
     * 收藏添加的方法
     * @param collect
     * @return
     */
    R save(Collect collect);

    /**
     * 根据用户id查询商品信息集合
     * @param userId
     * @return
     */
    R list(Integer userId);

    /**
     * 根据用户id和商品id删除收藏数据
     * @param collect
     * @return
     */
    R remove(Collect collect);
}

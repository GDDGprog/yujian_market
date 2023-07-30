package com.yujian.user.service;

import com.yujian.pojo.Address;
import com.yujian.utils.R;

public interface AddressService {
    /**
     * 根据用户 id 查询地址数据
     * @param userId 用户id ,已校验完毕
     * @return 001 004
     */
    R list(Integer userId);

    /**
     * 插入地址数据,插入成功以后,要返回新的数据集合
     * @param address 地址数据 已经校验完毕
     * @return 数据集合
     */
    R save(Address address);

    /**
     * 根据id 删除 地址数据
     * @param id 地址id
     * @return 001 004
     */
    R remove(Integer id);
}

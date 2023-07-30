package com.yujian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.pojo.Address;
import com.yujian.user.mapper.AddressMapper;
import com.yujian.user.service.AddressService;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户 id 查询地址数据
     * 1. 直接进行数据库查询
     * 2. 结果封装
     * @param userId 用户id ,已校验完毕
     * @return 001 004
     */
    @Override
    public R list(Integer userId) {
        // 1.数据库查询
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Address> addressList = addressMapper.selectList(wrapper);
        // 2.结果封装
        R ok = R.ok("查询成功", addressList);
        log.info("查询成功");
        return ok;
    }

    /**
     *
     * @param address 地址数据 已经校验完毕
     * @return 001 004
     */
    @Override
    public R save(Address address) {
        int row = addressMapper.insert(address);
        if (row <= 0){
            log.info("添加失败");
            return R.fail("添加失败");
        }
        return list(address.getUserId());
    }

    /**
     * TODO:
     *   1. 定义接收参数的param , 并且添加参数校验注解
     *   2. 定义controller
     *   3. 定义service
     *   4. 定义mapper
     *
     * 根据id 删除 地址数据
     * @param id 地址id
     * @return 001 004
     */
    @Override
    public R remove(Integer id) {
        int row = addressMapper.deleteById(id);
        if (row <= 0){
            log.info("删除失败");
            return R.fail("删除失败");
        }
        return R.ok("删除成功");
    }
}

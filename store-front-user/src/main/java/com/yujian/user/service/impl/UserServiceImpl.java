package com.yujian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yujian.constants.UserConstants;
import com.yujian.user.mapper.UserMapper;
import com.yujian.param.UserCheckParam;
import com.yujian.param.UserLoginParam;
import com.yujian.pojo.User;
import com.yujian.user.service.UserService;
import com.yujian.utils.MD5Util;
import com.yujian.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public R check(UserCheckParam userCheckParam) {
        //参数封装
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userCheckParam.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(wrapper);
        //查询结果处理
        if (total == 0){
            log.info("账号不存在,可进行使用!");
            return R.ok("账号不存在,可进行使用!");
        }

        log.info("账号已存在,不可进行使用!");
        return R.fail("账号已存在,不可进行使用!");
    }

    /**
     * 注册业务
     * 1. 检查账号是否存在
     * 2. 密码加密处理
     * 3. 插入数据库数据
     * 4. 返回结果封装
     * @param user 参数已经校验,但是密码是明文!
     * @return 结果 001 004
     */
    @Override
    public R register(User user) {
        //1.检查账号时候存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(wrapper);
        if (total > 0){
            log.info("账号已存在,不可注册!");
            return R.fail("账号已存在,不可注册!");
        }
        //2. 密码加密处理 , 注意要加盐!
        /**
         * MD5 是一种不可逆转的加密方式, 只能加密,不能解密
         *  固定的明文加密之后的密文是固定的
         *  123456 --> 加密 --> 111111
         *  注册时存储的是加密之后的密文!
         *  登录时将密码进行加密,用密文与数据库的密文进行比对
         * MD5 可以暴力破解
         *  穷举法
         *  为了提高密码的复杂度,可以进行加盐处理 1 + 字符串[盐] 9999 = 10000
         */
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);
        //3. 插入数据库数据
        int row = userMapper.insert(user);
        //4. 返回封装结果
        if (row <= 0){
            log.info("注册失败,请稍后再试!");
            return R.fail("注册失败,请稍后再试!");
        }
        log.info("注册成功!");
        return R.ok("注册成功!");
    }

    /**
     * 登录业务
     * 1. 密码的加密和加盐处理
     * 2. 账号和密码进行数据库查询 , 返回一个完整的数据库user 对象
     * 3. 判断返回结果
     * @param userLoginParam 账号和密码已经校验 , 但是密码是明文!
     * @return
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        // 1. 密码的加密和加盐处理
        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SLAT);
        // 2. 账号和密码进行数据库查询 , 返回一个完整的数据库user 对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userLoginParam.getUserName());
        wrapper.eq("password",newPwd);

        User user = userMapper.selectOne(wrapper);
        // 3.  判断返回结果
        if (user == null){
            log.info("账号或密码错误!");
            return R.fail("账号或密码错误!");
        }
        log.info("登录成功!");
        //不返回password数据
        user.setPassword(null);
        return R.ok("登录成功!",user);

    }
}

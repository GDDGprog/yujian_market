package com.yujian.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yujian.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

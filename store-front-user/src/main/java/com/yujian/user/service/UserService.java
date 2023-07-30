package com.yujian.user.service;

import com.yujian.param.UserCheckParam;
import com.yujian.param.UserLoginParam;
import com.yujian.pojo.User;
import com.yujian.utils.R;

public interface UserService {
    /**
     * 检查账号是否可用
     * @param userCheckParam 账号参数 已经校验完毕
     * @return 检查结果 001 004
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 注册业务
     * @param user 参数已经校验,但是密码是明文!
     * @return 结果 001 004
     */
    R register(User user);

    /**
     * 登录业务
     * @param userLoginParam 账号和密码已经校验 , 但是密码是明文!
     * @return 001 004
     */
    R login(UserLoginParam userLoginParam);
}

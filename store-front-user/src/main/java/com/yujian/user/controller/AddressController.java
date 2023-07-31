package com.yujian.user.controller;

import com.yujian.param.AddressListParam;
import com.yujian.param.AddressParam;
import com.yujian.param.AddressRemoveParam;
import com.yujian.pojo.Address;
import com.yujian.user.service.AddressService;
import com.yujian.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    public R list(@RequestBody @Validated AddressListParam adressListParam, BindingResult result) {
        if (result.hasErrors()){
            return R.fail("参数异常,查询失败!!!");
        }

        return addressService.list(adressListParam.getUserId());
    }

    @PostMapping("/save")
    public R save(@RequestBody @Validated AddressParam addressParam, BindingResult result) {
        if (result.hasErrors()){
            return R.fail("参数异常,保存失败!!!");
        }
        Address address = addressParam.getAdd();
        address.setUserId(addressParam.getUserId());
        return addressService.save(address);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result) {
        if (result.hasErrors()){
            return R.fail("参数异常,删除失败!!!");
        }
        return addressService.remove(addressRemoveParam.getId());
    }


}

package com.zheng.web;

import com.zheng.model.User;
import com.zheng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * Created by zhenghui on 2018/1/29.
 */
@Controller
public class GeneralController {
    @Resource
    private UserService userService;

    @GetMapping("/dynamic")
    public String testDynamicDataSource(Model model){
        User user = userService.findById(1);
        model.addAttribute("name",user.getName());
        return "home";
    }
}

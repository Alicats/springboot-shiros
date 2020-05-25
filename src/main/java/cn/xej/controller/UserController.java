package cn.xej.controller;

import cn.xej.common.RespObj;
import cn.xej.pojo.User;
import cn.xej.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/toLogin")
    @ResponseBody
    public RespObj toLogin(String userId, String password, HttpSession session){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userId,password);

        try {
            subject.login(token);
            session.setAttribute("currentUserName",((User)subject.getPrincipal()).getName());
            return RespObj.build(200,"ok",null);
        } catch (Exception e) {
            System.out.println("账号或密码错误");
            return RespObj.build(500,"账号或密码错误",null);
        }

    }

    @PostMapping("/logout")
    @ResponseBody
    public RespObj logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RespObj.build(200,"ok",null);
    }

    // 下载题目模板excel
    @GetMapping("/imageDownload")
    @ResponseBody
    public void excelDownload(HttpServletResponse response){
        userService.imageDownload(response);
    }


    @GetMapping("/getAllUser")
    @ResponseBody
    public JSONObject getAllUser(){
        JSONObject jsonObject = new JSONObject();
        List<User> userList = userService.getAllUser();


        if(userList==null){
            jsonObject.put("total",0);
            jsonObject.put("data",new ArrayList());
        }else{
            jsonObject.put("total",userList.size());
            jsonObject.put("data",userList);

        }
        System.out.println(jsonObject);
        return jsonObject;
    }
}

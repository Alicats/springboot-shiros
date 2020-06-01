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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/toRegister")
    @ResponseBody
    public RespObj toRegister(String userId, String password,String name,String date,Integer time){
        System.out.println("userId: "+userId+" password: "+password+" name: "+name+" date: "+date+" time: "+time);

        return userService.addUser(userId,password,name,date,time);
    }

    @PostMapping("/logout")
    @ResponseBody
    public RespObj logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RespObj.build(200,"ok",null);
    }

    // 下载图片
    @GetMapping("/imageDownload")
    @ResponseBody
    public void imageDownload(HttpServletResponse response){
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
        return jsonObject;
    }

    @GetMapping("/stopJob/{userId}")
    @ResponseBody
    public RespObj stopJob(@PathVariable("userId")String userId){
        System.out.println("userId: "+userId);
        return userService.stopJob(userId);
    }

    @GetMapping("/resumeJob/{userId}")
    @ResponseBody
    public RespObj resumeJob(@PathVariable("userId")String userId){
        System.out.println("userId: "+userId);
        return userService.resumeJob(userId);
    }
}

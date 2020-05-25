package cn.xej.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysController {

    @RequestMapping({"/","/welcome"})
    public String welcome(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/add")
    public String add(){
        return "pages/add";
    }

    @RequestMapping("/update")
    public String update(){
        return "pages/update";
    }

    @RequestMapping("/demo1")
    public String demo1(){
        return "demo1";
    }

    @RequestMapping("/downimages")
    public String downimages(){
        return "pages/downimages";
    }

    @RequestMapping("/userList")
    public String userList(){
        return "pages/userList";
    }

}

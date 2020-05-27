package cn.xej.service;

import cn.xej.common.RespObj;
import cn.xej.pojo.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    public User findByUserId(String userId);

    public List<String> getRolesIdByUserId(String userId);

    public void imageDownload(HttpServletResponse response);

    public List<User> getAllUser();

    public RespObj addUser(String userId, String password,String name,String date,Integer time);

    public RespObj startUpdateUserStatus(String userId,Integer time,String date);

    public RespObj closeUpdateUserStatus(String userId);

    public RespObj stopJob(String userId);

    public RespObj resumeJob(String userId);
}

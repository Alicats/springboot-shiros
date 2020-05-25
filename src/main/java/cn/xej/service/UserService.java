package cn.xej.service;

import cn.xej.pojo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    public User findByUserId(String userId);

    public List<String> getRolesIdByUserId(String userId);

    public void imageDownload(HttpServletResponse response);

    public List<User> getAllUser();
}

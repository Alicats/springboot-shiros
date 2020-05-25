package cn.xej.mapper;

import cn.xej.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public User findByUserId(String userId);

    public List<String> queryRolesIdByUserId(String userId);

    public List<User> getAllUser();
}

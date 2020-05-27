package cn.xej.mapper;

import cn.xej.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public User findByUserId(String userId);

    public List<String> queryRolesIdByUserId(String userId);

    public List<User> getAllUser();

    public void addUser(User user);

    public void startUpdateUserStatus(String userId);

    public void closeUpdateUserStatus(String userId);
}

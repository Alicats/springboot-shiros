package cn.xej.config;

import cn.xej.pojo.User;
import cn.xej.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();

        List<String> roles = userService.getRolesIdByUserId(user.getUserId());
        info.addRoles(roles);

        return info;
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        User user = userService.findByUserId(token.getUsername());

        if(user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getUserId()),getName());
        // 第一个参数，将会传到授权中进行获取，比如 User user = (User) principalCollection.getPrimaryPrincipal();
        // 第二个参数，是该用户数据库里的密码
        // 第三个参数，是加密的salt
        // 第四个参数，是当前类名
    }
}

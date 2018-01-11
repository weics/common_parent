package com.itheima.bos.service.system;

import com.itheima.bos.dao.system.UserDao;
import com.itheima.bos.domain.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的realm，负责完成认证和授权操作
 */
public class BosRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
        //获得页面提交的用户名
        String username = passwordToken.getUsername();
        //根据页面提交的用户名查询数据库中的记录
        User user = userDao.findByUsername(username);
        if (user == null) {
            //用户名不存在
            return null;
        }
        //说明页面提交的用户名存在
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权信息对象,用于为当前用户进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //为当前用户授权一个权限,关键字为area
        info.addStringPermission("area");//area字符串标售授权的关键字
        info.addStringPermission("subArea");
        return info;
    }


}

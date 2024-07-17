package com.example.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.entity.Permission;
import com.example.entity.PermissionExample;
import com.example.entity.RolesPermissions;
import com.example.entity.RolesPermissionsExample;
import com.example.entity.User;
import com.example.entity.UsersRoles;
import com.example.entity.UsersRolesExample;
import com.example.mapper.PermissionMapper;
import com.example.mapper.RoleMapper;
import com.example.mapper.RolesPermissionsMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.UsersRolesMapper;

@Service
public class ShiroProcess extends AuthorizingRealm{
	private static final Logger logger = LoggerFactory.getLogger(ShiroProcess.class);

	
	/**
	 * 用户认证
	 */
		@Override
		protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			if (logger.isDebugEnabled()) {
				logger.debug("开始认证用户信息");
			}
			 //UsernamePasswordToken对象用来存放提交的登录信息
	        UsernamePasswordToken token1=(UsernamePasswordToken) token;
	        String username = ResourceBundle.getBundle("application").getString("mynacos.login.name");
			String password = ResourceBundle.getBundle("application").getString("mynacos.login.password");
	        //查出是否有此用户
	       User user = new User();
	       user.setUserName(username);
	       user.setPassword(password);
	        if(user!=null){
	            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
	            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	        }
	        return null;
		}
		
	
	/**
	 * 用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始为用户授权=====");
		}
		// 添加权限 和 角色信息
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				// 获取当前登陆用户
				User user  =(User) SecurityUtils.getSubject().getPrincipal();
        if(user!=null){
            return grantedAuthority(authorizationInfo, user);
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
	}

	private AuthorizationInfo grantedAuthority(SimpleAuthorizationInfo authorizationInfo, User user) {
		//给用户授予角色和权限
		authorizationInfo.addRole("select");
		authorizationInfo.addRole("delete");
		authorizationInfo.addRole("update");
		authorizationInfo.addRole("add");
		authorizationInfo.addRole("all");
		return authorizationInfo;
	}
}

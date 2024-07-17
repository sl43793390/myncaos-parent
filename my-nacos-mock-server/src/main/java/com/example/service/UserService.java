//package com.example.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.entity.RolesPermissions;
//import com.example.entity.RolesPermissionsExample;
//import com.example.entity.User;
//import com.example.entity.UserExample;
//import com.example.entity.UsersRoles;
//import com.example.entity.UsersRolesExample;
//import com.example.mapper.PermissionMapper;
//import com.example.mapper.RoleMapper;
//import com.example.mapper.RolesPermissionsMapper;
//import com.example.mapper.UserMapper;
//import com.example.mapper.UsersRolesMapper;
//
//@Service
//public class UserService {
//
//	@Autowired
//	private UserMapper userMapper;
//	@Autowired
//	private RoleMapper roleMapper;
//	@Autowired
//	private UsersRolesMapper userRoleMapper;
//	@Autowired
//	private RolesPermissionsMapper rolePermissionMapper;
//	@Autowired
//	private PermissionMapper permissionMapper;
//	
//	public User getUserById(String userId) {
//		UserExample ex = new UserExample();
//		ex.createCriteria().andUserIdEqualTo(userId);
//		List<User> selectByExample = userMapper.selectByExample(ex);
//		if (null != selectByExample && !selectByExample.isEmpty()) {
//			return selectByExample.get(0);
//		}
//		return null;
//	}
//	
//	public UsersRoles getRoleByUserId(String userId) {
//		UsersRolesExample ex = new UsersRolesExample();
//		ex.createCriteria().andUserIdEqualTo(userId);
//		List<UsersRoles> selectByExample = userRoleMapper.selectByExample(ex);
//		if (null != selectByExample && !selectByExample.isEmpty()) {
//			return selectByExample.get(0);
//		}
//		return null;
//	}
//	
//	public List<RolesPermissions> getRolePermissionByRoleId(String roleId){
//		RolesPermissionsExample ex = new RolesPermissionsExample();
//		ex.createCriteria().andRoleIdEqualTo(roleId);
//		List<RolesPermissions> selectByExample = rolePermissionMapper.selectByExample(ex);
//		return selectByExample;
//	}
//	
//}

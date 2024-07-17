package com.example.mapper;

import com.example.entity.UsersRoles;
import com.example.entity.UsersRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersRolesMapper {
    int deleteByExample(UsersRolesExample example);

    int deleteByPrimaryKey(@Param("userId") String userId, @Param("roleId") String roleId);

    int insert(UsersRoles record);

    int insertSelective(UsersRoles record);

    List<UsersRoles> selectByExample(UsersRolesExample example);

    UsersRoles selectByPrimaryKey(@Param("userId") String userId, @Param("roleId") String roleId);

    int updateByExampleSelective(@Param("record") UsersRoles record, @Param("example") UsersRolesExample example);

    int updateByExample(@Param("record") UsersRoles record, @Param("example") UsersRolesExample example);

    int updateByPrimaryKeySelective(UsersRoles record);

    int updateByPrimaryKey(UsersRoles record);
}
package com.rammus.dao;

import java.util.List;

import javax.annotation.Resource;

import com.rammus.entity.User;

public interface UserDao {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User selectByName(String name);

	// 根据角色id查询出角色名称
	int selectByRole(String name);

	// 根据角色id查询出权限名
	List<String> selectByRoleId(Integer roleId);
}
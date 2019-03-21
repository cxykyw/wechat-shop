package com.kyw.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kyw.common.mybatis.BaseDao;
import com.kyw.entity.UserEntity;

@Mapper
public interface UserDao extends BaseDao{

	@Select("select ID,USERNAME,PASSWORD,PHONE,EMAIL,CREATED,UPDATED from mb_user where phone=#{phone} and password=#{password}")
	public UserEntity getUserByPhoneAndPwd(@Param("phone")String phone,@Param("password")String password);
	
	@Select("select ID,USERNAME,PASSWORD,PHONE,EMAIL,CREATED,UPDATED from mb_user where id=#{id}")
	public UserEntity getUserById(@Param("id")Long id);
	
	@Select("select ID,USERNAME,PASSWORD,PHONE,EMAIL,CREATED,UPDATED from mb_user where openid=#{openid}")
	public UserEntity findUserByOpenId(@Param("openid")String openid);
	
	@Update("update mb_user set openid=#{openid},updated=#{updated} where id=#{id}")
	public void updateUserByOpenId(@Param("openid")String openid,@Param("updated")Timestamp updated,@Param("id")Long id);
}

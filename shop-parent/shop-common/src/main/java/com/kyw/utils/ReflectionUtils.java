package com.kyw.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import org.apache.ibatis.jdbc.SQL;

import com.kyw.common.entity.TestEntity;

/**
 * 反射工具类
 * 
 * @author Administrator
 *
 */
public class ReflectionUtils {

	/**
	 * 获取属性名称
	 * @param obj
	 * @return
	 */
	public static String getFields(Object obj) {
		if (obj == null) {
			return null;
		}
		Field[] fields_parent = obj.getClass().getSuperclass().getDeclaredFields();
		Field[] fields_current = obj.getClass().getDeclaredFields();
		String parent = getParentAndCurrentFields(fields_parent);
		String current = getParentAndCurrentFields(fields_current);
		return parent + "," + current;
	}
	
	/**
	 * 获取到属性值
	 * @param obj
	 * @return
	 */
	public static String getValues(Object obj) {
		if (obj == null) {
			return null;
		}
		Field[] fields_parent = obj.getClass().getSuperclass().getDeclaredFields();
		Field[] fields_current = obj.getClass().getDeclaredFields();
		String parent = getParentAndCurrentValues(obj,fields_parent);
		String current = getParentAndCurrentValues(obj,fields_current);
		return parent + "," + current;
	}

	public static String getParentAndCurrentFields(Field[] declaredFields) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < declaredFields.length; i++) {
			//获取到属性名称
			bf.append(declaredFields[i].getName());
			if (i < declaredFields.length - 1) {
				bf.append(",");
			}
		}
		return bf.toString();
	}

	public static String getParentAndCurrentValues(Object obj, Field[] declaredFields){
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < declaredFields.length; i++) {
			try {
				Field field = declaredFields[i];
				field.setAccessible(true);
				
				//获取到属性值
				Object value = field.get(obj);
				if(value!=null && (value instanceof String || value instanceof Timestamp)) {
					bf.append("'"+value+"'");
				}else {
					bf.append(value);
				}
				
				if (i < declaredFields.length - 1) {
					bf.append(",");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return bf.toString();
	}

	/**
	 * 获取类的属性，拼接属性sql
	 * 
	 * @return
	 */
	/*
	 * static public String getInsertFileds(Object obj) { if(obj == null) { return
	 * null; } StringBuffer bf = new StringBuffer();
	 * 
	 * Field[] fields_parent = obj.getClass().getSuperclass().getDeclaredFields();
	 * for (int i = 0; i < fields_parent.length; i++) {
	 * bf.append(fields_parent[i].getName()); bf.append(","); }
	 * 
	 * Class<? extends Object> classInfo = obj.getClass(); Field[] declaredFields =
	 * classInfo.getDeclaredFields();
	 * 
	 * for (int i = 0; i < declaredFields.length; i++) {
	 * bf.append(declaredFields[i].getName()); if(i<declaredFields.length-1) {
	 * bf.append(","); } } return bf.toString(); }
	 */

	public static void main(String[] args) {
		TestEntity testEntity = new TestEntity();
		testEntity.setUserName("kyw");
		testEntity.setPassword("1213113");
		testEntity.setCreated(DateUtils.getTimestamp());
		testEntity.setUpdated(DateUtils.getTimestamp());
		String insertFileds = getFields(testEntity);
		String values = getValues(testEntity);
		System.out.println(insertFileds);
		System.out.println(values);
		
		SQL sql = new SQL() {{
			INSERT_INTO("mb_user");
			VALUES(insertFileds, values);
		}};
		System.out.println(sql.toString());
	}
}

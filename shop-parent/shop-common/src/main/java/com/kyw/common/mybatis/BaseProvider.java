package com.kyw.common.mybatis;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.kyw.utils.ReflectionUtils;

public class BaseProvider {

	public String save(Map<String,Object> map) {
		//实体类
		Object object = map.get("oj");
		//表名
		String table = (String) map.get("table");
		
		//生成添加的sql语句,使用反射机制
		String insertFileds = ReflectionUtils.getFields(object);
		String values = ReflectionUtils.getValues(object);
		SQL sql = new SQL() {{
			INSERT_INTO(table);
			VALUES(insertFileds, values);
		}};
		
		
		return sql.toString();
	}
}

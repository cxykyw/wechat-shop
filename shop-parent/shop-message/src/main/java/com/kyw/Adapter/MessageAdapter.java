package com.kyw.Adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 消息适配器
 * @author Administrator
 *
 */
public interface MessageAdapter {
	public void distribute(	JSONObject jsonObject);
}

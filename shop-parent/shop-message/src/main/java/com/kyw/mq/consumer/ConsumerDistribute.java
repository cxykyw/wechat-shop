package com.kyw.mq.consumer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kyw.Adapter.MessageAdapter;
import com.kyw.constants.MQInterfaceType;
import com.kyw.server.SMSMailboxService;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:(mq分发)
 * @author Administrator
 *
 */
@Slf4j
@Component
public class ConsumerDistribute {

	@Autowired
	private SMSMailboxService sMSMailboxService;
	
	@JmsListener(destination="email_queue")
	public void distribute(String json) {
		log.info("####收到消息,消息内容为:{}",json);
		if(StringUtils.isEmpty(json)) {
			return;
		}
		
		JSONObject parseObject = JSONObject.parseObject(json);
		JSONObject header = parseObject.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");
		
		if (StringUtils.isEmpty(interfaceType)) {
			return;
		}
		MessageAdapter messageAdapter = null;
		
		switch (interfaceType) {
		case MQInterfaceType.SMS_MAIL:
			messageAdapter = sMSMailboxService;
			break;

		default:
			break;
		}
		JSONObject content = parseObject.getJSONObject("content");
		messageAdapter.distribute(content);
	}
	
}

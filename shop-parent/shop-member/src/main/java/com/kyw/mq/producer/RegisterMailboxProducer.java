package com.kyw.mq.producer;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 使用MQ调用消息服务,发送注册邮件
 * @author Administrator
 *
 */
@Service("registerMailboxProducer")
public class RegisterMailboxProducer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	public void send(Destination destination,String json) {
		
		jmsMessagingTemplate.convertAndSend(destination, json);
	}
}

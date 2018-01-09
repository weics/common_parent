package com.itheima.mq.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.itheima.mq.utils.SMSUtils;

/**
 * 自定义一个消息监听器
 * @author zhaoqx
 *
 */
public class MyMessageListener implements MessageListener {
	//当指定队列中存在消息时，监听器自动执行
	public void onMessage(Message msg) {
		MapMessage mapMessage = (MapMessage) msg;
		String code;
		try {
			String phoneNumbers = mapMessage.getString("telephone");
			code = mapMessage.getString("validateCode");
			SMSUtils.sendValidateCode(phoneNumbers, code);
			System.out.println("消费者消费了一个消息，向:" + phoneNumbers + "发送验证码：" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

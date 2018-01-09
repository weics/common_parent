package com.itheima.mq.consumer;

import com.itheima.mq.utils.SMSUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MyMessageListener implements MessageListener {
    //当指定队列中存在消息是,监听器自动执行
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        String code;
        try {
            String phoneNumbers = mapMessage.getString("telephone");
            code = mapMessage.getString("validateCode");
            SMSUtils.sendValidateCode(phoneNumbers, code);
            System.out.println("消费者消费了一个消息,向: " + phoneNumbers + "发送验证码: " + code);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

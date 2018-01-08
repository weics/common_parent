package com.itheima.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MQProducer {
    //消息生成者,向队列中发送消息
    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //从工厂中获得一个连接
        Connection connection = connectionFactory.createConnection();
        //打开连接
        connection.start();
        //获得一个会话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个队列
        Queue queue = session.createQueue("heima");
        //消息生成者,向指定的队列中发送消息
        MessageProducer producer = session.createProducer(queue);
        //创建一个消息
        TextMessage msg = session.createTextMessage("hello ActiveMQ!!!");
        //向队列中发送消息
        producer.send(msg);
        session.close();
        connection.close();
    }
}

package com.itheima.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MQConsumer {
    //消息消费者,读取队列中的消息
    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //从工厂获得一个连接
        Connection connection = connectionFactory.createConnection();
        //打开连接
        connection.start();
        //获得一个会话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个队列
        Queue queue = session.createQueue("heima");
        //创建消息的消费者,从指定队列中读取消息
        MessageConsumer consumer = session.createConsumer(queue);
        //设置一个消息监听器,当指定队列中存在消息时,监听器自动执行
        consumer.setMessageListener(new MessageListener() {
            //当指定队列中存在消息是,监听器自动执行
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println("消费者消费了一个消息:" + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

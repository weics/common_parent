import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mq.xml")
public class ProducerTest {
    //注入消息发送的模板对象
    @Autowired
    @Qualifier("jmsQueueTemplate")//通过id注入
    private JmsTemplate jmsTemplate;

    @Test
    public void test1() {
        jmsTemplate.send("queue_1", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("telephone", "18600715065");
                message.setString("validateCode", "1234");
                return message;
            }
        });
    }
}

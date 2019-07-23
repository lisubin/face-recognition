package ijinbu.recognition.utils;

import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;

import javax.jms.*;
import javax.swing.*;
/*
 主要分为两种模式为队列模式和主题模式
 队列模式是连接处创建Queue,消费者会分食生产者发送的消息
 主题模式是Topic，所有订阅的消费者会受到生产者的所有消息
 */
public class ActiveMqUtil {


    public static void checkServer(){
        if(NetUtil.isUsableLocalPort(8161)){
            JOptionPane.showMessageDialog(null,"ActiveMq服务未启动");
            System.exit(1);
        }
    }
}

class TestProducer{
    private static String URL="tcp://127.0.0.1:61616";
    private static String topicName = "topic_style";
    public static void main(String[] args) throws JMSException {
        ActiveMqUtil.checkServer();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic queue = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage(String.format("第%d条消息", i));
            producer.send(textMessage);
            System.out.println("发送"+textMessage.getText());
        }

        connection.close();
    }
}

class TestConsumer{
    private static String URL = "tcp://127.0.0.1:61616";
    private static String topicName = "topic_style";
    private static String consumerName = "consumer"+ RandomUtil.randomString(5);
    public static void main(String[] args) throws JMSException {
       ActiveMqUtil.checkServer();
        System.out.println(consumerName);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic queue = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage=(TextMessage)message;
                try {
                    System.out.println(consumerName +" 接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }}

    class TestConsumer2{
    private static String URL = "tcp://127.0.0.1:61616";
    private static String topicName = "topic_style";
    private static String consumerName = "consumer"+ RandomUtil.randomString(5);
    public static void main(String[] args) throws JMSException {
       ActiveMqUtil.checkServer();
        System.out.println(consumerName);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic queue = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage=(TextMessage)message;
                try {
                    System.out.println(consumerName +" 接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}


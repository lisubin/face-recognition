package ijinbu.recognition.utils;


import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.RandomUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ提供了四种Exchange模式：fanout,direct,topic,header 。 header模式在实际使用中较少，这里只讨论前三种模式.
 * 1.fanout 模式就是广播模式~
 * 消息来了，会发给所有的队列~
 * 2.Direct 模式就是指定队列模式， 消息来了，只发给指定的 Queue, 其他Queue 都收不到。
 * 3.topic模式，注意这里的主题模式，和 ActivityMQ 里的不一样。
 *   ActivityMQ 里的主题，更像是广播模式,而这里是对订阅了专门匹配的人发送对应的消息
 */
public class RabbitMqUtil {

    public static void checkServer(int port){
        try {
            (new Socket("139.196.94.99",port)).close();
            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("未连接到RabbitMq服务");
        }
    }

    public static void main(String[] args) {

    }
}

    class TestProducer2{
        public final static String EXCHANGE_NAME="direct_queue";
        public static void main(String[] args) {

            ConnectionFactory factory = new ConnectionFactory();
            try {
                RabbitMqUtil.checkServer(5672);
                factory.setHost("139.196.94.99");
                factory.setUsername("admin");
                factory.setPassword("admin");
                factory.setPort(5672);
                factory.setVirtualHost("my_vhost");
                //创建连接工厂
                //设置RabbitMQ相关信息
                //创建一个新的连接
                Connection connection = factory.newConnection();
                //创建一个通道
                Channel channel = connection.createChannel();
                for (int i = 0; i < 100; i++) {
                    String message = "direct 消息 " +i;
                    //发送消息到队列中
                    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                    System.out.println("发送消息： " + message);

                }
                //关闭通道和连接
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    class TestConsumer3{
//        public final static String EXCHANGE_NAME="direct_queue";
        public final static String QUEUE_NAME="direct_queue";

        public static void main(String[] args) throws IOException, TimeoutException {
            //为当前消费者取随机名
            String name = "consumer-"+ RandomUtil.randomString(5);

            //判断服务器是否启动
            RabbitMqUtil.checkServer(5672);
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ地址
            factory.setHost("139.196.94.99");
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setPort(5672);
            factory.setVirtualHost("my_vhost");
            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();
            //交换机声明（参数为：交换机名称；交换机类型）
            //channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            channel.queueDeclare(QUEUE_NAME,false,false,true,null);
//            //获取一个临时队列
//            String queueName = channel.queueDeclare().getQueue();
//            //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
//            channel.queueBind(queueName,QUEUE_NAME,"");

            System.out.println(name +" 等待接受消息");
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(name + " 接收到消息 '" + message + "'");
                }
            };

            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(QUEUE_NAME, true, consumer);
        }
    }

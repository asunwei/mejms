package com.learn.java.mejms.pointtopoint;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.TransportListener;

/**
 * 队列消息的发送
 * 
 * @author wei.sun02
 *
 */
public class Sender {
	//测试模拟发送
	public static void main(String[] args) {
		send();
	}

	// 默认的连接用户名
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	// 队列消息的发送
	public static void send() {
		// 连接
		Connection connection;
		// 会话接手或者发送消息的线程
		Session session;
		// 消息的目的地
		Destination destination;
		// 消息的生产者
		MessageProducer producer;
		// 实例化连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Sender.USERNAME, Sender.PASSWORD, Sender.BROKEURL);
		// connectionFactory.setTrustAllPackages(true);
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			/*
			 *  第一个参数是是否是事务型消息，设置为true,第二个参数无效
			 *  第二个参数是
			 *  Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
			 *  Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
			 *  时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
			 *  DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
			 *  待测试
			 */
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			//创建一个名字为 myfirst-queue 的消息队列
			destination = session.createQueue("myfirst-queue"); // 点对点模型
			//创建消息的生产者
			producer = session.createProducer(destination);
			// 消息持久化，默认就是持久的（未消费的消息会持久化）
			producer.setDeliveryMode(DeliveryMode.PERSISTENT); 
			// ObjectMessage
			MqBean mqBean = new MqBean();
			mqBean.setAge(20);
			int i = 0;
			//String str;
			while (true) {
				i++;
				// str = "小黄" + i;
				// producer.send(session.createTextMessage(str));
				mqBean.setName("HERO" + i);
				//session创建一条文本信息,通过生产者producer发送消息
				producer.send(session.createObjectMessage(mqBean));
				Thread.sleep(1000);
			}
			// producer.close();

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

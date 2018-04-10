package com.learn.java.mejms.pointtopoint;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 队列消息的接收
 * 
 * @author wei.sun02
 *
 */
public class Receiver {
	// 默认的连接用户名
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	public static void main(String[] args) {
		receive();
	}

	// 消息队列接收
	public static void receive() {
		// 连接
		Connection connection;
		// 会话接手或者发送消息的线程
		Session session;
		// 消息的目的地
		Destination destination;
		// 消息的消费者
		MessageConsumer consumer;
		// 实例化连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Receiver.USERNAME,
				Receiver.PASSWORD, Receiver.BROKEURL);
		// connectionFactory.setTrustAllPackages(true);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			// 这个最好还是有事务
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 获取消息队列中名称为myfirst-queue的消息，点对点模式
			destination = session.createQueue("myfirst-queue");
			//创造消息消费者
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					try {
						if (null != message) {
							MqBean mqBean = (MqBean) ((ObjectMessage) message).getObject();
							System.out.println("接收到消息" + mqBean.getName());
						}
						// if(null != message){
						// String str = ((TextMessage)message).getText();
						// System.out.println("接收到消息 : "+str);
						// }
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

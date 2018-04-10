package com.learn.java.mejms.publishsubscribe;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 订阅消息的发送
 * 
 * @author wei.sun02
 *
 */
public class Publisher {
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static void main(String[] args) throws JMSException {
		// 默认连接地址
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Publisher.BROKEURL);
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("myTopic.messages");

		MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i=0;;i++) {
			TextMessage message = session.createTextMessage();
			message.setText("message_" + i);
			producer.send(message);
			System.out.println("Sent message: " + message.getText());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// session.close();
		// connection.stop();
		// connection.close();
	}
}

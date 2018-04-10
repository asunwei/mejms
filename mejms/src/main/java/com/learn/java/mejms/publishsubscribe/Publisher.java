package com.learn.java.mejms.publishsubscribe;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ������Ϣ�ķ���
 * 
 * @author wei.sun02
 *
 */
public class Publisher {
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static void main(String[] args) throws JMSException {
		// Ĭ�����ӵ�ַ
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

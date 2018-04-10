package com.learn.java.mejms.publishsubscribe;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ������Ϣ�Ľ���
 * 
 * Number Of Pending Messages �ȴ����ѵ���Ϣ����ǵ�ǰδ�����е��������������Ϊ�ܽ�����-�ܳ������� Messages
 * Enqueued ������е���Ϣ������е�������,���������еġ��������ֻ������ Messages Dequeued
 * ���˶��е���Ϣ�������Ϊ�����������ѵ�������
 * 
 * @author wei.sun02
 *
 */
public class Subscriber {
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(Subscriber.BROKEURL);
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("myTopic.messages");

		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage tm = (TextMessage) message;
				try {
					System.out.println("Received message: " + tm.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		// session.close();
		// connection.stop();
		// connection.close();
	}
}

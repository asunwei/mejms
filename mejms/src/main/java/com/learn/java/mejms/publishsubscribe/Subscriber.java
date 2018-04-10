package com.learn.java.mejms.publishsubscribe;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 订阅消息的接收
 * 
 * Number Of Pending Messages 等待消费的消息这个是当前未出队列的数量。可以理解为总接收数-总出队列数 Messages
 * Enqueued 进入队列的消息进入队列的总数量,包括出队列的。这个数量只增不减 Messages Dequeued
 * 出了队列的消息可以理解为是消费这消费掉的数量
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

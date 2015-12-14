package org.bytecodeandcode.spring.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.bytecodeandcode.spring.jms.properties.jms.AdditionalJmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"org.bytecodeandcode.spring"})
@EnableJms
public class Application {

	public static final String TOPIC_NAME = "person.status.topic";
	public static final String QUEUE_NAME = "person.status.queue";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Topic topic() {
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(TOPIC_NAME);

		return activeMQTopic;
	}
	
	/*@Bean
	public Queue queue() {
		return new ActiveMQQueue(QUEUE_NAME);
	}*/

	
	@Bean
	public DefaultJmsListenerContainerFactory containerFactory(ConnectionFactory connectionFactory,
			AdditionalJmsProperties additionalJmsProperties) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrency("1");

		factory.setClientId(additionalJmsProperties.getClientId());
		factory.setPubSubDomain(true);
		factory.setSubscriptionDurable(true);

		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Destination destination) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);

		return jmsTemplate;
	}
}

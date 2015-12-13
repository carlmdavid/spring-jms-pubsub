package org.bytecodeandcode.spring.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class Application {

	public static final String TOPIC_NAME = "person.status.topic";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Topic topic() {
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(TOPIC_NAME);
		
		return activeMQTopic;
	}
	
	@Bean
	public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory, Topic topic) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(topic);
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
}

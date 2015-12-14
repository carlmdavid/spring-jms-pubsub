package org.bytecodeandcode.spring.jms.consumer;

import org.bytecodeandcode.spring.batch.persistence.domain.Person;
import org.bytecodeandcode.spring.jms.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private static Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(
			destination = Application.TOPIC_NAME
			, selector = "REQUEST_TYPE = 'person' AND PERSON_ID = 2"
			, subscription = "test.subscriber"
			, id = "client.test.id"
			, containerFactory = "containerFactory"
			)
	public void receiveStatus(Person person) {
		logger.info("Received: " + person);
	}
}

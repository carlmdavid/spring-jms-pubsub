package org.bytecodeandcode.spring.jms.producer;

import javax.jms.Topic;

import org.apache.commons.lang3.RandomStringUtils;
import org.bytecodeandcode.spring.batch.persistence.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer /*implements CommandLineRunner*/ {

	private static Logger logger = LoggerFactory.getLogger(Producer.class);

	private JmsMessagingTemplate template;
	private Topic topic;

	@Autowired
	public Producer(JmsMessagingTemplate template, Topic topic) {
		super();
		this.template = template;
		this.topic = topic;
	}

/*	@Override
	public void run(String... args) throws Exception {
		// Build a person
		logger.info("building person...");
		Person person = new Person();
		person.setFirstName(getRandomChars());
		person.setLastName(getRandomChars());

		// Push the person to Topic
		send(person);
	}*/

	private String getRandomChars() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public void send(Person person) {
		logger.info("Pushing person {} to topic...", person);
		this.template.convertAndSend(this.topic, person);
	}

}

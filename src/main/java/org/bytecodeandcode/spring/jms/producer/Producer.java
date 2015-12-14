package org.bytecodeandcode.spring.jms.producer;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.apache.commons.lang3.RandomStringUtils;
import org.bytecodeandcode.spring.batch.persistence.dao.PersonRepository;
import org.bytecodeandcode.spring.batch.persistence.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private PersonRepository personRepository;
	
	private JmsMessagingTemplate template;
	private Destination destination;

	@Autowired
	public Producer(JmsMessagingTemplate template, Destination destination) {
		super();
		this.template = template;
		this.destination = destination;
	}

	@Override
	public void run(String... args) throws Exception {
		// Build a person
		for(int i = 0; i< 5; i++) {
		logger.info("building person...");
		Person person = new Person();
		person.setFirstName(getRandomChars());
		person.setLastName(getRandomChars());
		
		person = personRepository.save(person);

		// Push the person to Topic
		send(person, "human");
		send(person, "person");
		}
	}

	private String getRandomChars() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public void send(Person person, String requestType) {
		logger.info("Pushing person {} to topic...", person);
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("REQUEST_TYPE", requestType);
		requestMap.put("PERSON_ID", person.getPersonId());
		this.template.convertAndSend(this.destination, person, requestMap);
	}

}

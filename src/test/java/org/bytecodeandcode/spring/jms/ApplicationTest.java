/**
 * 
 */
package org.bytecodeandcode.spring.jms;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.bytecodeandcode.spring.batch.persistence.domain.Person;
import org.bytecodeandcode.spring.jms.producer.Producer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Carl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTest {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();
	
	
	@Autowired private Producer producer;
	
	@Test
	public void testPushAndPull() throws InterruptedException {
		// Build person
		Person person = new Person();
		person.setFirstName(getRandomChars());
		person.setLastName(getRandomChars());
		producer.send(person);
		
		Thread.sleep(5000);
		assertThat(outputCapture.toString(), containsString("Received:"));
	}

	private String getRandomChars() {
		return RandomStringUtils.randomAlphabetic(5);
	}

}

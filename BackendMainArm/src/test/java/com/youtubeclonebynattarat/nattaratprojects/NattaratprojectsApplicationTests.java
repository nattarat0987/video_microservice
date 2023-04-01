package com.youtubeclonebynattarat.nattaratprojects;


import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NattaratprojectsApplicationTests {
	@Autowired
	private EmailService emailService;
	interface TestEmail{
		String email = "nattarat.arm@gmail.com";
		String name = "พันธรรม";
		String token= "fdskfl;ksd122434235";
	}

	@Test
	public void sendEmail() throws BaseException, MessagingException {
		emailService.sendActivateUserEmail(TestEmail.email, TestEmail.name, TestEmail.token);
	}
	@Test
	void contextLoads() {
	}

}

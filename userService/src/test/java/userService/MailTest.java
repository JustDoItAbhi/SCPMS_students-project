package userService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class MailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void testSendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("erarvinder2012@gmail.com");
        message.setTo("erarvinder2012@gmail.com");
        message.setSubject("Test Email");
        message.setText("This is a test email from Spring Boot");
        mailSender.send(message);
    }
}


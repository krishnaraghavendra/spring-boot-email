package org.common.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.common.email.service.EmailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by abburi on 6/10/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailSenderConfig.class, EmailService.class})
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    private GreenMail testSmtp;


    @Test
    public void testEmail() throws MessagingException, IOException {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
        emailService.sendMail("toAddress@gmail.com", "Test Subject", "Test mail");

        testSmtp.waitForIncomingEmail(1);

        Message[] messages = testSmtp.getReceivedMessages();
        Assert.assertTrue(messages.length == 1);

        String message = messages[0].getSubject();

        Multipart part = (Multipart) messages[0].getContent();

        assertEquals(part.getCount(), 1);

        assertEquals("Test Subject", message);
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertTrue(body.contains("Test mail"));

        testSmtp.stop();


    }

    @Test
    public void testEmailWithFile() throws MessagingException, IOException {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();



        File file = new File(this.getClass().getClassLoader().getResource("TestFile.txt").getFile());
        emailService.sendMail("toAddress@gmail.com", "Test Subject", "Test mail", file);

        testSmtp.waitForIncomingEmail(1);

        Message[] messages = testSmtp.getReceivedMessages();
        Assert.assertTrue(messages.length == 1);

        String message = messages[0].getSubject();
        assertEquals("Test Subject", message);
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertTrue(body.contains("Test mail"));

        Multipart part = (Multipart) messages[0].getContent();

        assertEquals(part.getCount(), 2);

        BodyPart bodyPart = part.getBodyPart(1);
        assertEquals(bodyPart.getFileName(), "TestFile.txt");

        assertEquals(bodyPart.getContent().toString(), "Testing file to be sent as email attachment");

        testSmtp.stop();

    }

}

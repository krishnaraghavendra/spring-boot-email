package org.common.email.controller;

import org.common.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * Created by abburi on 6/18/17.
 */

@RestController
public class SendEmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/testSendEmail" , method = RequestMethod.GET)
    public void sendEmail(){
        try {
            emailService.sendMail("test@gmail.com", "Test Subject", "TestMessage");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

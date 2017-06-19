This demo uses spring-boot and spring mail to send emails. For testing purpose I used greenmail.

To run this application, please visit src/main/resources/application.properties and change email.sender.username, email.sender.password to your credentials. 

Running the EmailApplication.java will start this application in http://localhost:8080

For a simple test purpose i have added /testSendEmail to send out email. You can change the toAddress to see real email in SendEmailController. 
 
Note : Gmail will not allow using its smtp from less secured apps. You might need to switch off that feature while testing to send email. 
In gmail, my account goto Sign-in & Security --> Connected apps & sites --> Allow less secure apps: turn it on



[![Build Status](https://travis-ci.org/abburi03/spring-boot-email.svg?branch=master)](https://travis-ci.org/abburi03/spring-boot-email)

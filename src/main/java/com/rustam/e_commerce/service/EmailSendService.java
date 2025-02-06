package com.rustam.e_commerce.service;

import com.rustam.e_commerce.util.MailSenderUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailSendService {

    MailSenderUtil mailSenderUtil;

    public void sendOrderConfirmationEmail(String toEmail){
        mailSenderUtil.sendEmail(toEmail,"Rustam`s E-commerce",toEmail + " : Your order has been confirmed. Good luck.");
    }
}

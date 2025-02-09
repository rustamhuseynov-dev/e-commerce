package com.rustam.e_commerce.service;

import com.rustam.e_commerce.config.RedisTemplateConfig;
import com.rustam.e_commerce.dto.request.EmailVerificationRequest;
import com.rustam.e_commerce.exception.custom.EmailVerificationProcessFailedException;
import com.rustam.e_commerce.util.MailSenderUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailSendService {

    MailSenderUtil mailSenderUtil;
    RedisTemplate<String,String> redisTemplate;

    public void sendOrderConfirmationEmail(String toEmail){
        mailSenderUtil.sendEmail(toEmail,"Rustam`s E-commerce",toEmail + " : Your order has been confirmed. Good luck.");
    }

    public void verificationCode(String email) {
        String code = generateVerificationCode();
        redisTemplate.opsForValue().set(email, code, 1, TimeUnit.MINUTES);
        mailSenderUtil.sendEmail(email,code);
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void verificationCodeEqualsRequestCode(EmailVerificationRequest emailVerificationRequest) {
        String cachedCode = redisTemplate.opsForValue().get(emailVerificationRequest.getVerificationCode());
        if (cachedCode != null && cachedCode.equals(emailVerificationRequest.getVerificationCode())) {
            redisTemplate.delete(emailVerificationRequest.getEmail());
        }else {
            throw new EmailVerificationProcessFailedException("The Email Verification Process Failed");
        }
    }
}

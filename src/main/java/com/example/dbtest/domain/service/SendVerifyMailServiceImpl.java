package com.example.dbtest.domain.service;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.dbtest.domain.entity.User;

@Service
public class SendVerifyMailServiceImpl implements SendVerifyMailService {
    private final JavaMailSender mailSender;

    public SendVerifyMailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(User user) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setFrom("XXX Symtem <info@hoge.info>");
            helper.setTo(user.getEmail());
            helper.setSubject("Please verify your email.");

            String text = "<html><body><div>Hi " + user.getUsername() + ", welcome to XXX System!</div>" +
                    "<div><a href='http://localhost:8080/register/verify?id=" + user.getTempkey() + "'>Verify</a></div>" +
                    "</body></html>";

            helper.setText(text, true);
        });
    }

}
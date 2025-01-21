package com.snapit.notification.framework.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

import static javax.mail.Session.getInstance;

@Configuration
@RequiredArgsConstructor
public class SMTPConfig {

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.starttls-enable}")
    private String starttlsEnable;

    @Value("${mail.authentication.user}")
    private String user;

    @Value("${mail.authentication.password}")
    private String password;


    @Bean
    public Session getSession() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
        return getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }
}

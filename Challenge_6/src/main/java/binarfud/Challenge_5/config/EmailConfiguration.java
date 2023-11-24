package binarfud.Challenge_5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    private final ProviderConfiguration providerConfiguration;

    @Autowired
    public EmailConfiguration(ProviderConfiguration providerConfiguration) {
        this.providerConfiguration = providerConfiguration;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(providerConfiguration.getHost());
        mailSender.setPort(providerConfiguration.getPort());

        mailSender.setUsername(providerConfiguration.getUsername());
        mailSender.setPassword(providerConfiguration.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", providerConfiguration.isAuth());
        props.put("mail.smtp.starttls.enable", providerConfiguration.isStarttls());

        return mailSender;
    }

}

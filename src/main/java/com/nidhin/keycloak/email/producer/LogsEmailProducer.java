package com.nidhin.keycloak.email.producer;
import com.nidhin.keycloak.email.service.SendMailService;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
@ToString(callSuper = true)
public class LogsEmailProducer implements EmailProducer {

    @Override
    public void produce(String emailMessage, String destinationEmail) {
        log.info("produce email with message=" + emailMessage +
                " for destination=" + destinationEmail);

        SendMailService sendService = new SendMailService();
        sendService.send(destinationEmail, "Login to Weinvest keycloak", emailMessage);
    }
}

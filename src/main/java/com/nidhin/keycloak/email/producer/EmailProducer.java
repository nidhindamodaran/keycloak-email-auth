package com.nidhin.keycloak.email.producer;

public interface EmailProducer {

    void produce(String emailMessage, String destinationNumber);

}

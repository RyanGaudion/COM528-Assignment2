/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank.client.impl;


import java.util.logging.Level;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.solent.com504.oodd.bank.model.client.IBankRestClient;
import org.solent.com504.oodd.bank.model.dto.TransactionRequest;
import org.solent.com504.oodd.bank.model.dto.TransactionResponse;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgaudion 
 * @author kpeacock
 * @author cgallen
 * The Bank REST Client is responsible for all REST calls to the Bank API and for deserialising these responses to Java Objects
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankRestClient implements IBankRestClient {

    final static Logger logger = LogManager.getLogger(BankRestClient.class);

    String urlStr;

    /** 
     * @param urlStr A String of the URL endpoint that the API is located at.
     */
    public BankRestClient(String urlStr) {
        this.urlStr = urlStr;
    }

    /**
     * @param request The TransactionRequest Object for the request
     * @return TransactionResponse
     * This method simply calls the Transaction Request endpoint for the API and returns a TransactionResponse object
     */
    @Override
    public TransactionResponse transferMoney(TransactionRequest request) {
        logger.debug("transferMoney called: ");

        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // allows client to decode json
        client.register(JacksonJsonProvider.class);

        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));

        TransactionResponse transactionReplyMessage = response.readEntity(TransactionResponse.class);

        logger.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReplyMessage);

        return transactionReplyMessage;

    }

    /**
     * @param request
     * @param userName
     * @param password
     * @return TransactionResponse
     * This method performs the same function as the other transferMoney method 
     * however this method provides Http Authentication using the username and 
     * password of the ShopKeeper
     */
    @Override
    public TransactionResponse transferMoney(TransactionRequest request, String userName, String password) {
        logger.debug("transferMoney called: ");

        // sets up logging for the client       
        Client client = ClientBuilder.newClient(new ClientConfig().register(
                new LoggingFeature(java.util.logging.Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
                        Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000)));

        // basic authentication
        HttpAuthenticationFeature basicAuthfeature = HttpAuthenticationFeature.basic(userName, password);
        client.register(basicAuthfeature);
        
        
        // allows client to decode json
        client.register(JacksonJsonProvider.class);
        WebTarget webTarget = client.target(urlStr).path("/transactionRequest");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));

        TransactionResponse transactionReplyMessage = response.readEntity(TransactionResponse.class);

        logger.debug("Response status=" + response.getStatus() + " ReplyMessage: " + transactionReplyMessage);

        return transactionReplyMessage;

    }
}

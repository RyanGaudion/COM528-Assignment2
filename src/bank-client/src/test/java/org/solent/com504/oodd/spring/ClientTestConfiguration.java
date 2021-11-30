package org.solent.com504.oodd.spring;


import org.solent.com504.oodd.ClientConfiguration;
import org.solent.com504.oodd.cart.dao.impl.PersistenceJPAConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rgaudion
 */
@Configuration
@Import({ClientConfiguration.class, PersistenceJPAConfig.class})
@PropertySource("classpath:persistence-test.properties")
public class ClientTestConfiguration {
    
}
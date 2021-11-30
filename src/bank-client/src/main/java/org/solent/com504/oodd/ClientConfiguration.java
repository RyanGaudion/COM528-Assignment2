package org.solent.com504.oodd;


import org.solent.com504.oodd.properties.dao.impl.PropertiesSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rgaud
 */
@Configuration

@ComponentScan(basePackages = {"org.solent.com504.oodd.bank.client.impl", "org.solent.com504.oodd.bank.model.client"})
@Import({PropertiesSpringConfig.class})
public class ClientConfiguration {

}
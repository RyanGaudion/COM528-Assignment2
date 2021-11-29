/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.dao.test;

import org.solent.com504.oodd.cart.dao.impl.PersistenceJPAConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


/**
 *
 * @author cgallen
 */
@Configuration
@ComponentScan(basePackages = {"org.solent.com504.oodd.properties.dao.impl"})
@Import(PersistenceJPAConfig.class)
@PropertySource("classpath:persistence-test.properties")
public class DAOTestConfiguration {
    
}

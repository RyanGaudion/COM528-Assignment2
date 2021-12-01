/*
 * Copyright 2021 rgaud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.solent.com504.oodd.properties;

/**
 *
 * @author rgaud
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.runner.RunWith;
import org.solent.com504.oodd.cart.dao.test.DAOTestConfiguration;
import org.solent.com504.oodd.properties.dao.impl.PropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the OrderServiceConfig class
@ContextConfiguration(classes = DAOTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class PropertiesDaoTest {

    final static Logger LOG = LogManager.getLogger(PropertiesDaoTest.class);
    private File propertiesFile;
    private Properties properties = new Properties();
    
    @Autowired
    public PropertiesDao propertiesDao;
    
    public synchronized void setProperty(String propertyKey, String propertyValue) {
        properties.setProperty(propertyKey, propertyValue);
        //savePropertiesTest();
    }

    public synchronized String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    @Test
    /**
     * Find and load the default properties file. Expected: Pass. Find default
     * props file and load.
     */
    public void loadDefaultPropertiesTest() {
        InputStream input = null;
        try {
            LOG.debug("loading properties from: default");
            input = PropertiesDao.class.getClassLoader().getResourceAsStream("application.default.properties");
            properties.load(input);
            assertTrue(input != null);

        } catch (IOException ex) {
            LOG.error("cannot load properties", ex);

        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {

            }
        }
    }

    @Test
    /**
     * Test saving properties to temp properties file Expected: New prop values
     * saves to temp file.
     */
    public void savePropertiesTest() {
        
        try (OutputStream output = new FileOutputStream("tomcat/apache-tomcat-9.0.53/temp")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("org.solent.oodd.pos.service.apiUrl", "TEST");
            prop.setProperty("org.solent.oodd.pos.service.apiUsername", "TEST");
            prop.setProperty("org.solent.oodd.pos.service.apiPassword", "passwordTest");
            prop.setProperty("org.solent.oodd.pos.service.shopKeeperCard", "Test123456");
            
            prop.store(output, null);
            assertTrue(output != null);
            
            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}

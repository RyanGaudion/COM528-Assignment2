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
package org.solent.com504.oodd.properties.dao.impl;


/*
 * Copyright 2021 lholmes
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PropertiesDao {


    final static Logger logger = LogManager.getLogger(PropertiesDao.class);

    private File propertiesFile;

    private Properties properties = new Properties();

    public PropertiesDao() {
        String tempDir = System.getProperty("java.io.tmpdir");
        File propertiesFileLocal = new File(tempDir + "/application.properties");
        String propertiesFileLocation = propertiesFileLocal.getAbsolutePath();
        
        try {
            propertiesFile = new File(propertiesFileLocation);
            if (!propertiesFile.exists()) {
                logger.info("properties file does not exist: loading default ");   
                logger.debug(propertiesFileLocation);

                loadDefaultProperties();
            } else {
                logger.debug("Temp properties file found. Loading...");
                loadProperties();
            }
            logger.debug(propertiesFileLocation);
        } catch (Exception ex) {
            logger.error("cannot load properties", ex);
        }
    }

    // synchronized ensures changes are not made by another thread while reading
    public synchronized String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public synchronized void setProperty(String propertyKey, String propertyValue) {
        properties.setProperty(propertyKey, propertyValue);
        saveProperties();
    }

    private void saveProperties() {
        OutputStream output = null;
        try {
            logger.debug("saving properties to: " + propertiesFile.getAbsolutePath());

            output = new FileOutputStream(propertiesFile);
            String comments = "# properties file";
            properties.store(output, comments);

        } catch (IOException ex) {
            logger.error("cannot save properties", ex);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    private void loadProperties() {
        
        InputStream input = null;
        try {
            logger.debug("loading properties from: " + propertiesFile.getAbsolutePath());
            input = new FileInputStream(propertiesFile);
            properties.load(input);
        } catch (IOException ex) {
            logger.error("cannot load properties", ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    private void loadDefaultProperties() {
        InputStream input = null;
        try {
            logger.debug("loading properties from: " + propertiesFile.getAbsolutePath());
            input = PropertiesDao.class.getClassLoader().getResourceAsStream("application.default.properties");
            properties.load(input);
        } catch (IOException ex) {
            logger.error("cannot load properties", ex);

        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
            }
        }
    }

}


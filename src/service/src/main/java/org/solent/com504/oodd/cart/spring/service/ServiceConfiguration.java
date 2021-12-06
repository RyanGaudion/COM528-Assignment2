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
package org.solent.com504.oodd.cart.spring.service;

import org.solent.com504.oodd.ClientConfiguration;
import org.solent.com504.oodd.cart.dao.impl.PersistenceJPAConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for the service project
 * @author cgallen
 */
@Configuration

@ComponentScan(basePackages = {"org.solent.com504.oodd.cart.service",
    "org.solent.com504.oodd.cart.spring.service"
})
@Import({PersistenceJPAConfig.class, ClientConfiguration.class})
public class ServiceConfiguration {

}

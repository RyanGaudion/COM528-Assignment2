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
package org.solent.com504.oodd.cart.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author rgaud
 */
@SpringBootApplication(scanBasePackages = "org.solent.com504.oodd.cart.spring.web")
public class SpringBootJspApplication extends SpringBootServletInitializer {

    /**
     * Initial builder for the spring application
     * @param builder
     * @return SpringApplicationBUilder for the application
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootJspApplication.class);
    }

    /**
     * Initial main method for the application - runs the SpringApplication run method
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJspApplication.class);
    }
}
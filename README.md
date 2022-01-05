# COM528-Assignment2
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[Design Doc](https://github.com/RyanGaudion/COM528-Assignment2/blob/main/DESIGN.md) - Contains all the design models & diagrams as well as use cases, features & test plans.


--- 
This application is built using Java with [Spring MVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html) and Dependency Injection is handled using [Spring Boot](https://spring.io/projects/spring-boot). For data access I am using [Spring JPAs](https://spring.io/projects/spring-data-jpa) with [Hibernate](https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa). For testing I am using [JUnit](https://junit.org/junit5/) and for Logging, [Log4J](https://logging.apache.org/log4j/2.x/). The API communicates using [Jersey](https://eclipse-ee4j.github.io/jersey/) implementation of JAX-RS..

</br>


# Using the app
When using Netbeans the web app is deployed by default to `http://localhost:8080/shoppingCartApplication/home`

## Requirements
This application has been tested with the following. Other variations may work but have have not been tested and hence are not supported:
 - Java 11
 - Tomcat 9
 - Browser - Microsoft Edge

## Defaults
By default when the app runs it will create the following 2 accounts (where password is equal to username):
 - `globaladmin`
 - `user1234`

 By default the catalog already has items in it and each user already has orders/invoices too.

## Logging
Logging is handled by Log4j2 and the log config can be found at `web\src\main\resources\log4j2.xml`

2 Log files are created as part of this application - the standard log file containing all logs and a transaction specific log file - more information can be seen in the table below. The `${sys:catalina.base}` variable in the table below relates to your Apache Tomcat installation folder.

| Name      | Level | Location | Description |
| ----------- | ----------- |----------- | ----------- |
| app-shoppingcart      | `DEBUG` | ${sys:catalina.base}/logs/app/app-shoppingcart.log       | This contains all logs from level Debug and above from all namespaces of the application | 
| app-shoppingcart-transactions  | `INFO` | ${sys:catalina.base}/logs/app/app-shoppingcart-transactions.log        | This file contains all bank transactions between the shopping cart and the Bank API |

## Setup
As an administrator in the shopping cart application you can setup the details to use within the application by signing in and clicking "Manage Properties". From this page you can configure all the properties for the application including API url and Bank details.

## Issues 
If you're seeing unexpected behavior then make sure to delete the application.properties file in your tomcat instance's temp folder.  

Limitation - When uploading a file as an image the application is limited to images under 1048576 bytes (1mb) due to an upload file size limit with tomcat.

# Building & Testing the App
Before building or testing the app make sure you have the correct bank api URL set in both `src/service/src/test/resources/service-test.properties` and `src/dao/src/main/resources/application.default.properties` otherwise the banking service tests will fail resulting in surefire failing the build.  

Running the following command in the project root folder (src) will build the project with Maven and will also run all the tests for the Project Solution:

mvn clean install

In order to run the application it is recommended to use Netbeans with Tomcat setup as a Server. You can then load in all projects in the solution, right click on the web project and click "Build with Dependencies".

Once this is built - simply right click "Run" on the web project to run the application.

## Cargo Run

Alternatively you can use the cargo plugin to run the application. First make sure TomCat is stopped and then navigate to the web directory after `mvn clean install` and run the following line.

```
mvn cargo:run
```
then navigate to http://localhost:8080/shoppingCartApplication/

## JavaDoc Creation
To view JavaDocs, navigate to the project's source (src) folder within a command prompt and run:

`mvn javadoc:javadoc`

Visit https://maven.apache.org/plugins/maven-javadoc-plugin/index.html for more information.

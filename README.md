# capitole-inditex
Api rest to search prices.

## Technologies:  

* **Java 17**
* **Maven**
* **Spring Boot**
* **Spring Security**
* **JUnit**
* **Swagger**
* **H2 Database**

## Instrucciones:

Local:

You need to have java 1.17 and Maven.

Then run the command mvn clean install to generate the executable.

Command to run the executable: java -jar prices-x.x.x-SNAPSHOT-.jar where x.x.x is the version number.

## Usage:

Swagger: {Base_url}/swagger-ui/index.html

Database: {Base_url}/h2-console/

If you run the application locally the Base_url by default is: http://localhost:8080

This service needs to set the next environment variables

Database:

 - database_user = username.
 - database_pass = password.
 
Spring security:

 - security_user = username.
 - security_pass = password.

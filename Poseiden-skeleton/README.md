# Project: POSEIDON INC
![img_1.png](img_1.png)
This app uses Java to run and stores the data in Mysql DB.

# Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

## Technical used:

1. Framework: Spring Boot v2.6.4
2. Java 8
3. Spring Data JPA
4. Spring Security 
5. MySQL
6. OAuth2
7. Thymeleaf
8. Bootstrap v.4.3.1
9. Junit
10. Mockito

## Setup with Intellij IDE

1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Installation

Before you continue, ensure you meet the following requirements:
1.Install Java:
https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2. Install MySql:
https://www.mysql.com/downloads/

After downloading and installing it, 

3. Run: 
``` git clone https://github.com/OpenClassrooms-Student-Center/JavaDA_PROJECT7_RESTAPI.git ```

4. In file application.properties, input your username and password of MySQL: 
   ```sh
   spring.datasource.username= 
   spring.datasource.password= 
   ```
   
## Testing

The app has unit tests and integration tests written.
The existing tests need to be triggered from maven-surefire plugin while we try to generate the final executable jar file.
To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

* To run test, use command:
``` mvn surefire-report:report ```

Access file directory : target/site/jacoco and run the file index.html in your web browser to see the packages tested and their percentage.

# Project: POSEIDON INC
![img_1.png](img_1.png)
# Getting Started
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

1. Java: https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
2. MySql: https://www.mysql.com/downloads/
After downloading and installing it, 
3. Run: ``` git clone https://github.com/OpenClassrooms-Student-Center/JavaDA_PROJECT7_RESTAPI.git ```
4. In file application.properties, input your username and password of MySQL: 
   ```sh
   spring.datasource.username= 
   spring.datasource.password= 
   ```
 
## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config

### S401 Introduction to SpringBoot

Spring Boot REST service that stores users in memory during the application runtime. 

**Technologies**:<br>
* Backend Java<br>
* Spring Boot 4.1.1<br>
* Apache Maven 3.8.8<br>
* JUnit 5<br>
* Spring Web and Spring Boot DevTools<br>

*The application is configured to listen to port **9000***

**Installation and Compilation**:

1. Clone repository:<br>
   https://github.com/bmestres/S401_INTRO_SPRINGBOOT.git
2. Navigate to the project root directories:<br>
   `cd UserApi`
3. Resolve dependencies and compile the project using Maven:<br>
   `./mvn clean package`
4. Run .jar file:<br>
java -jar target/userapi-0.0.1-SNAPSHOT.jar


**Project structure:**
````
.
├── Captura de pantalla 2026-10-12 a las 17.34.54.png
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── cat
    │   │       └── itacademy
    │   │           └── s04
    │   │               └── t01
    │   │                   └── userapi
    │   │                       ├── controllers
    │   │                       │   ├── HealthController.java
    │   │                       │   └── UserController.java
    │   │                       ├── exceptions
    │   │                       │   └── ResourceNotFoundException.java
    │   │                       ├── model
    │   │                       │   └── User.java
    │   │                       ├── status
    │   │                       │   └── HealthStatus.java
    │   │                       └── UserApiApplication.java
    │   └── resources
    │       ├── application.yaml
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── cat
                └── itacademy
                    └── s04
                        └── t01
                            └── userapi
                                ├── controllers
                                │   ├── HealthControllerTest.java
                                │   └── UserControllerTest.java
                                └── UserApiApplicationTests.java
```

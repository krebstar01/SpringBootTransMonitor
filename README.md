# Springboot Microservice


# Compiling the code
You should received a shaded, executable jar from the service "we transfer".
However, should you wish to build the jar yourself, checkout the code and 
(assuming zou have Maven set up) run the following command in the main directory (testService),
```
mvn clean install -DskipTests
```

This will create an executable jar in the target directory.

You may also wish to run the project in an IDE of your choice.
For Intellij: open hte Run dialog and choose the Application class.
For Eclipse, you may need to add the path to the application.yml file as a Run Time arguement.
(resources/application.yml)


# Getting the Service Up and Running

* run the service (at the project root...)
```
java -jar target/test-service.jar
OR
mvn spring-boot:run
```

# Simple Testing
* Interact with Services via Swagger Documentation
```
localhost:8081/api/swagger-ui.html
```

* note: The following site offers an up-to-date epoch date in seconds.
(Grab the date, and add three zeros on the end)
https://www.epochconverter.com/


# Notes about the Tech Stack and General Implementation Choices
## Tech Stack
* SpringBoot
* H2 file in Memory database (generated in folder called "data", generated at root same root dir)
* Swagger 

## Notes about General Implementation Choices
* H2 in memory DB allows for persistance of of data, In File allows saving data beyond service restart.
* Swagger allows for simple blackbox tests along the way, expiditing development
* Springboot: JPA Spring Data (repositories)
* Limited Unit Tests given time restraints. Some test written to verify transformation of DateTime objects:
epoch time stamp << >> ZonedDateTime (to set UTC TimeZone) << >> LocalDateTime (for H2) 

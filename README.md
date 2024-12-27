# Hancook (Server)

This repository contains a RESTful API built with Spring Boot, Kotlin, and MySQL for managing a Korean-based restaurant system. It interacts with a MySQL database for data storage and supports OAuth2 authentication.

Check out [Hancook (Client)](https://github.com/bp82125/hancook-client)

## Tech Stack
- Backend Framework: Spring Boot
- Language: Kotlin
- Database: MySQL (via XAMPP)
- Database Migrations: Flyway
- Authentication: OAuth2 Resource Server
- Dependencies:
  - Spring Data JPA
  - Jackson (for JSON handling)
  - Spring Boot Validation
  - JUnit 5, MockK for testing
 
## Getting Started
### Prerequisites
- JDK 17 (or higher)
- MySQL (via XAMPP or another MySQL setup)
- Gradle (to build the project)
- A MySQL database configured and running
### Clone the repository
```bash
git clone https://github.com/your-username/hancook-backend.git
cd hancook-backend
```
### Set up the database
Make sure you have MySQL running, and create a new database for the application. If you are using XAMPP, you can do this via phpMyAdmin or MySQL Workbench.

For example:
```sql
CREATE DATABASE hancook;
```

### Configure the application
In your `src/main/resources/application.yaml`, make sure the database and API configuration matches your local setup.

Here is an example of what your application.yaml should look like:
```yaml
spring:
  datasource:
    url: 'jdbc:mysql://localhost:3306/hancook'
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect
    generate-ddl: true
    hibernate:
      ddl:
        auto: update
  flyway:
    baseline-on-migrate: true
    locations: 'classpath:db/migration'

api:
  endpoint:
    base-url: /api/v1
```

### Build the project
Run the following Gradle command to build the project:
```bash
./gradlew build
```
Alternatively, you can run the application as a JAR file after building:
```bash
java -jar build/libs/hancook-backend-0.0.1-SNAPSHOT.jar
```
The application will start running on http://localhost:8080.

## License
This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

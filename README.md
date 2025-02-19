## Overview

    This project is a microservices-based customer feedback system where users can provide 
    feedback on various items (e.g., restaurants, shops, etc.). The system is designed with
    scalability and extensibility in mind, allowing it to grow as new features are added.
    
    The application is built using Spring Boot and is split into two main services: 
    Feedback Service: Manages and processes user feedback for different items. This service
    allows users to submit feedback with a score and optional text comments.
    IAM (Identity and Access Management) Service: Handles authentication and authorization, 
    ensuring secure access to the system. It uses OAuth2 and JWT for token-based security.

### Reference Documentation

* [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
* [Java 11 Documentation](https://docs.oracle.com/en/java/javase/11/)
* [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/reference/)
* [PostgreSQL Documentation](https://www.postgresql.org/docs/)
* [Maven Documentation](https://maven.apache.org/guides/index.html)

### Default IAM users

  password: Jambit123@
  userName: jambittestuser1

  password: Jambit321@
  userName: jambittestuser2

  password: Jambit213@
  userName: jambittestuser3
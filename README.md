## Overview

    This project is a microservices-based customer feedback system where users can provide 
    feedback on various items (e.g., restaurants, shops, etc.). The system is designed with
    scalability and extensibility in mind, allowing it to grow as new features are added.
    
    The application is built using Spring Boot and is split into two main services: 
    Feedback Service: Manages and processes user feedback for different items. This service
    allows users to submit feedback with a score and optional text comments.
    IAM (Identity and Access Management) Service: Handles authentication and authorization, 
    ensuring secure access to the system. It uses OAuth2 and JWT for token-based security.

## Architecture Overview

    The IAM service uses a layered architectural design pattern because it primarily relies on REST-based CRUD
    operations, which is a common approach for security-related services.

    The Feedback service follows a hexagonal architectural pattern, as it requires flexibility to integrate 
    multiple external systems and extensibility to integrate several features in the future. 
    This pattern enables better separation between the core logic and external dependencies.


### Reference Documentation

* [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
* [Java 11 Documentation](https://docs.oracle.com/en/java/javase/11/)
* [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/reference/)
* [PostgreSQL Documentation](https://www.postgresql.org/docs/)
* [Maven Documentation](https://maven.apache.org/guides/index.html)
* [Docker Reference Guide](https://docs.docker.com)

### Default IAM users

    Admin
    password: Jambit123@
    userName: jambittestuser1
  
    User
    password: Jambit321@
    userName: jambittestuser2
  
    User
    password: Jambit213@
    userName: jambittestuser3

### Application build/run description

1. Go to the project base directory f.e cd {base-dir}/jambitTask
2. Make: mvn clean install (by default it will run default dev profile)
3. Set the environment variables for production:

       export DB_URL=jdbc:postgresql://common-db:5432/jambit_db
       export DB_USERNAME=<db_username>
       export DB_PASSWORD=<db_password>
       export FLYWAY_USERNAME=<flyway_username>
       export FLYWAY_PASSWORD=<flyway_password>
       export JWT_SECRET_KEY=<jwt_secret>
       export DOCKER_DB_USER=<docker_db_user>
       export DOCKER_DB_PASS=<docker_db_pass>

4. Make: -E docker-compose -f local_stack/docker-compose.yml up --build
5. After running application you can retrieve jwt token by default iam users credentials
   mentioned above and use feedback APIs.

### OAUTH API Description

* [swagger url](http://localhost:8081/swagger-ui.html)


* [retrieve jwt access token]

      curl -X POST "http://localhost:8081/iam/oauth/token" \
          -H "Accept: */*" \
          -H "Content-Type: application/json" \
          -d '{
          "password": {password},
          "userName": {userName}
      }'

### Feedback API Description

* [swagger url](http://localhost:8080/swagger-ui.html)

* [feedback-target-controller]

      curl -X POST "http://localhost:8080/api/feedback_targets/command" \
          -H "Accept: */*" \
          -H "Authorization: Bearer {token} \
          -H "Content-Type: application/json" \
          -d '{
          "name": {name},
          "targetType": {targetType}
      }'

      curl -X DELETE "http://localhost:8080/api/feedback_targets/command/{id}" \
          -H "Accept: */*" \
          -H "Authorization: Bearer {token}"

      curl -X GET "http://localhost:8080/api/feedback_targets/query/{id}" \
          -H "Accept: */*" \
          -H "Authorization: Bearer {token}"

      curl -X GET "http://localhost:8080/api/feedback_targets/query/pages?pageNumber={pageNumber}&pageSize={pageSize}" \
           -H "Accept: */*" \
           -H "Authorization: Bearer {token}"


* [feedback-controller]

      curl -X POST "http://localhost:8080/api/feedbacks/command" \
           -H "Accept: */*" \
           -H "Authorization: Bearer {token}" \
           -H "Content-Type: application/json" \
           -d '{
              "comment": {comment},
              "feedbackTargetId": {feedbackTargetId},
              "score": {score},
              "title": {title}
          }'

      curl -X DELETE "http://localhost:8080/api/feedbacks/command/{id}" \
          -H "Accept: */*" \
          -H "Authorization: Bearer {token}"

      curl -X GET "http://localhost:8080/api/feedbacks/query/{id}" \
           -H "Accept: */*" \
           -H "Authorization: Bearer {token}"

      curl -X GET "http://localhost:8080/api/feedbacks/query/{id}/by_user" \
           -H "Accept: */*" \
           -H "Authorization: Bearer {token}"

      curl -X GET "http://localhost:8080/api/feedbacks/query/{target_id}/pages?pageNumber=0&pageSize=10" \
           -H "Accept: */*" \
           -H "Authorization: Bearer {token}"


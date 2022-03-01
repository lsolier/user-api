# User-Api

## Features

 - Generate Bearer Token with JWT
 - Register user
 - Get all users
 - Get user by id
 - Update user
 - Delete user

## Tech

 - Spring security for protected endpoints,
 - JWT token in order to generate bearer token,
 - Spring session JDBC to temporarily store user information,
 - Spring data JPA integrated with hibernate,
 - Javax validations and customs annotation for validation,
 - In-memory database with H2 to persist user data,
 - Swagger2 for api documentation,
 - Lombok for reduce repetitive code,
 - JUnit5 framework for unit testing,
 - Mockito framework in order to create mocks, stubs.
 

## How to deploy

- Install dependencies and build artifact:

```sh
cd user-api
./gradlew build
```

- Launch the application:
```sh
./gradlew bootRun
```

- Application is running in 
```sh
http://localhost:8080
```

## How to see api documentation

- Go to your and enter the following URL:
```sh
http://localhost:8080/swagger-ui.html
```

## How to access to H2 database console

- Go to your browser and enter the following URL:
```sh
http://localhost:8080/h2-console/
```


## How to test

- Inside de project there is a postman collections folder, 
  import it into Postman App.
  
Steps:
 - Run generate token endpoint.
 - Copy the token and set authentication header.
    * Authentication: Bearer [JWT token] 

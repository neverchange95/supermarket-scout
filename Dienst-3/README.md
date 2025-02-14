# Overview

Dienst-3 is a hexagonal microservice that enables user registrations and authentication. The service offers a RESTful API, which allows users to communicate with the service.

# Features

1. User Registration (`Endpoint 1`): A user can be registered with the structure of the following example request body:
      ```json
    {
        "username": "TestUser",
        "password": "geheimesPasswort",
        "location": "Berlin",
        "email": "max@mustermann.de",
        "preferredCategory": "Sonstiges"
    }
    ```
      after successful registration the service is returning a valid JWT (JSON Web Token)
   
2. User Authentication (`Endpoint 2`): Allows authentication of a user with a passed username and password and returns a valid JWT (Json Web Token) if authentication is successful.

# Usage

## Endpoint 1:

`POST /api/v1/user-service/register`

#### Example Request:

```
POST http://localhost:8081/api/v1/user-service/register
```

#### Example Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0VXNlciIsImlhdCI6MTczNjQ2ODg1OCwiZXhwIjoxNzM2NDcyNDU4fQ.bsHnaCephd_hUB64VCqRvyVIIdD-QWxAoMIJE0mcgdU",
  "errorMessage": "",
  "user": {
    "username": "TestUser",
    "email": "max@mustermann.de",
    "location": "Berlin",
    "preferredCategory": "Sonstiges"
  }
}
```


## Endpoint 2:

`GET /api/v1/user-service/login`

#### Parameters:

`username`: (String, required): Username for already registered User.

`password`: (String, required): Passwort for already registered User.

#### Example Request:

```
GET http://localhost:8081/api/v1/user-service/login?username=TestUser&password=geheimesPasswort
```

#### Example Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0VXNlciIsImlhdCI6MTczNjQ2OTE4NiwiZXhwIjoxNzM2NDcyNzg2fQ.IWKBasQsk6wgs-SmrHGSPbbLRyHacLoQ0G5-i3VorC0",
  "errorMessage": "",
  "user": {
    "username": "TestUser",
    "email": "max@mustermann.de",
    "location": "Berlin",
    "preferredCategory": "Sonstiges"
  }
}
```

# VM installation and usage:

### 1. Install Java JDK 21
https://www.oracle.com/de/java/technologies/downloads/#java21

### 2. Install Maven 3.9.8
https://maven.apache.org/docs/3.9.8/release-notes.html

### 3. Change directory to userprofile_auth_app
```
cd userprofile_auth_app
```

### 4. Build the application using Maven
```
mvn clean package
```

### 5. Run application
```
java -jar target/userprofile_auth_app-0.0.1-SNAPSHOT.jar
```

### 6. Configure port forwarding in the host to make port 8081 available


# Docker installation and usage:

### 1. Build the docker image:
```
docker build -t userprofile_auth_app_image .
```

### 2. Run the image 
```
docker run -p 8081:8081 --name userprofile_auth_app userprofile_auth_app_image
```

# Task Manager API

A simple Task Management REST API built using Spring Boot.
This project allows users to register, login, and manage their tasks securely.

## Tech Stack
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- H2 Database
- Maven

## Features
- User registration and login
- Password encryption using BCrypt
- Create, fetch, and update tasks
- Task ownership validation
- Proper validation and error handling

## API Endpoints

### Authentication APIs
- POST /auth/register
- POST /auth/login

### Task APIs
- POST /tasks
- GET /tasks
- PUT /tasks/{id}/done

## API Testing
All APIs were tested using Postman with both positive and negative test cases.

## How to Run the Application

### Prerequisites
- Java 21
- Maven

### Steps
```bash
mvn spring-boot:run

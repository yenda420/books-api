# Books API (Spring Boot REST API)

This project is a Spring Boot REST API designed to manage **books and authors**. It served as my **first Spring Boot project**, a dedicated learning endeavor to grasp the fundamentals of the framework, including RESTful principles, data persistence, and testing. As an API, it operates without a graphical user interface and was primarily developed and tested using **Postman**.

## Overview

The Books API provides a set of endpoints for performing CRUD (Create, Read, Update, Delete) operations on `Author` and `Book` entities. It follows a clean architecture, separating concerns into controllers, services, repositories, and DTOs. A key focus during development was implementing a comprehensive suite of **unit and integration tests** to ensure robust functionality.

## Features

* **Author Management:**
    * Create new authors (`POST /authors`)
    * Retrieve all authors (`GET /authors`)
    * Retrieve a single author by ID (`GET /authors/{id}`)
    * Update an existing author fully (`PUT /authors/{id}`)
    * Partially update an existing author (`PATCH /authors/{id}`)
    * Delete an author by ID (`DELETE /authors/{id}`)
    * Custom repository methods for querying authors (e.g., by age).
* **Book Management:**
    * (Assumed similar CRUD operations for books, based on file structure)
    * Create new books (`POST /books`)
    * Retrieve all books (`GET /books`)
    * Retrieve a single book by ID (`GET /books/{id}`)
    * Update an existing book fully (`PUT /books/{id}`)
    * Partially update an existing book (`PATCH /books/{id}`)
    * Delete a book by ID (`DELETE /books/{id}`)
* **Data Transfer Objects (DTOs):** Utilizes DTOs for efficient and flexible data transfer between the API layer and the service layer.
* **ModelMapper Integration:** Employs ModelMapper for simplified object mapping between entities and DTOs.
* **Robust Testing:** Includes a comprehensive set of tests covering controllers, repositories, and services, ensuring high code quality and reliability.

## Technology Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Database:** **PostgreSQL** (for development/production), **H2 Database** (for testing)
* **ORM:** Spring Data JPA
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito, Spring Boot Test, MockMvc
* **Utility Libraries:** Lombok (for boilerplate code reduction), ModelMapper (for object mapping)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* **Java Development Kit (JDK) 24+**
* **Maven**
* **PostgreSQL** database server
* A tool for sending HTTP requests (e.g., **Postman, Insomnia, curl**)

### Installation and Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/yenda420/books-api.git
    cd books-api
    ```
2.  **Database Configuration:**
    * Create a PostgreSQL database for the application.
    * Update the `src/main/resources/application.properties` file with your PostgreSQL database credentials:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/[your_database_name]
        spring.datasource.username=<your_username>
        spring.datasource.password=<your_password>
        spring.jpa.hibernate.ddl-auto=update # or create, create-drop for development
        spring.jpa.show-sql=true
        ```
    * For tests, the application automatically uses an in-memory H2 database, so no separate configuration is needed for testing.

### Running the Application

To start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

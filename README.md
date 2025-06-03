# Literalura

Literalura is a Java Spring Boot console-based application for managing literary data, using PostgreSQL as the database and environment configuration with Dotenv. It fetches book and author data from the [Gutendex API](https://gutendex.com/) and allows users to interact with and manage this information through a console-based menu.

## Features

- Fetch and persist book data from the Gutendex API
- Manage authors and book information
- Console-based interactive menu
- PostgreSQL integration
- Spring Data JPA support
- External API configuration using Dotenv

## Project Structure

```
literalura
├── src
│   └── main
│       ├── java
│       │   └── developer.ezandro.literalura
│       │       ├── config
│       │       │   └── HttpClientConfig.java
│       │       ├── domain
│       │       │   ├── model
│       │       │   │   ├── Author.java
│       │       │   │   └── Book.java
│       │       │   ├── dtos
│       │       │   │   ├── ApiResponseDTO.java
│       │       │   │   ├── AuthorDTO.java
│       │       │   │   └── BookDTO.java
│       │       │   └── repository
│       │       │       ├── AuthorRepository.java
│       │       │       └── BookRepository.java
│       │       ├── exceptions
│       │       │   ├── BookNotFoundException.java
│       │       │   └── DataDeserializationException.java
│       │       ├── services
│       │       │   ├── mapper
│       │       │   │   └── DTOMapper.java
│       │       │   ├── AuthorService.java
│       │       │   ├── BookService.java
│       │       │   ├── ILiterAluraDataDeserializer.java
│       │       │   ├── LiterAluraApiClient.java
│       │       │   └── LiterAluraDataDeserializer.java
│       │       ├── ui
│       │       │   └── ConsoleMenu.java
│       │       └── LiteraluraApplication.java
│       └── resources
│           ├── application.properties
│           └── .env
├── pom.xml
└── README.md
```

## Technologies Used

- Java 24
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Jackson
- Dotenv (dotenv-java)
- Maven

## Configuration

Create a `.env` file in `src/main/resources/` with the following variables:

```env
DB_URL=jdbc:postgresql://localhost:5432/literalura
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
```

## How to Run

1. Ensure PostgreSQL is running and the database is created.
2. Configure your `.env` file.
3. Run the application:

```bash
./mvnw spring-boot:run
```

## Author

Developed by Ezandro — Part of Oracle Next Education (ONE) + Alura Back-End Java program.

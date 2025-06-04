## 📚 LiterAlura

**LiterAlura** is a console-based Java application developed with Spring Boot, designed to explore and manage literary data. It integrates with the [Gutendex API](https://gutendex.com/) to fetch information about books and authors, providing a user-friendly interactive menu in the console. The application uses PostgreSQL for persistent storage and leverages Spring Data JPA for database interaction. It also employs Dotenv for managing external API configurations.

---

## ✨ Features

- 🔍 **Search Books by Title**: Retrieves books from the Gutendex API and stores them locally if not already registered.
- 📚 **List Registered Books**: Displays all books saved in the local database.
- ✍️ **List Registered Authors**: Shows authors associated with stored books.
- 🧓 **List Authors Alive in a Given Year**: Finds authors who were alive in a specific year.
- 🌍 **List Books by Language**: Filters books based on language code (e.g., "en", "pt", "fr").
- 💾 **Data Persistence**: Uses PostgreSQL to persist book and author data.
- 🔧 **Environment Configuration**: Manages environment variables using Dotenv.
- 🔄 **DTO Mapping**: Maps domain models to DTOs using dedicated mappers.
- 🧪 **Validation and Error Handling**: Includes input validation and custom exception handling for better UX.

---

## 📂 Project Structure

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
│           └── application.properties
├── .env        
├── pom.xml
└── README.md
```
---

## 🛠️ Technologies Used

- Java 24
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Jackson (for JSON parsing)
- Dotenv (via [dotenv-java](https://github.com/cdimascio/dotenv-java))
- Maven
- IntelliJ IDEA or Eclipse

---

## 🧭 Menu Overview

The application runs in the terminal and provides an interactive menu for users to explore its features.  
Option 1 connects to the [Gutendex API](https://gutendex.com/); other options operate on local database data.

```
                    ╔══════════════════════════════╗
                    ║          LiterAlura          ║
                    ╚══════════════════════════════╝

                     1 - Search book by title  
                     2 - List registered books  
                     3 - List registered authors  
                     4 - List authors alive in a given year  
                     5 - List books by language  
                     0 - Exit
```

---

## ⚙️ Setup & Installation

1. Clone the repository.  
2. Configure your PostgreSQL database and environment variables in a `.env` file.

Create a `.env` file in the **project root directory** with the following content:

```
DB_URL=jdbc:postgresql://localhost:5432/literalura
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
```

> **Note:** Make sure you have [PostgreSQL](https://www.postgresql.org/download/) installed and running, and that you have Java 24 installed on your system before running the application.

3. Use Maven to build the project.  
4. Run the application using your preferred IDE or the terminal.

---

## 🚀 Running the Application

1. Start your PostgreSQL database.  
2. Run the application using your IDE or via command line:  
   ```bash
   ./mvnw spring-boot:run
   ```
3. Interact with the console-based menu to fetch and explore book data.

> **Tip:** The application runs entirely in the terminal with an interactive menu.

---

## 👤 Author

Developed by **Ezandro** — Part of the Oracle Next Education (ONE) + Alura Back-End Java program.

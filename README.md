# 📚 Literalura

**Literalura** is a console-based Java application that allows users to search for books using the [Gutendex API](https://gutendex.com/), persist the retrieved data into a PostgreSQL database, and perform various queries on the saved data.

This project was developed as part of the **Java Back-End** track of the [Oracle Next Education](https://www.oracle.com/br/education/oracle-next-education/) program in partnership with [Alura](https://www.alura.com.br/).

---

## 🚀 Features

- 🔍 Search books from the Gutendex API
- 💾 Persist books and authors using Spring Data JPA
- 📖 List all saved books
- 🌎 Query authors by nationality
- 📆 Search books by publication year
- 🧭 Interactive text-based menu

---

## 🛠 Technologies Used

- Java 24
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Dotenv Java (`dotenv-java`)
- Jackson Databind
- Maven

---

## 📁 Project Structure

```
literalura/
├── src/main/java/developer/ezandro/literalura/
│   ├── ui/                # Console menu and user input
│   ├── domain/            # JPA entities (Book, Author)
│   ├── repositories/      # JPA repositories
│   └── LiteraluraApplication.java
├── resources/
│   ├── application.properties
│   └── .env               # Environment variables
├── pom.xml
```

---

## ⚙️ Setup

### 1. Prerequisites

- Java 21+ (Java 24 recommended)
- PostgreSQL
- Maven

### 2. Database Setup

Create a database named `literalura`:

```sql
CREATE DATABASE literalura;
```

### 3. Environment Variables

Create a `.env` file in the project root with the following content:

```env
DB_URL=jdbc:postgresql://localhost/literalura
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

> ⚠️ Do not commit your `.env` file to version control.

---

## ▶️ Running the Project

Use the following command to run the application:

```bash
mvn spring-boot:run
```

The system will start and display the interactive console menu.

---

## 🤝 Contribution

This project was built for educational purposes and is part of the **Java Back-End** specialization track from **ONE | Oracle Next Education** in collaboration with **Alura**.

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
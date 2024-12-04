# 📚 WiseDroid-Server

WiseDroid-Server is an AI-powered platform that processes content to create quizzes, flashcards, and personalized learning tools, optimizing study sessions and improving knowledge retention.

---

## 🚀 Features

- **Intelligent Conversion**: Transforms content into quizzes and personalized flashcards.
- **AI Integration**: Leverages AI models to adapt learning to user needs.
- **REST API**: Provides an interactive API for web and mobile app integration.
- **Swagger Documentation**: Includes auto-generated documentation to explore and test endpoints.

---

## 🛠️ Technologies

- **Language**: Java
- **Framework**: Spring Boot (version 3.4.0)
- **Dependency Management**: Maven
- **Database**: (Configurable, check project properties)
- **Security**: Spring Security with JWT
- **API Documentation**: Springdoc OpenAPI (Swagger UI)

---

## 📂 Project Structure

- **`src/main/java`**: Main server source code.
- **`src/main/resources`**: Project configurations, such as `application.properties`.
- **`pom.xml`**: Maven dependencies and project configuration.
- **`README.md`**: General project information.
- **`.mvn/`**: Maven Wrapper files for environment consistency.

---

## ⚙️ Setup and Execution

### **1. Prerequisites**

- **Java 17** or higher.
- **Maven 3.8** or higher (or use the included Maven Wrapper: `mvnw`).

### **2. Configuration**

- Configure the required properties in `src/main/resources/application.properties` or `application.yml`:
  - Database credentials.
  - JWT configuration.
  - Other necessary deployment parameters.

### **3. Run the Application**

From the project root directory, execute:

```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or standard Maven
mvn spring-boot:run
```

The server will be available at: `http://localhost:8080`

---

## 🔗 API Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

---

## 🧪 Testing

To run tests, use:

```bash
./mvnw test
```

---

## 🤝 Contributions

Contributions are welcome. To contribute:

1. Fork the repository.
2. Create a new branch for your changes:
   ```bash
   git checkout -b feature/new-feature
   ```
3. Submit a pull request describing your changes.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 🛡️ Author

**WiseDroid-Server** is maintained by the **TeamDroid** team.

---

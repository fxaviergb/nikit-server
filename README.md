# 📚 nikit-server

NikIT (Now I Know IT) Server is an AI-powered platform that processes content to create quizzes, flashcards, and personalized learning tools, optimizing study sessions and improving knowledge retention.

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
- **`cicd/`**: Docker and deployment-related configuration (Dockerfile, docker-compose, scripts).

---

## ⚙️ Setup and Execution

### **1. Prerequisites**

- **Java 17** or higher.
- **Maven 3.8** or higher (or use the included Maven Wrapper: `mvnw`).
- **Docker** and **Docker Compose** installed and running.

---

### **2. Run with Maven (Local)**

```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or standard Maven
mvn spring-boot:run
```

The server will be available at: `http://localhost:8080`

---

### **3. Run with Docker**

All Docker-related configuration is inside the `cicd/` folder.

#### 🧱 Build and Run

From the root of the project:

```bash
./cicd/run.sh
```

This script will:
- Compile the project with Maven
- Build the Docker image
- Start the application and MongoDB containers using Docker Compose

---

### 👀 View Running Containers

To list all running services:

```bash
docker ps
```

Or, using Docker Compose (inside `cicd/`):

```bash
docker-compose ps
```

---

### 📜 View Logs

To see logs of the `nikit-server` container in real-time:

```bash
docker logs -f nikit-server
```

Or with Compose:

```bash
docker-compose logs -f nikit-server
```

To see logs of **all services**:

```bash
docker-compose logs -f
```

---

## 🔗 API Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

---

## 🧪 Testing

To run tests locally:

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

**NikIT-Server** is maintained by the **TeamDroid** team.

---

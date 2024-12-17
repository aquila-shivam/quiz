# Quiz App - Spring Boot REST API

This project is a **Quiz Application** implemented in **Spring Boot**. It allows users to:

1. Start a new quiz session.
2. Retrieve a random multiple-choice question from the database.
3. Submit answers to questions.
4. Get statistics on the total number of questions answered and details on correct/incorrect submissions.

---

## Table of Contents
1. [Technologies Used](#technologies-used)
2. [Getting Started](#getting-started)
3. [Database Setup](#database-setup)
4. [Running the Application](#running-the-application)
5. [API Endpoints](#api-endpoints)
6. [Inserting Questions into Database](#inserting-questions-into-database)
7. [Sample Payloads](#sample-payloads)
8. [Troubleshooting](#troubleshooting)

---

## Technologies Used
- **Java 17**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **H2** (or replaceable with MySQL/PostgreSQL)
- **Maven**
- **Hibernate ORM**
- **Embedded Tomcat Server**

---

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo-url/quiz-app.git
   cd quiz-app
   ```
2. **Ensure Java and Maven are installed.**
   - Java: Version 17+
   - Maven: Version 3.x

3. **Database Configuration:**
   - The project uses an in-memory H2 database by default. To change to another database (like MySQL/PostgreSQL), update `application.properties`.

4. **Build the Project:**
   ```bash
   mvn clean install
   ```

---

## Database Setup

By default, the application uses an **H2 in-memory database**. To seed sample questions:

### Option 1: Using `data.sql`
Add questions to `src/main/resources/data.sql`:
```sql
INSERT INTO question (text, options, correct_answer) VALUES
('What is the capital of France?', 'Paris,London,Berlin,Madrid', 'Paris'),
('What is 2 + 2?', '3,4,5,6', '4'),
('Who wrote "Hamlet"?', 'Shakespeare,Chaucer,Dickens,Austen', 'Shakespeare');
```

### Option 2: Using API to Insert Questions
Use the **`POST /api/questions/add`** endpoint to add new questions dynamically (refer to [API Endpoints](#api-endpoints)).

### Switching to Another Database
To use MySQL or PostgreSQL, configure `src/main/resources/application.properties`:
```properties
# Example for MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/quiz_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## Running the Application

1. Start the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
2. Application will run on **`http://localhost:8080`**.

---

## API Endpoints

| Endpoint                   | HTTP Method | Description                                     |
|----------------------------|-------------|------------------------------------------------|
| `/api/quiz/start`          | `POST`      | Start a new quiz session                       |
| `/api/quiz/{sessionId}/question` | `GET`       | Get a random question for a session            |
| `/api/quiz/{sessionId}/answer`   | `POST`      | Submit an answer for a question                |
| `/api/quiz/{sessionId}/stats`    | `GET`       | Get quiz session statistics                    |
| `/api/questions/add`       | `POST`      | Add a new question to the database             |

---

## Inserting Questions into Database

To insert questions into the database, there are **three main options**:

### 1. Using the API
Make a `POST` request to `http://localhost:8080/api/questions/add` with JSON:
```json
{
  "text": "What is the capital of France?",
  "options": "Paris,London,Berlin,Madrid",
  "correctAnswer": "Paris"
}
```

### 2. Using SQL Script (`data.sql`)
Place your insert queries in `src/main/resources/data.sql`:
```sql
INSERT INTO question (text, options, correct_answer) VALUES
('What is the capital of France?', 'Paris,London,Berlin,Madrid', 'Paris');
```

### 3. Using Java Code
Seed questions programmatically in `QuizApplication`:
```java
@Bean
public CommandLineRunner seedQuestions(QuestionRepository questionRepository) {
    return args -> {
        questionRepository.save(new Question("What is the capital of France?", "Paris,London,Berlin,Madrid", "Paris"));
    };
}
```

---

## Sample Payloads

### 1. Start a New Quiz
**Endpoint:** `POST /api/quiz/start`
**Response:**
```json
{
  "id": 1,
  "answers": []
}
```

### 2. Get a Random Question
**Endpoint:** `GET /api/quiz/{sessionId}/question`
**Response:**
```json
{
  "id": 1,
  "text": "What is the capital of France?",
  "options": "Paris,London,Berlin,Madrid",
  "correctAnswer": "Paris"
}
```

### 3. Submit an Answer
**Endpoint:** `POST /api/quiz/{sessionId}/answer`
**Request:**
```json
{
  "questionId": 1,
  "answer": "Paris"
}
```
**Response:**
```json
"Correct answer!"
```

### 4. Get Quiz Stats
**Endpoint:** `GET /api/quiz/{sessionId}/stats`
**Response:**
```json
{
  "totalQuestions": 3,
  "correctAnswers": 2,
  "incorrectAnswers": 1
}
```

---

## Troubleshooting

### Error: `Request method 'GET' is not supported`
- Ensure you're using the correct HTTP method. For example:
  - `POST` for `/api/quiz/start`
  - `POST` for `/api/quiz/{sessionId}/answer`

### Error: `Table not found`
- Verify the database schema was created properly. Use `spring.jpa.hibernate.ddl-auto=update` in `application.properties` to auto-create tables.

### Error: `No default constructor for entity`
- Ensure all entity classes have a **no-argument constructor**.

---

## Contact
If you encounter any issues or have suggestions, feel free to contact me.

---

Happy Coding! 🎉

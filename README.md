# Quiz-App-public

This is a Spring Boot project that serves as a learning exercise for implementing REST APIs in the Spring Boot framework. The application is a quizzing platform that allows a main user to create and manage quizzes, candidates, and tests.

## Status

The project is still under development.

## Features

### Implemented Features

- **Quiz Management**: The user can create, edit, and delete quizzes.
  - A quiz consists of a title, description, and a list of questions.
- **Candidate Management**: The user can create simple candidate profiles.
- **Test Management**: The user can create a Test, which is an association between a Candidate and a Quiz.
- **Test Access**: The user can send an access link to a candidate via email to allow them to take the test.
  - Candidates do not need authentication to take a test.

### Planned Features (Not Yet Implemented)

- **Resume Upload & Assignment**: The user will be able to upload and assign resumes to candidates.
- **Quiz Import/Export**: The platform will support importing and exporting quizzes in CSV format.
- **Profile Management**: The user will be able to manage their profile.

## API-Only Application

This application does not generate a front-end interface. It only accepts and returns JSON data, making it suitable for integration with external front-end applications or clients.

## Running the Project

### Prerequisites

- Java 17 or later
- Gradle
- MySQL database

### Setup & Run

1. Clone the repository:
   ```sh
   git clone https://github.com/RGProgrammer/Quiz-App-public.git
   cd Quiz-App-public
   ```
2. Configure the database:
   - Ensure MySQL is running.
   - Create a database and update the `application.properties` with the correct database credentials.
3. Build and run the application:
   ```sh
   ./gradlew bootRun
   ```
4. The application will be available at `http://localhost:8080`


## Contribution

Since this project was created for learning purposes, contributions are not currently expected, but feedback and suggestions are always welcome!





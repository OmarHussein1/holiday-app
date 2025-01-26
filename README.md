# HolidayApp

## Overview
HolidayApp is a Java-based application designed to interact with holiday data. It allows users to:
- Retrieve the last three holidays celebrated in a specific country.
- Count holidays in a given year that do not fall on weekends.
- Find common holidays between two countries for a specific year.

The application has 2 modes of operation:
1. **Interactive Mode:** The user can interact with the application through the command line.
2. **REST API Mode:** The user can interact with the application through a REST API.

---

## Key Features
1. Retrieve the last three celebrated holidays for a specific country.
2. Count holidays in a given year that do not occur on weekends.
3. Compare and find common holidays between two countries.

---

## Technology Choices
- **Java:** Chosen for its robustness, scalability, and extensive ecosystem, making it ideal for enterprise applications.
- **Spring Boot:** Simplifies application configuration and provides dependency injection, making development faster and cleaner.
- **Gradle:** Selected as the build tool for its flexibility, faster builds, and modern dependency management.
- **OpenAPI:** Used to define the API specification, making it easier to understand and interact with the application.
- **OpenAPI Generator:** Generates client libraries, server stubs, API documentation, and other tools based on the OpenAPI specification.

---

## Getting Started

### Prerequisites
Ensure the following are installed on your system:
- Java 21 or later
- Gradle 7.x or later (optional if using the Gradle Wrapper)

### Clone the Repository
```bash
git clone https://github.com/OmarHussein1/holidayapp.git
cd holidayapp
```
### Run the Application in REST API Mode
```bash
./gradlew bootRun
```
### Run the Application in Interactive Mode
```bash
./gradlew bootRun --args='cli'
```

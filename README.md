# 📚 Library Intelligence System

A full-stack library management and reader profile classification system designed for the individual hackathon challenge.

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/)
[![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)

---

## 🏗 System Architecture & Data Flow
[cite_start]The system implements a modular architecture to ensure clear separation between the data layer and the intelligence logic[cite: 52, 73].
Below is the technical data matrix mapping out how the system components communicate seamlessly:

```text
[ Front-End UI ] 
      |
      | ( HTTP POST /api/borrowings )
      v
[ REST Controller ] 
(BookController.java)
      |
      | (Evaluates Borrowing Data)
      v
[ Intelligence Engine ]
(Decision Tree Classifier)
      |
      | ( Return Reader Persona )
      v
[ SQLite Database ] 
(LibraryRepository / JPA)
      |
      | ( Renders JSON Table Rows )
      v
[ UI State Sync ]


### Data Pipeline:
* [cite_start]**Frontend Layer**: Built with HTML/JS to provide an interactive interface for catalog viewing and borrowing actions[cite: 73].
* [cite_start]**API Layer**: Java Spring Boot backend handles RESTful requests and coordinates business logic[cite: 73].
* [cite_start]**Database Layer**: SQLite stores library `books` and `borrow_records`, using relational joins to aggregate borrowing statistics[cite: 73].
* [cite_start]**DM/ML Module**: Implements a decision tree algorithm that analyzes borrowing patterns—such as total volume and genre concentration—to classify readers into specific personas[cite: 56, 127].

---

## 🎯 Key Features
* [cite_start]**Library Management**: Full CRUD support for book cataloging[cite: 153].
* [cite_start]**Borrowing System**: Real-time tracking of borrowing history linked to specific users[cite: 133].
* [cite_start]**Intelligent Classification**: Dynamic decision tree engine that categorizes users and offers personalized insights[cite: 127].

---

## 🚀 Setup & Execution
1. [cite_start]**Initialize Database**: Ensure the `library.db` file is present in the root directory[cite: 104].
2. **Build and Run**:
   ```bash
   mvn clean package
   java -jar target/library-vibe-system-0.0.1-SNAPSHOT.jar

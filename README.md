# 📚 Library Intelligence System

A full-stack library management and reader profile classification system designed for the individual hackathon challenge.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![DM/ML](https://img.shields.io/badge/DM/ML-007ACC?style=for-the-badge&logo=python&logoColor=white)

---

## Data Flow

⚙️ Data Pipeline
Frontend Layer: Built with HTML/JS to provide an interactive interface for catalog viewing and borrowing actions.
API Layer: Java Spring Boot backend handles RESTful requests and coordinates business logic.
Database Layer: SQLite stores library books and borrow records, using relational joins to aggregate borrowing statistics.
DM/ML Module: Implements a decision tree algorithm that analyzes borrowing patterns—such as total volume and genre concentration—to classify readers into specific personas like Fanatic, Scholar, Specialist, or Casual.

🎯 Key Features
Library Management: Full CRUD support for book cataloging.
Borrowing System: Real-time tracking of borrowing history linked to specific users.
Intelligent Classification: Dynamic decision tree engine that categorizes users and offers personalized insights based on data mining patterns.

🚀 Setup & Execution
Initialize Database: Ensure the library.db file is present in the root directory.


## 🏗 System Architecture 
The system implements a modular architecture to ensure clear separation between the data layer and the intelligence logic. Below is the technical data matrix mapping out how the system components communicate seamlessly:
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



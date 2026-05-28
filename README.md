# Consumer and Retry Service

## 📌 Overview
Consumer and Retry Service is a Spring Boot application that consumes event messages from an Apache Kafka topic, processes them, and forwards them to a downstream receiver service. In case of failures, messages are stored and retried using a scheduler mechanism.

---

## 🚀 Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- Lombok
- JUnit 5 & Mockito

---

## 📂 Project Structure
```
src/main/java/com/assignment/consumer/
│
├── service
├── scheduler
├── repository
├── model
├── util
└── ConsumerServiceApplication.java
```

---

## ⚙️ Setup & Installation

### 1. Clone the repository
```
git clone https://github.com/Panchalic3/assignment-consumerAndRetrySrvice
cd assignment-consumerAndRetrySrvice
```
### 2. Build the project
```
mvn clean install
```
### 3. Run the application
```
mvn spring-boot:run
```
---

## 🔄 Processing Flow

### 📥 Kafka Consumption
- Listens to Kafka topic: `event-topic`
- Consumes JSON messages
- Converts message  `EventPayload`
- Sends data to Receiver Service via REST API

---

### ❌ Failure Handling
If processing fails:
- Message is stored in `retry_events` table
- Status set to `FAILED`
- Retry count initialized

---

### 🔁 Retry Mechanism
- Scheduler runs every **10 seconds**
- Fetches failed events from DB
- Retries sending to receiver service
- Updates:
    - ✅ Status → `SUCCESS` (on success)
    - 🔁 Retry count increment (on failure)

---

## 🗄️ Database

### RetryEvent Table Fields:
- `payload` → Original message
- `retryCount` → Number of attempts
- `status` → FAILED / SUCCESS

---

## 🧪 Testing

### Run all tests:
```
mvn test
```

### ✔ Coverage Includes:
- Kafka Consumer Service
- Scheduler Retry Service
- Receiver API Client

---

## ⚠️ Important Notes

- Ensure Kafka is running (`localhost:9092`)
- Ensure PostgreSQL is configured and running
- Receiver service should be available for successful processing

---

## 💡 Features
- Kafka message consumption
- REST API integration
- Retry mechanism for failures
- Scheduled background processing
- High test coverage

---

## 👩‍💻 Author
**Panchali**

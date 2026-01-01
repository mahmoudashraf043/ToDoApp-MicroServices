# ✅ To-Do Microservices App (Spring Boot + Docker)

A **microservices-based To-Do application** built with **Spring Boot, Spring Cloud, Eureka, and API Gateway**.
It supports user authentication, task management, notifications, and can be run independently or together using **Docker Compose**.

---

## 📦 Features

### 👤 User Service

* Register new account
* Login (JWT optional)
* Update profile
* Change password
* Delete account
* Get currently logged-in user

### ✅ Task Service

* Create / update / delete tasks
* Get task by ID
* Get all tasks
* Get completed tasks
* Get uncompleted tasks

### 🔔 Notification Service

* Daily scheduled check using `@Scheduled`
* Generates notification if a task is due tomorrow
* API to get notifications

### 🌐 API Gateway

* Routes requests to the correct service
* Works with Eureka service discovery

---

## 🛠 Tech Stack

* Java 17+
* Spring Boot 3.x
* Spring Cloud (Eureka, Gateway)
* JPA / Hibernate
* MySQL 8
* Docker & Docker Compose
* Postman (for API testing)

---

## 🏗 Project Structure

```
To-Do_MicroService/
├── docker-compose.yml        # Master Docker Compose for all services
├── api-gateway/              # API Gateway
├── auth-service/             # Authentication service
├── user-service/             # User management service
├── task-service/             # Task management service
├── notification-service/     # Notification service
└── eureka-server/            # Service discovery (Eureka)
```

---

## 🚀 Getting Started

### 1️⃣ Clone the Repo

```bash
git clone https://github.com/YourUsername/to-do-microservice.git
cd To-Do_MicroService
```

### 2️⃣ Build All Services

```bash
cd auth-service
mvn clean package -DskipTests

cd ../user-service
mvn clean package -DskipTests

cd ../task-service
mvn clean package -DskipTests

cd ../notification-service
mvn clean package -DskipTests

cd ../api-gateway
mvn clean package -DskipTests

cd ../eureka-server
mvn clean package -DskipTests
```

### 3️⃣ Start Everything with Docker Compose

```bash
docker-compose up --build
```

* Or in detached mode:

```bash
docker-compose up -d --build
```

---

### 4️⃣ Verify Services

* **Eureka Dashboard:** [http://localhost:8761](http://localhost:8761) — All services registered
* **API Gateway Routes:**

```text
http://localhost:8080/auth-service/auth/login
http://localhost:8080/user-service/users
http://localhost:8080/task-service/tasks
http://localhost:8080/notification-service/notifications
```

### 5️⃣ Access MySQL Databases

```bash
docker exec -it auth-mysql mysql -uroot -proot
docker exec -it user-mysql mysql -uroot -proot
docker exec -it task-mysql mysql -uroot -proot
docker exec -it notification-mysql mysql -uroot -proot
```



## 🖼 Architecture Diagram

![To-Do Microservices Architecture](./To-Do_Microservice_Diagram.png)

* **API Gateway** → Routes requests
* **Eureka Server** → Service discovery
* Each service → Own MySQL database
* Notifications handled by Notification Service

---

## 📝 Notes

* Each service has **its own database**.
* Gateway routes requests using **Eureka service discovery**.
* You can run **individual services** for development if needed.

---

## 👨‍💻 Author

**Mahmoud (Spark)**

e

## 📸 Application Preview
![Dashboard Preview](resources/Jumbo_6.jpg)

## Enterprise Retail Dashboard

> A modernized, type-safe enterprise retail dashboard engineered with Angular, Spring Boot microservices, Spring Cloud, and Apache Kafka to handle real-time product ingestion, customer order processing, active inventory sync, and live audit logging.

[![Azure Cloud](https://img.shields.io/badge/Cloud-Microsoft%20Azure-0089D6?logo=microsoftazure&logoColor=white)](#)
[![Kubernetes](https://img.shields.io/badge/Orchestration-Kubernetes-326CE5?logo=kubernetes&logoColor=white)](#)
[![GitHub Actions](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)](#)
[![Apache Kafka](https://img.shields.io/badge/Event%20Streaming-Apache%20Kafka-231F20?logo=apachekafka&logoColor=white)](#)
[![Backend Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?logo=springboot&logoColor=white)](#)
[![Frontend Angular](https://img.shields.io/badge/Frontend-Angular-DD0031?logo=angular&logoColor=white)](#)

---

## 🌐 Live Application

* **Deployment Status:** CI/CD pipeline integrated via GitHub Actions, target-deployed to Microsoft Azure Kubernetes Service (AKS) (Staging and local verification verified via containerized pods).

---

## 📐 System Architecture & Workflow

The architecture is built around a modernized **Microservices Architecture**. The Angular front-end issues strongly-typed HTTP REST requests through a Spring Cloud API Gateway (`8080`), registered with Netflix Eureka Discovery Server (`8761`). The Gateway routes requests to Product Service (`8081`) or Order Service (`8082`). Adding new products or placing orders emits asynchronous Kafka messaging events to port `9092`. The Inventory Service (`8083`) updates active stock counts, while the Notification Service (`8084`) updates the real-time processing log. Persistent entities are managed via Spring Data JPA into MySQL database instances.

```
                                         +----------------------------------+
                                         |  Eureka Discovery Server (8761)  |
                                         +----------------------------------+
                                                          ^
                                                          | Service Registration
                                                          v
+------------------+     HTTP / REST     +----------------------------------+
|                  | ------------------> |   Spring Cloud API Gateway       | ---> Product Service (8081)
|  Angular Client  |                     |   (Port 8080)                    | ---> Order Service (8082)
| (Retail Dashboard) <------------------ +----------------------------------+
+------------------+   Real-Time Data                     |
    (Port 4200)                                           | Events Stream
                                                          v
                                                 +------------------+
                                                 |   Apache Kafka   |
                                                 |   Broker Topic   |
                                                 |   (Port 9092)    |
                                                 +--------+---------+
                                                          |
                                      +-------------------+-------------------+
                                      |                                       |
                                      v                                       v
                         +-------------------------+             +-------------------------+
                         |    Inventory Service    |             |   Notification Service  |
                         |       (Port 8083)       |             |       (Port 8084)       |
                         +------------+------------+             +------------+------------+
                                      |                                       |
                                      +-------------------+-------------------+
                                                          | JPA Persistence
                                                          v
                                                 +------------------+
                                                 |  MySQL Database  |
                                                 |  (Port 3306)     |
                                                 +------------------+
```

---

## ✨ Key Features & Capabilities

* **Product Catalog Management:** Intuitive interface enabling users to add new retail products using Name and unique SKU identifiers.
* **Dynamic Customer Order Checkout:** Streamlined order entry allowing users to select target SKUs, set item quantities, and submit customer orders.
* **Active Inventory Dashboard:** Real-time publishing and display of available stock units updated immediately upon product creation or checkout events.
* **Live Order Processing Audit Log:** Asynchronous streaming audit log displaying live status updates for every incoming order.
* **End-to-End Type Safety:** Strongly-typed Angular TypeScript interfaces paired with Java DTOs to enforce static contract safety across all service boundaries.

---

## 🛠️ Tech Stack & Dependencies

* **Back-End:** Java JRE, Spring Boot, Spring Data JPA, Spring Cloud (Gateway, Eureka Discovery)
* **Front-End:** Angular, TypeScript, RxJS, HTML5/SCSS
* **Database & Persistence:** MySQL, JPA / Hibernate
* **Streaming & CI/CD:** Apache Kafka, GitHub Actions
* **Cloud & Infrastructure:** Docker Desktop, Kubernetes (k8s), Microsoft Azure (AKS)

---

## 🔌 Port Configuration & Environment Setup

| Component / Service | Default Port | Protocol / Description |
| :--- | :--- | :--- |
| **Front-End Portal** | `4200` | Angular Development Server |
| **API Gateway** | `8080` | Spring Cloud Gateway Routing |
| **Discovery Server** | `8761` | Eureka Discovery Registry |
| **Product Service** | `8081` | Microservice Endpoint |
| **Order Service** | `8082` | Microservice Endpoint |
| **Inventory Service** | `8083` | Microservice Endpoint |
| **Notification Service** | `8084` | Microservice Endpoint |
| **Kafka Broker** | `9092` | Apache Kafka Messaging Service |
| **MySQL Database** | `3306` | Relational Data Store |

---

## 💻 Local Getting Started

### Prerequisites
* Java Development Kit (JDK 11+)
* Node.js (v18+) & Angular CLI (`npm i -g @angular/cli`)
* Apache Kafka & Zookeeper / KRaft
* MySQL Server 8.0+
* Docker Desktop

---

### 1. Database & Kafka Setup

Create the target database in MySQL:

```sql
CREATE DATABASE enterprise_retail_db;
```

Configure application parameters in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/enterprise_retail_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.kafka.bootstrap-servers=localhost:9092
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

### 2. Start Kafka Cluster (Local / Docker)

Launch the Kafka broker on port `9092`:

```bash
# Start Zookeeper and Kafka Broker via Docker
docker run -d --name zookeeper -p 2181:2181 zookeeper
docker run -d --name kafka -p 9092:9092 --link zookeeper:zookeeper -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka
```

---

### 3. Build and Run Microservices

Launch services in sequence:

```bash
# 1. Discovery Server (Port 8761)
cd discovery-server
./mvnw spring-boot:run

# 2. API Gateway (Port 8080)
cd ../api-gateway
./mvnw spring-boot:run

# 3. Product & Order Backend Services (Ports 8081, 8082)
cd ../product-service
./mvnw spring-boot:run

# 4. Inventory & Notification Services (Ports 8083, 8084)
cd ../inventory-service
./mvnw spring-boot:run
```

---

### 4. Build and Run Frontend

Launch the Angular web client:

```bash
cd ../frontend-angular

# Install dependencies
npm install

# Start Angular client
ng serve --port 4200
```

Access retail dashboard UI at `http://localhost:4200`.

---

## 🚀 Cloud & CI/CD Deployment

1. **GitHub Actions Automation:** Workflow triggers build pipelines, executes unit/integration tests, and generates container images.
2. **Container Registry & AKS:** Docker images are pushed to Azure Container Registry (ACR) and deployed onto Microsoft Azure Kubernetes Service (AKS) clusters.
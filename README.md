# SwiftCart – Scalable E-Commerce Infrastructure

SwiftCart is a full-stack e-commerce platform built with a modern Android frontend and scalable backend architecture. It supports real-time product browsing, secure user authentication, and high-throughput order processing.

## Tech Stack

### Frontend (Android)
- **Language:** Kotlin
- **Framework:** Jetpack Compose
- **Features:** MVVM architecture, LiveData, Navigation, Retrofit, Coroutines, JWT-based auth

### Backend
- **Language:** Kotlin
- **Framework:** Spring Boot (Microservices)
- **Storage:** PostgreSQL (RDS), AWS S3
- **Auth:** OAuth2, JWT
- **Infra:** Docker, AWS ECS, JMeter

---

## Features

- User registration, login, and secure JWT-based session management
- Product listing, search, and category filtering
- Cart and order management with real-time stock checks
- Image upload and retrieval via AWS S3
- Optimized database with indexing and connection pooling
- Load tested to handle >5000 concurrent sessions with P95 latency <180ms

---

## Setup Instructions

### Backend
1. Clone the backend repo:
   ```bash
   git clone https://github.com/shaurya-afk/swiftcart-backend.git
   cd swiftcart-backend
    ```

2. Configure `.env` or `application.yml` with DB credentials, S3 keys, JWT secret.
3. Build and run:

   ```bash
   ./mvnw clean install
   docker-compose up
   ```

### Frontend (Android)

1. Clone the frontend repo:

   ```bash
   git clone https://github.com/shaurya-afk/swiftcart-frontend.git
   cd swiftcart-frontend
   ```
2. Open in Android Studio.
3. Set base API URL and JWT keys in a local `secrets.properties`.
4. Run on emulator or device.

---

## Performance

* Load tested using JMeter: sustained 5K RPS
* Backend latency: P95 <180ms
* Asynchronous processing with stateless services deployed on AWS ECS

---

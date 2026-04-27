# 🛒 CartForge API

> **Production-grade e-commerce backend** built with Spring Boot 3 — featuring JWT-secured REST APIs, layered architecture, JPA/Hibernate ORM, and full shopping lifecycle management.

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue?style=flat-square&logo=springsecurity)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![REST API](https://img.shields.io/badge/API-RESTful-ff6b35?style=flat-square)]()

---

## 📌 What is CartForge API?

CartForge API is a fully functional e-commerce backend designed to manage key operations such as user authentication, product management, cart workflows, and order processing. The application is structured using a layered architecture (Controller → Service → Repository), ensuring clear separation of concerns, scalability, and maintainability.

The project emphasizes strong backend design principles, including **stateless authentication using JWT**, well-defined entity relationships, and consistent data flow across application layers. It reflects commonly adopted patterns in production-grade backend systems, focusing on reliability, modularity, and clean architecture.

---

## 🏗️ Architecture Overview

```
Client (Postman / Frontend)
         │
         ▼
  ┌──────────────────┐
  │  REST Controller │  ← Receives HTTP requests, validates input, returns responses
  └────────┬─────────┘
           │
  ┌────────▼─────────┐
  │  Service Layer   │  ← Business logic, validations, orchestration
  └────────┬─────────┘
           │
  ┌────────▼─────────┐
  │   Repository     │  ← Database access via Spring Data JPA
  └────────┬─────────┘
           │
  ┌────────▼─────────┐
  │  MySQL Database  │  ← Persistent storage via Hibernate ORM
  └──────────────────┘
```

**Spring Security + JWT** sits as a cross-cutting concern — intercepting every request before it reaches the Controller layer.

---

## ✨ Features

| Module | Capabilities |
|---|---|
| 🔐 **Auth** | User registration, login, JWT token generation & validation |
| 👤 **Users** | Role-based access (ADMIN / USER), profile management |
| 📦 **Products** | CRUD operations, category filtering, image management |
| 🗂️ **Categories** | Hierarchical product categorization |
| 🛒 **Cart** | Add/remove items, update quantities, real-time total calculation |
| 📋 **Orders** | Cart-to-order conversion, order history, status tracking |
| 🖼️ **Images** | Product image upload and retrieval |

---

## 🔐 Security Design

Authentication is **stateless** using JSON Web Tokens (JWT):

```
1. POST /api/v1/auth/login  →  Server validates credentials  →  Returns JWT
2. Client stores JWT and sends it in every subsequent request:
   Authorization: Bearer <token>
3. JwtAuthFilter intercepts  →  validates signature  →  grants access
```

- Passwords stored as **BCrypt hashes** (never plaintext)
- Role-based endpoint protection (`ROLE_ADMIN` vs `ROLE_USER`)
- Token expiry + signature verification on every request

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/v1/auth/register` | Register new user | Public |
| POST | `/api/v1/auth/login` | Login and receive JWT | Public |

### Products
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/v1/products/all` | Fetch all products | Public |
| GET | `/api/v1/products/{id}` | Get product by ID | Public |
| GET | `/api/v1/products/by/category` | Filter by category | Public |
| POST | `/api/v1/products/add` | Add product | Admin |
| PUT | `/api/v1/products/{id}/update` | Update product | Admin |
| DELETE | `/api/v1/products/{id}/delete` | Delete product | Admin |

### Cart
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/v1/carts/{cartId}` | View cart contents | User |
| POST | `/api/v1/carts/item/add` | Add item to cart | User |
| PUT | `/api/v1/carts/item/{itemId}/update` | Update item quantity | User |
| DELETE | `/api/v1/carts/item/{itemId}/remove` | Remove item from cart | User |
| DELETE | `/api/v1/carts/{cartId}/clear` | Clear entire cart | User |

### Orders
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/v1/orders/order` | Place order from cart | User |
| GET | `/api/v1/orders/{orderId}` | Get order details | User |
| GET | `/api/v1/orders/user/{userId}` | Get user order history | User |

---

## 🗄️ Database Schema (Entity Relationships)

```
User (1) ──────────────── (1) Cart
  │                              │
  │                        CartItem (Many)
  │                              │
  └──── Order (Many)        Product (1)
             │                    │
        OrderItem (Many)     Category (1)
             │                    │
         Product (1)          Image (Many)
```

Key JPA relationships used:
- `@OneToOne` — User ↔ Cart
- `@OneToMany` / `@ManyToOne` — Cart → CartItems, Order → OrderItems
- `@ManyToOne` — Product → Category

---

## 📁 Project Structure

```
src/main/java/com/safina/shoppingcart/
│
├── controller/               # REST layer — HTTP in, HTTP out
│   ├── AuthController.java
│   ├── CartController.java
│   ├── CartItemController.java
│   ├── CategoryController.java
│   ├── ImageController.java
│   ├── OrderController.java
│   ├── ProductController.java
│   └── UserController.java
│
├── service/                  # Business logic — one sub-package per domain
│   ├── cart/
│   ├── category/
│   ├── image/
│   ├── order/
│   ├── product/
│   └── user/
│
├── repository/               # Spring Data JPA interfaces (DB access)
│
├── model/                    # JPA Entities — mapped to DB tables
│
├── dto/                      # Data Transfer Objects — API response shapes
│
├── request/                  # Request body models (what client sends)
│
├── response/                 # Unified API response wrapper
│
├── data/                     # DataInitializer — seeds DB at startup
│
├── enums/                    # OrderStatus and other constants
│
├── exceptions/               # Custom exceptions + global @ControllerAdvice handler
│
└── security/
    ├── config/               # SecurityFilterChain, CORS, endpoint permissions
    ├── jwt/                  # JwtUtils (generate/validate), JwtAuthFilter
    └── user/                 # ShoppingCartUserDetails, UserDetailsServiceImpl
```

---

## 🛠️ Tech Stack

| Layer | Technology | Why |
|---|---|---|
| Language | Java 17 | Industry standard, strong typing, mature ecosystem |
| Framework | Spring Boot 3 | Auto-configuration, production-ready defaults |
| Security | Spring Security + JWT | Stateless auth, horizontally scalable |
| ORM | Hibernate / Spring Data JPA | Java objects ↔ DB tables, minimal boilerplate SQL |
| Database | MySQL 8 | Relational, ACID-compliant, production-proven |
| Build | Maven | Dependency management, reproducible builds |
| Mapping | ModelMapper | Clean DTO ↔ Entity conversion |
| Boilerplate | Lombok | Eliminates repetitive getters/setters/constructors |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Setup

```bash
# 1. Clone the repo
git clone https://github.com/SHAIKSHAFINA/CartForge-API.git
cd CartForge-API/shoppingcart

# 2. Create the database
mysql -u root -p
CREATE DATABASE cartforge_db;

# 3. Configure src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/cartforge_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# 4. Set your JWT secret
app.jwtSecret=your_base64_secret_key_here
app.jwtExpirationMs=86400000

# 5. Build and run
mvn spring-boot:run
```

### Test with Postman

```
Step 1 — Register
  POST /api/v1/auth/register
  Body: { "firstName": "Shafina", "lastName": "S", "email": "test@email.com", "password": "pass123" }

Step 2 — Login
  POST /api/v1/auth/login
  Body: { "email": "test@email.com", "password": "pass123" }
  Response: { "token": "eyJhbGci..." }  ← copy this token

Step 3 — Authenticated requests
  Add header to all further requests:
  Authorization: Bearer <your_token>

Step 4 — Add product (Admin)
  POST /api/v1/products/add

Step 5 — Add item to cart
  POST /api/v1/carts/item/add

Step 6 — Place order
  POST /api/v1/orders/order
```

*Built with ☕ Java and Spring Boot*

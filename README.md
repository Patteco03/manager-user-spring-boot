# 🚀 Spring Boot User API

A RESTful API built with **Spring Boot** for user management, featuring:

-   Full CRUD operations\
-   JWT-based authentication\
-   Spring Security integration\
-   Data validation\
-   Global exception handling\
-   Environment profiles (`dev`, `test`, `prod`)

------------------------------------------------------------------------

# 🧱 Architecture

The project follows a layered architecture:

    src/main/java/com/yourproject
    │
    ├── controller/        # HTTP layer
    ├── service/           # Business logic
    ├── repository/        # Data access (JPA)
    ├── domain/            # Entities
    ├── dto/               # API input/output
    ├── mapper/            # DTO <-> Entity mapping
    ├── security/          # JWT + authentication
    ├── exception/         # Global error handling
    ├── config/            # Configurations

------------------------------------------------------------------------

# 🔐 Authentication

The API uses **JWT (JSON Web Token)** for stateless authentication.

## 📌 Flow

1.  User logs in:

```{=html}
<!-- -->
```
    POST /auth/login

2.  Receives a token:

``` json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

3.  Sends token in requests:

```{=html}
<!-- -->
```
    Authorization: Bearer {token}

------------------------------------------------------------------------

# 👤 Main Endpoints

## 🔹 Users

Method   Endpoint      Description
  -------- ------------- ----------------
POST     /users        Create user
GET      /users        List users
GET      /users/{id}   Get user by ID
DELETE   /users/{id}   Delete user

------------------------------------------------------------------------

## 🔹 Authentication

Method   Endpoint      Description
  -------- ------------- ---------------
POST     /auth/login   Login & token

------------------------------------------------------------------------

## 🔹 Authenticated User

    GET /users/me

Returns the currently authenticated user.

------------------------------------------------------------------------

# ⚙️ Environment Profiles

The project supports multiple environments:

## 🧪 `dev`

-   Database: H2 (in-memory)\
-   H2 console enabled\
-   SQL logs enabled

## 🧪 `test`

-   Database: H2 (isolated)\
-   `ddl-auto=create-drop`

## 🚀 `prod`

-   Database: PostgreSQL\
-   Configuration via environment variables

------------------------------------------------------------------------

# 🌍 Environment Variables

``` bash
JWT_SECRET=your-super-secure-32-byte-secret
DB_URL=jdbc:postgresql://localhost:5432/appdb
DB_USER=postgres
DB_PASSWORD=your-password
```

------------------------------------------------------------------------

# 🗄️ H2 Database (dev)

Access:

    http://localhost:8080/h2-console

Configuration:

-   JDBC URL: `jdbc:h2:mem:devdb`\
-   User: `sa`\
-   Password: (empty)

------------------------------------------------------------------------

# 🛡️ Security

-   Passwords encrypted with BCrypt\
-   JWT with secure key (≥ 256 bits)\
-   Custom authentication filter\
-   Integrated with `UserDetailsService`

------------------------------------------------------------------------

# ❗ Error Handling

Standard error response:

``` json
{
  "code": "USER_NOT_FOUND",
  "message": "User not found",
  "status": 404,
  "timestamp": "2026-04-18T14:32:10",
  "path": "/users/123"
}
```

------------------------------------------------------------------------

# 🧪 Testing the API

### 1. Create user

    POST /auth/register

### 2. Login

    POST /auth/login

### 3. Use token

    Authorization: Bearer {token}

------------------------------------------------------------------------

# 🧠 Tech Stack

-   Java 21+\
-   Spring Boot\
-   Spring Security\
-   Spring Data JPA\
-   H2 Database\
-   PostgreSQL\
-   JWT (JJWT)\
-   Maven

------------------------------------------------------------------------

# 🚀 Future Improvements

-   [ ] Refresh token\
-   [ ] Role-based authorization (ADMIN / USER)\
-   [ ] Audit fields (created_at, updated_at)\
-   [ ] Soft delete\
-   [ ] Docker support\
-   [ ] CI/CD pipeline

------------------------------------------------------------------------

# 📌 Notes

This project was built with a focus on:

-   clean architecture principles\
-   separation of concerns\
-   security best practices\
-   real-world backend patterns

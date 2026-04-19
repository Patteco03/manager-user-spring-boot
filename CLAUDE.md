# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run (dev profile is active by default)
./mvnw spring-boot:run

# Run with a specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ManageUserApplicationTests
```

## Architecture

Layered Spring Boot application with JWT-based stateless auth. The active profile defaults to `dev` (H2 in-memory), switchable to `test` or `prod` (PostgreSQL).

```
controllers/    # HTTP layer — AuthController (/auth), UserController (/users)
service/        # Business logic — UserService (CRUD + password encoding)
repository/     # Spring Data JPA — UserRepository
domain/model/   # JPA entities — User
domain/config/  # SecurityConfig (filter chain, BCrypt bean)
dto/            # Request/response records — UserRequestDTO, UserResponseDTO (record), LoginRequestDTO
mapper/         # Static conversion — UserMapper (toEntity / toDTO)
security/auth/  # AuthService — validates credentials, delegates token generation
security/jwt/   # JwtService (generate/validate), JwtFilter (per-request auth), JwtProperties
exception/      # GlobalExceptionHandler, ApiError, ErrorCode enum, ResourceNotFoundException, BusinessException
```

## Key Conventions

**Exceptions:** Throw `ResourceNotFoundException` (404) or `BusinessException` (400) with an `ErrorCode` enum value. `GlobalExceptionHandler` converts them to a uniform `ApiError` JSON body (`code`, `message`, `status`, `timestamp`, `path`).

**Security:** `/auth/**` is public; all other routes require a valid Bearer JWT. `JwtFilter` extracts the email from the token, loads `UserDetails`, and sets the `SecurityContext`. JWT secret is read from `JWT_SECRET` env var; the fallback in `application.properties` is for local dev only — it must be ≥ 32 chars to meet JJWT's 256-bit HMAC requirement.

**DTOs:** `UserResponseDTO` is a Java record. New response DTOs should follow the same pattern.

**Password:** Always encoded through `PasswordEncoder` (BCrypt) in `UserService`, never raw.

## Profiles & Environment Variables

| Profile | DB | Activated by |
|---------|-----|-------------|
| `dev` (default) | H2 in-memory (`jdbc:h2:mem:devdb`) | default |
| `test` | H2 isolated, `create-drop` | `-Dspring-boot.run.profiles=test` |
| `prod` | PostgreSQL | `-Dspring-boot.run.profiles=prod` |

Required env vars for `prod`:
```
JWT_SECRET=<≥32-char secret>
DB_URL=jdbc:postgresql://localhost:5432/appdb
DB_USER=postgres
DB_PASSWORD=<password>
```

H2 console (dev only): `http://localhost:8080/h2-console` — JDBC URL `jdbc:h2:mem:devdb`, user `sa`, no password.

# Spring Security Interview Questions & Answers
## Learning Progress: Day 1–Current Stage

This README contains the Spring Security interview questions and answers covered so far, arranged from **basic → intermediate → advanced**.

---

# 1. Spring Security Fundamentals

## Q1. What is Spring Security?

Spring Security is a framework used to secure Spring applications. It mainly provides:

- Authentication
- Authorization
- Password management
- Session management
- CSRF protection
- Security filters
- Method-level security
- OAuth2 and JWT support

In simple terms:

```text
Spring Security
      |
      +-- Authentication → Who are you?
      |
      +-- Authorization  → What can you access?
```

---

## Q2. What is Authentication?

Authentication verifies the identity of a user.

Example:

```text
Username: pradip
Password: admin123
```

Spring Security checks whether the supplied credentials belong to a valid user.

**Interview answer:**

> Authentication is the process of verifying the identity of a user or system.

---

## Q3. What is Authorization?

Authorization determines what an authenticated user is allowed to access.

Example:

```text
ROLE_USER  → User pages
ROLE_ADMIN → Admin pages
```

**Interview answer:**

> Authorization is the process of determining whether an authenticated user has permission to access a particular resource.

---

## Q4. Authentication vs Authorization?

| Authentication | Authorization |
|---|---|
| Who are you? | What are you allowed to do? |
| Happens first | Happens after authentication |
| Validates identity | Validates permissions |
| Uses credentials | Uses roles/authorities |

Example:

```text
Login
  ↓
Authentication
  ↓
User identified as pradip
  ↓
Authorization
  ↓
Can pradip access /admin?
```

---

# 2. SecurityFilterChain

## Q5. What is SecurityFilterChain?

`SecurityFilterChain` defines how Spring Security should protect incoming HTTP requests.

Example:

```java
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/home").permitAll()
            .anyRequest().authenticated()
        );

    return http.build();
}
```

It controls things such as:

- URL authorization
- Login
- Logout
- CSRF
- HTTP Basic
- Session management
- Authentication mechanisms

---

## Q6. What is `HttpSecurity`?

`HttpSecurity` is the configuration object used to configure web security.

Example:

```java
http
    .authorizeHttpRequests(...)
    .formLogin(...)
    .csrf(...)
    .httpBasic(...);
```

---

## Q7. What does `permitAll()` mean?

It allows everyone to access the specified resource without authentication.

```java
.requestMatchers("/home").permitAll()
```

---

## Q8. What does `authenticated()` mean?

It means the user must be authenticated before accessing the resource.

```java
.anyRequest().authenticated()
```

---

# 3. Form Login

## Q9. What does `formLogin()` do?

It enables username/password form-based authentication.

Default:

```java
.formLogin(Customizer.withDefaults())
```

Spring Security can provide a default login page.

For a custom page:

```java
.formLogin(form -> form
    .loginPage("/login")
    .permitAll()
)
```

---

## Q10. How does custom login work?

Our custom HTML page contains:

```html
<form th:action="@{/login}" method="post">
```

The browser sends:

```text
POST /login
```

Spring Security processes the login request.

We normally do not write the password authentication logic inside the controller.

---

# 4. HTTP Basic

## Q11. What is HTTP Basic Authentication?

HTTP Basic sends username/password credentials using the HTTP `Authorization` header.

Configured using:

```java
http.httpBasic(Customizer.withDefaults());
```

It is commonly useful for:

- REST API testing
- Postman
- Simple service-to-service authentication

---

# 5. In-Memory Authentication

## Q12. What is In-Memory Authentication?

Users are stored in memory instead of a database.

Example:

```java
@Bean
public UserDetailsService userDetailsService(
        PasswordEncoder passwordEncoder) {

    UserDetails user = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin123"))
        .roles("ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user);
}
```

Architecture:

```text
Login
  ↓
Spring Security
  ↓
InMemoryUserDetailsManager
  ↓
Memory
```

Useful for:

- Learning
- Testing
- Prototypes

Not normally suitable for production user management.

---

# 6. PasswordEncoder and BCrypt

## Q13. Why do we use PasswordEncoder?

Passwords should never be stored as plain text.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

## Q14. Why BCrypt?

BCrypt is a password hashing algorithm designed for password storage.

It is intentionally computationally expensive and includes a salt.

Example:

```text
admin123
    ↓
BCrypt
    ↓
$2a$10$......................
```

The database stores the hash, not the original password.

---

## Q15. Is BCrypt encryption?

No.

BCrypt is a **one-way password hashing mechanism**, not normal reversible encryption.

You do not decrypt a BCrypt password.

---

## Q16. How does BCrypt verify a password?

Suppose the user enters:

```text
admin123
```

Database contains:

```text
$2a$10$......................
```

Spring Security uses:

```java
passwordEncoder.matches(
    rawPassword,
    storedHash
);
```

Conceptually:

```text
Raw Password
     +
Stored BCrypt Hash
     ↓
PasswordEncoder.matches()
     ↓
true / false
```

---

# 7. Database Authentication

## Q17. Why do we need database authentication?

In a real application, users normally need to be persisted in a database.

Example:

```text
users
----------------------------
id
username
password
enabled
role_id
```

---

## Q18. What is UserDetailsService?

`UserDetailsService` is a Spring Security interface used to retrieve user information by username.

Important method:

```java
UserDetails loadUserByUsername(String username)
```

---

## Q19. What is CustomUserDetailsService?

It is our implementation of `UserDetailsService`.

Example:

```java
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(
            String username) {

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "User not found"));

        return new CustomUserDetails(user);
    }
}
```

Its responsibility is mainly:

```text
Username
   ↓
Repository
   ↓
Database
   ↓
User
   ↓
UserDetails
```

---

## Q20. Who calls `loadUserByUsername()`?

We do not normally call it manually from our controller.

During authentication, Spring Security's authentication infrastructure, commonly `DaoAuthenticationProvider`, calls:

```java
loadUserByUsername(username)
```

---

# 8. DaoAuthenticationProvider

## Q21. What is DaoAuthenticationProvider?

`DaoAuthenticationProvider` is an `AuthenticationProvider` implementation commonly used for username/password authentication backed by a `UserDetailsService`.

It coordinates:

```text
UserDetailsService
        +
PasswordEncoder
        ↓
Authentication
```

---

## Q22. What does DaoAuthenticationProvider do?

Conceptually:

```text
Login Request
     ↓
DaoAuthenticationProvider
     ↓
UserDetailsService
     ↓
loadUserByUsername()
     ↓
Database
     ↓
UserDetails
     ↓
PasswordEncoder
     ↓
Password Verification
     ↓
Success / Failure
```

---

# 9. CustomUserDetails

## Q23. Why do we need CustomUserDetails?

Our database entity is an application object:

```text
User
 ├── id
 ├── username
 ├── password
 ├── enabled
 └── role
```

Spring Security expects a `UserDetails`.

Therefore:

```text
User Entity
    ↓
CustomUserDetails
    ↓
Spring Security
```

`CustomUserDetails` acts as an adapter between our domain model and Spring Security.

---

## Q24. What is `getUsername()`?

It returns the username used by Spring Security.

```java
@Override
public String getUsername() {
    return user.getUsername();
}
```

---

## Q25. What is `getPassword()`?

It returns the stored password hash.

```java
@Override
public String getPassword() {
    return user.getPassword();
}
```

---

## Q26. What is `getAuthorities()`?

It returns the permissions/roles that Spring Security uses for authorization.

Example:

```java
return List.of(
    new SimpleGrantedAuthority(
        user.getRole().getRoleName()
    )
);
```

If the database contains:

```text
ROLE_ADMIN
```

Spring Security receives:

```text
GrantedAuthority → ROLE_ADMIN
```

---

# 10. Role and Authority

## Q27. What is a role?

A role represents a security category assigned to a user.

Examples:

```text
ROLE_USER
ROLE_ADMIN
ROLE_MANAGER
```

Roles are commonly used for coarse-grained access control.

---

## Q28. What is an authority?

A `GrantedAuthority` represents a permission or authority known to Spring Security.

Examples:

```text
ROLE_ADMIN
READ_EMPLOYEE
DELETE_EMPLOYEE
CREATE_REPORT
```

---

# 11. User Entity and Role Entity

## Q29. Why do we create User and Role entities?

Because our application stores users and roles in the database.

Example:

```java
@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;

    @ManyToOne
    private Role role;
}
```

---

## Q30. Why use `@ManyToOne` between User and Role?

Multiple users can have the same role.

```text
Pradip ─────┐
Rahul ──────┼──> ROLE_USER
Amit ───────┘
```

Therefore:

```java
@ManyToOne
private Role role;
```

---

# 12. Why `enabled`?

`enabled` represents whether the account is active.

```java
@Override
public boolean isEnabled() {
    return user.isEnabled();
}
```

Example:

```text
enabled = true
→ Account can authenticate

enabled = false
→ Authentication can be rejected
```

---

# 13. Registration Flow

Our registration process:

```text
Browser
   ↓
register.html
   ↓
POST /register
   ↓
RegistrationPageController
   ↓
RegistrationService
   ↓
PasswordEncoder
   ↓
BCrypt
   ↓
UserRepository
   ↓
MySQL
```

---

# 14. REST Registration vs Browser Registration

We learned two approaches.

### REST

```text
Postman
   ↓
POST /auth/register
   ↓
JSON
   ↓
@RestController
```

### Browser

```text
Browser
   ↓
GET /register
   ↓
register.html
   ↓
POST /register
   ↓
@Controller
```

Both can use:

```text
RegistrationService
```

This avoids duplicating business logic.

---

# 15. Duplicate Username Handling

We used:

```java
userRepository.existsByUsername(username)
```

If true:

```text
UsernameAlreadyExistsException
```

Then:

```text
@RestControllerAdvice
```

can convert it into a suitable HTTP response such as:

```text
409 CONFLICT
```

---

# 16. CSRF

## Q31. What is CSRF?

CSRF stands for **Cross-Site Request Forgery**.

It is a security attack where an attacker attempts to make a user's browser perform an unwanted state-changing request to an application where the user is already authenticated.

Spring Security provides CSRF protection.

We temporarily used:

```java
.csrf(csrf -> csrf.disable())
```

for our Postman learning/testing scenario.

We will later learn when CSRF should be enabled or disabled.

---

# 17. Complete Database Authentication Flow

This is one of the most important interview diagrams:

```text
Browser
   ↓
POST /login
   ↓
UsernamePasswordAuthenticationFilter
   ↓
AuthenticationManager
   ↓
DaoAuthenticationProvider
   ↓
CustomUserDetailsService
   ↓
loadUserByUsername()
   ↓
UserRepository
   ↓
MySQL
   ↓
User Entity
   ↓
CustomUserDetails
   ↓
PasswordEncoder
   ↓
BCrypt verification
   ↓
Authentication SUCCESS / FAILURE
```

---

# 18. Most Important Interview Question

## Q32. Explain database authentication in Spring Security.

### Strong interview answer:

> In our Spring Boot application, we use database-backed authentication with a custom `UserDetailsService`. When the user submits the login form, Spring Security's authentication infrastructure processes the request. `DaoAuthenticationProvider` calls our `CustomUserDetailsService`, which uses `UserRepository` to find the user by username. The database user is converted into `CustomUserDetails`. Spring Security then uses the configured `PasswordEncoder`, such as BCrypt, to compare the raw password provided during login with the stored BCrypt hash. If the credentials are valid and the account is enabled, authentication succeeds and an authenticated SecurityContext is established.

---

# 19. Advanced Interview Flow

```text
HTTP Request
     ↓
SecurityFilterChain
     ↓
Authentication Filter
     ↓
AuthenticationManager
     ↓
AuthenticationProvider
     ↓
DaoAuthenticationProvider
     ↓
UserDetailsService
     ↓
Repository
     ↓
Database
     ↓
UserDetails
     ↓
PasswordEncoder
     ↓
Authentication
     ↓
SecurityContext
     ↓
Authorization
```

Remember this architecture. It will be reused throughout the rest of the course.

---

# Next Day Overview — Role-Based Authorization

The next stage is:

## Day Next: Roles, Authorities & Authorization

We will build on our current project and implement:

### 1. Multiple users

```text
pradip → ROLE_USER
admin  → ROLE_ADMIN
```

### 2. Role-based URLs

For example:

```text
/user/**   → ROLE_USER
/admin/**  → ROLE_ADMIN
```

### 3. `hasRole()`

```java
.hasRole("ADMIN")
```

### 4. `hasAnyRole()`

```java
.hasAnyRole("ADMIN", "MANAGER")
```

### 5. `hasAuthority()`

```java
.hasAuthority("ROLE_ADMIN")
```

### 6. Most important distinction

We will deeply understand:

```text
hasRole("ADMIN")
        vs
hasAuthority("ROLE_ADMIN")
```

and the automatic `ROLE_` prefix behavior.

### 7. Real project implementation

We will create endpoints such as:

```text
GET /user/home
GET /admin/home
GET /admin/users
GET /employee/home
```

and test:

```text
ROLE_USER
       ↓
Can access user endpoint
       ↓
Cannot access admin endpoint
```

while:

```text
ROLE_ADMIN
       ↓
Can access user endpoint
       ↓
Can access admin endpoint
```

### 8. Testing

We'll test both:

```text
Browser
Postman
```

and trace the complete flow:

```text
Request
 ↓
Authentication
 ↓
SecurityContext
 ↓
GrantedAuthority
 ↓
Authorization
 ↓
Access granted / 403 Forbidden
```

### 9. Interview preparation

We'll cover questions such as:

- What is the difference between authentication and authorization?
- What is a role?
- What is a `GrantedAuthority`?
- `hasRole()` vs `hasAuthority()`?
- Why does Spring Security add `ROLE_`?
- Why do we get 403 instead of 401?
- What happens internally when a user accesses an unauthorized URL?
- Where are authorities stored after login?
- What is `SecurityContext`?
- How does Spring Security know which role belongs to the logged-in user?

---

# Current Learning Position

```text
Day 1–3  → Spring Security Fundamentals
Day 4–6  → SecurityFilterChain & Request Security
Day 7–8  → In-Memory Authentication
Day 9–10 → Database Authentication
Current  → Registration + Custom Login + BCrypt
Next     → Role-Based Authorization
```

The next major concept is **not another isolated configuration**. We will take the user we have already registered in MySQL and use that same user to understand **Role → Authority → SecurityContext → Authorization → 403** end-to-end.

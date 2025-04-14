# StarScan API

StarScan API is a Spring Boot application that provides endpoints to interact with the Star Wars API (SWAPI). It allows users to retrieve information about Star Wars characters (people) and films.

## Features

- Retrieve a paginated list of Star Wars characters.
- Fetch details of a specific character by ID or name.
- Retrieve a paginated list of Star Wars films.
- Fetch details of a specific film by ID.
- Secure endpoints using JWT-based authentication.
- OpenAPI documentation available via Swagger UI.

---

# API Documentation

## Controllers

### 1. **AuthController**
Handles user authentication and JWT token generation.

- **Base URL**: `/authenticate`

#### Endpoints:
- **POST `/authenticate`**
  - **Description**: Authenticates a user and generates a JWT token.
  - **Request Parameters**:
    - `Content-Type`: application/x-www-form-urlencoded
    - `username` (String) - testUser
    - `password` (String) - testPassword
  - **Responses**:
    - `200 OK`: Returns the generated JWT token.
    - `401 Unauthorized`: Invalid credentials.

---

### 2. **PeopleController**
Manages operations related to Star Wars characters.

- **Base URL**: `/starscan/people`

#### Endpoints:
- **GET `/starscan/people`**
  - **Description**: Retrieves a paginated list of Star Wars characters.
  - **Request Parameters**:
    - `page` (int, default: 1) - The page number.
    - `limit` (int, default: 10) - The number of items per page.
  - **Responses**:
    - `200 OK`: Returns a list of characters.
    - `500 Internal Server Error`: Error fetching the list.

- **GET `/starscan/people/{id}`**
  - **Description**: Retrieves details of a specific Star Wars character by ID.
  - **Path Parameters**:
    - `id` (String) - The ID of the character.
  - **Responses**:
    - `200 OK`: Returns the character details.
    - `404 Not Found`: Character not found.

- **GET `/starscan/people?name={name}`**
  - **Description**: Retrieves details of Star Wars characters by their name.
  - **Request Parameters**:
    - `name` (String) - The name of the character.
  - **Responses**:
    - `200 OK`: Returns the character details.
    - `404 Not Found`: Character not found.

---

### 3. **FilmsController**
Manages operations related to Star Wars films.

- **Base URL**: `/starscan/films`

#### Endpoints:
- **GET `/starscan/films`**
  - **Description**: Retrieves a paginated list of Star Wars films.
  - **Request Parameters**:
    - `page` (int, default: 1) - The page number.
    - `limit` (int, default: 10) - The number of items per page.
  - **Responses**:
    - `200 OK`: Returns a list of films.
    - `500 Internal Server Error`: Error fetching the list.

- **GET `/starscan/films/{id}`**
  - **Description**: Retrieves details of a specific Star Wars film by ID.
  - **Path Parameters**:
    - `id` (String) - The ID of the film.
  - **Responses**:
    - `200 OK`: Returns the film details.
    - `404 Not Found`: Film not found.

- **GET `/starscan/films?title={title}`**
  - **Description**: Retrieves details of Star Wars films by their title.
  - **Request Parameters**:
    - `title` (String) - The title of the film.
  - **Responses**:
    - `200 OK`: Returns the film details.
    - `404 Not Found`: Film not found.

---

### 4. **StarshipsController**
Manages operations related to Star Wars starships.

- **Base URL**: `/starscan/starships`

#### Endpoints:
- **GET `/starscan/starships`**
  - **Description**: Retrieves a paginated list of Star Wars starships.
  - **Request Parameters**:
    - `page` (int, default: 1) - The page number.
    - `limit` (int, default: 10) - The number of items per page.
  - **Responses**:
    - `200 OK`: Returns a list of starships.
    - `500 Internal Server Error`: Error fetching the list.

- **GET `/starscan/starships/{id}`**
  - **Description**: Retrieves details of a specific Star Wars starship by ID.
  - **Path Parameters**:
    - `id` (String) - The ID of the starship.
  - **Responses**:
    - `200 OK`: Returns the starship details.
    - `404 Not Found`: Starship not found.

- **GET `/starscan/starships?name={name}`**
  - **Description**: Retrieves details of Star Wars starships by their name.
  - **Request Parameters**:
    - `name` (String) - The name of the starships.
  - **Responses**:
    - `200 OK`: Returns the starship details.
    - `404 Not Found`: starship not found.

---

### 5. **VehiclesController**
Manages operations related to Star Wars vehicles.

- **Base URL**: `/starscan/vehicles`

#### Endpoints:
- **GET `/starscan/vehicles`**
  - **Description**: Retrieves a paginated list of Star Wars vehicles.
  - **Request Parameters**:
    - `page` (int, default: 1) - The page number.
    - `limit` (int, default: 10) - The number of items per page.
  - **Responses**:
    - `200 OK`: Returns a list of vehicles.
    - `500 Internal Server Error`: Error fetching the list.

- **GET `/starscan/vehicles/{id}`**
  - **Description**: Retrieves details of a specific Star Wars vehicle by ID.
  - **Path Parameters**:
    - `id` (String) - The ID of the vehicle.
  - **Responses**:
    - `200 OK`: Returns the vehicle details.
    - `404 Not Found`: Vehicle not found.

- **GET `/starscan/vehicles?name={name}`**
  - **Description**: Retrieves details of Star Wars vehicle by their name.
  - **Request Parameters**:
    - `name` (String) - The name of the vehicle.
  - **Responses**:
    - `200 OK`: Returns the vehicle details.
    - `404 Not Found`: vehicle not found.
---

## Configuration

### Security
The application uses Spring Security and JWT for securing endpoints:
- The `/authenticate` endpoint is publicly accessible for obtaining a JWT token.
- All `/starscan/**` endpoints require a valid JWT token for access.
- Swagger UI and OpenAPI documentation are publicly accessible.
- CSRF protection is disabled for simplicity (not recommended for production).

### JWT Configuration
- The application uses a secure Base64-encoded secret key for signing JWT tokens.
- Tokens are valid for 10 hours by default.

### OpenAPI Documentation
The API documentation is available at:
- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

## Exception Handling

The application includes custom exception handling:
- `ResourceNotFoundException`: Thrown when a requested resource is not found.
- `ControllerException`: Used for handling general controller-level errors.

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: For securing the endpoints.
- **JWT (io.jsonwebtoken)**: For token-based authentication.
- **Swagger/OpenAPI**: For API documentation.
- **Maven**: Build tool.

## How to Run

1. Clone the repository.
2. Build the project using Maven:
   ```bash
   mvn clean install
3. Run the application with more memory:
   ```bash
   java -Xms512m -Xmx1024m -jar target/starscan-0.0.1-SNAPSHOT.jar




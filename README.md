# StarScan API

StarScan API is a Spring Boot application that provides endpoints to interact with the Star Wars API (SWAPI). It allows users to retrieve information about Star Wars characters (people) and films.

## Features

- Retrieve a paginated list of Star Wars characters.
- Fetch details of a specific character by ID or name.
- Retrieve a paginated list of Star Wars films.
- Fetch details of a specific film by ID.
- Secure endpoints using JWT-based authentication.
- OpenAPI documentation available via Swagger UI.

## Endpoints

### Authentication Endpoint

#### Authenticate User
- **URL**: `/authenticate`
- **Method**: `POST`
- **Request Parameters**:
    - `username`: The username of the user.
    - `password`: The password of the user.
- **Response**: A JWT token to be used for accessing secured endpoints.

### People Endpoints

#### Get a List of People
- **URL**: `/starscan/people`
- **Method**: `GET`
- **Query Parameters**:
    - `page` (optional, default: `1`): The page number to retrieve.
    - `limit` (optional, default: `10`): The number of items per page.
- **Response**: A paginated list of Star Wars characters.

#### Get a Person by ID
- **URL**: `/starscan/people/{id}`
- **Method**: `GET`
- **Path Parameters**:
    - `id`: The ID of the person to retrieve.
- **Response**: Details of the specified Star Wars character.

#### Get a Person by Name
- **URL**: `/starscan/people`
- **Method**: `GET`
- **Query Parameters**:
    - `name`: The name of the person to retrieve.
- **Response**: Details of the specified Star Wars character.

### Films Endpoints

#### Get a List of Films
- **URL**: `/starscan/films`
- **Method**: `GET`
- **Query Parameters**:
    - `page` (optional, default: `1`): The page number to retrieve.
    - `limit` (optional, default: `10`): The number of items per page.
- **Response**: A paginated list of Star Wars films.

#### Get a Film by ID
- **URL**: `/starscan/films/{id}`
- **Method**: `GET`
- **Path Parameters**:
    - `id`: The ID of the film to retrieve.
- **Response**: Details of the specified Star Wars film.

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
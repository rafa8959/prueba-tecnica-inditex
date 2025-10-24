# Inditex Price API

REST API developed in **Java 17 + Spring Boot**, following **Hexagonal Architecture** principles and an **API-First** approach using **OpenAPI 3.0**.  
It allows querying applicable price rates for a given product, brand, and date.

## API First

The API contract is defined in [price-api.yaml](/src/main/resources/static/price-api.yaml)

The contract is used to:

- Automatically generate API interfaces and models.

- Ensure consistency between implementation and documentation.

You can visualize or test the API specification through Swagger UI at:

[https://rafaedo.com/swagger-ui/index.html](https://rafaedo.com/swagger-ui/index.html)

## Architecture

**Domain Layer:** pure business logic (entities, validations, value objects).

**Application Layer:** orchestrates use cases.

**Infrastructure Layer:** implements input/output adapters.

## Technologies & Libraries

- Java 17

- Spring Boot 3.5

- H2 Database

- OpenAPI / Swagger 3.0

- JUnit 5 / Karate

- Docker

## Live Demo

You can access a live demo of the API here:

[https://rafaedo.com/v1/prices](https://rafaedo.com/v1/prices?applicationDate=2020-06-14T10:00:00Z&productId=35455&brandId=1)

Example request:

`GET https://rafaedo.com/v1/prices?applicationDate=2020-06-14T16:00:00Z&productId=35455&brandId=1`

## Local Execution
```bash
mvn clean spring-boot:run
```
The service will be available at:

`http://localhost:8080/v1/prices`

## Run with Docker
```bash
# Build the image
mvn clean package -DskipTests
docker build -t inditex-price-api .

# Run the container
docker run -p 8080:8080 inditex-price-api
```
Verify the application at:
`http://localhost:8080/v1/prices`

## Tests
### Unit Tests

Run unit tests with:

```bash
mvn test
```

### Integration Tests (Karate)

Integration tests are located at:

[src/test/resources/karate/prices.feature](src/main/resources/karate/prices.feature)

You can run them with:

`mvn test -Dtest=PriceFeatureRunner`

After execution, an interactive HTML report is generated at:

`target/karate-reports/karate-summary.html`

### Postman

A Postman collection is included for manual endpoint testing:

[/postman/Prices.postman_collection.json](/postman/Prices.postman_collection.json)

Import it into Postman to easily test all endpoints.

## Autor

**Developed by:** Rafa Edo

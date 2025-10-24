# Inditex Price API

API REST desarrollada en **Java 17 + Spring Boot**, siguiendo principios de **Arquitectura Hexagonal** y un enfoque **API-First** con **OpenAPI 3.0**.  
Permite consultar las tarifas aplicables de precios para un producto y marca en una fecha concreta.

## Arquitectura

**Capa Dominio:** lógica de negocio pura (entidades, validaciones, value objects).

**Capa Aplicación:** orquesta casos de uso.

**Capa Infraestructura:** implementa los adaptadores de entrada y salida.

## Tecnologías y Librerías

- Java 17

- Spring Boot 3.5

- H2 Database

- OpenAPI / Swagger 3.0

- JUnit 5 / Karate

- Docker

## Ejecución Local
```bash
mvn clean spring-boot:run
```
El servicio estará disponible en:

`http://localhost:8080/v1/prices`

Ejemplo de petición:

`GET http://localhost:8080/v1/prices?applicationDate=2020-06-14T16:00:00Z&productId=35455&brandId=1`

## Ejecución con Docker
```bash
# Construir la imagen
mvn clean package -DskipTests
docker build -t inditex-price-api .

# Ejecutar el contenedor
docker run -p 8080:8080 inditex-price-api
```
Verifica la ejecución en:
`http://localhost:8080/v1/prices`

## Documentación API
La aplicación expone documentación interactiva generada automáticamente con **Springdoc OpenAPI.**

Una vez la aplicación esté levantada, puedes acceder al Swagger UI en:
http://localhost:8080/swagger-ui.html

## Tests
### Unitarios

Ejecuta los tests unitarios con:

```bash
mvn test
```

### Integración (Karate)

Los tests de integración se encuentran en:

[src/test/resources/karate/prices.feature](src/main/resources/karate/prices.feature)

Puedes ejecutarlos con:

`mvn test -Dtest=PriceFeatureRunner`

Tras la ejecución, se genera un reporte HTML interactivo en:

`target/karate-reports/karate-summary.html`

### Postman

Se ha incluido una colección en:

[/postman/Prices.postman_collection.json](/postman/Prices.postman_collection.json)

Importa esta colección en Postman para probar los endpoints manualmente.

## Autor

**Desarrollado por:** Rafa Edo

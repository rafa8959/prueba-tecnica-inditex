# Inditex Price Service

# Infrastructure / REST Layer

## Descripción general
Esta rama implementa la **capa de infraestructura REST** de la arquitectura **hexagonal** del proyecto **Inditex Price API**.  

Su responsabilidad principal es **exponer la API HTTP pública** que permite consultar los precios aplicables para un producto de una marca en una fecha determinada.

Toda la lógica de negocio reside en las capas **application** (casos de uso) y **domain**.  
Esta capa se encarga únicamente de:

- Recibir y validar las peticiones HTTP entrantes.  
- Delegar en el caso de uso correspondiente.  
- Mapear los modelos de dominio a DTOs (`PriceResponse`).  
- Gestionar excepciones y devolver respuestas normalizadas.  


## Componentes principales

| Componente | Descripción |
|-------------|-------------|
| **`PriceController`** | Controlador REST principal. Implementa la interfaz `PricesApi` generada a partir del contrato OpenAPI. Recibe los parámetros de consulta (`applicationDate`, `productId`, `brandId`) y delega en `GetApplicablePricesUseCase`. |
| **`PriceRestMapper`** | Mapper que convierte entidades de dominio (`Price`) en modelos de respuesta (`PriceResponse`). Marca el primer precio como `applied=true`. |
| **`GlobalExceptionHandler`** | Manejador global de excepciones. Intercepta errores del dominio o de validación y devuelve respuestas `ErrorResponse` con códigos HTTP adecuados (`400`, `404`, `500`). |
| **Tests Karate** | Tests de integración E2E que validan el correcto comportamiento de la API y los distintos escenarios (felices y de error). |

## Tests de integración (Karate)

Los tests de integración se definen en:
- `src/test/resources/karate/prices.feature`

Ejecutar con:
```bash
mvn test -Dtest=PriceFeatureRunner
```

## Colección Postman
También se ha dejado una colección Postman con las mismas peticiones usadas en los tests automáticos.
Ruta del archivo:
- `postman/Prices.postman_collection.json`

Puedes importarla en Postman y probar los endpoints manualmente.
Asegúrate de tener levantada la aplicación en http://localhost:8080.

Ejemplo de endpoint principal:
GET `http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00Z&productId=35455&brandId=1`
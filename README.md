# Inditex Price Service

## 🧱 Persistence Layer

The persistence layer implements the data access logic using **Spring Data JPA** and an in-memory **H2 database**.  
It is responsible for retrieving and mapping price data between the database schema and the domain model.

### Components
- **Entities**: `PriceEntity`, `PriceId` → Represent the `PRICES` table.
- **Repository adapter**: `PriceRepositoryImpl` → Implements the domain `PriceRepository` port.
- **JPA Repository**: `SpringDataPriceRepository` → Defines the SQL query for applicable prices.
- **Mapper**: `PriceEntityMapper` → Converts between JPA entities and domain models.

### Database
- Schema: `/src/main/resources/schema.sql`
- Seed data: `/src/main/resources/data.sql`

### Tests
Integration tests validate:
- Database connectivity and schema loading.
- Entity mappings and query correctness.
- Full conversion from persistence to domain (`PriceRepositoryImplIntegrationTest`).
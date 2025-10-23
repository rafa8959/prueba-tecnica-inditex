# Inditex Price Service

This project follows an **API-First** and **Hexagonal Architecture** approach for the Inditex Price Service.

## 🧩 Project Structure (Current State)

| Layer | Description | Status |
|-------|--------------|--------|
| **API Contract** | Defined in `src/main/resources/static/price-api.yaml` following OpenAPI 3.0.3. | Completed |
| **Domain Layer** | Contains the core model (`Price`, `Money`, `DateRange`) and business services. | Completed |
| **Application / Infrastructure** | Adapters for persistence (H2/JPA) and REST controller. | Pending implementation |

## 🧪 Testing

- **Unit tests** cover:
  - `Money`, `DateRange`, and `Price` domain objects.
  - `PriceDomainService` domain service.

To run the existing tests:
```bash
mvn clean test
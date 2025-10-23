# Inditex Price Service

## Application Layer

Implements the use case logic of the Inditex Price Service.

### Components
- `GetApplicablePricesUseCase`: orchestrates repository and domain logic.

### Notes
- Follows hexagonal architecture principles.
- Annotated with `@Transactional(readOnly = true)` for consistent transaction boundaries.
- Tested via unit tests with Mockito.

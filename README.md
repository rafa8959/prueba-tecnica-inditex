# API Contract – Inditex Price Service

This project defines the **API contract** (`price-api.yaml`) for the **Inditex Price Service**.

## Purpose
- Apply an **API-first** approach: the contract is defined **before** any implementation.
- Ensure that future domain, persistence, and test layers are built strictly against this specification.

## OpenAPI Generator – API First Automation

This project uses the **[OpenAPI Generator Maven Plugin](https://openapi-generator.tech/)**  
to automatically generate Java interfaces and models from the OpenAPI contract.

### Location
- Contract file: `src/main/resources/static/price-api.yaml`
- Generated sources: `target/generated-sources/openapi/`

### How to generate classes
Run:
```bash
mvn clean compile

# 🧠 Product Pricing API

Una API REST desarrollada con Spring Boot que expone información de precios de productos en función de las fechas y prioridades. Está basada en principios de **arquitectura hexagonal** (puertos y adaptadores) y cubre la lógica con pruebas automatizadas.

---

## 🚀 Tecnologías

- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- H2
- Lombok
- OpenAPI
- MapStruct
- JUnit 5 + Mockito
- Maven

---

## 🧱 Arquitectura

La aplicación está diseñada siguiendo los principios de **arquitectura limpia** y se estructura en capas según el patrón hexagonal:

- **`application`**: lógica de aplicación (DTOs y casos de uso).
- **`domain`**: lógica del dominio, modelos y puertos (interfaces).
- **`infrastructure`**: entrada (REST controllers), salida (repositorios), mappers y gestión de excepciones.

---

## ⚙️ Ejecución

### Requisitos

- Java 21
- Maven

### ✅ Cómo usar la API
```bash
./mvnw spring-boot:run
```

#### Pruebas navegador/postman (respuesta OK 200)
GET http://localhost:8081/api/v1/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

#### Open API
Acceder a http://localhost:8081/swagger-ui/index.html e introducir los valores deseados para probar:
![image](https://github.com/user-attachments/assets/28a71f96-629a-4d5c-b51f-571b9429ea39)


#### Ejecutar pruebas unitarias y de integración:
```bash
./mvnw test
```

![image](https://github.com/user-attachments/assets/1fdf22ec-29ff-42d2-9695-dfaff31d35dd)


## Acceso a consola H2 (modo test)

- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:testdb`

## 📁 Estructura del Proyecto

```text
├── HELP.md                        # Documentación adicional
├── mvnw                           # Wrapper Maven para Unix
├── mvnw.cmd                       # Wrapper Maven para Windows
├── pom.xml                        # Configuración Maven
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── inditex
│   │   │           └── technical
│   │   │               ├── application
│   │   │               │   ├── dto
│   │   │               │   │   └── ProductPricingDTO.java  # Objeto de transferencia de datos (DTO) para la información de precios de productos
│   │   │               │   └── usecase
│   │   │               │       └── FindProductPricingUseCase.java  # Caso de uso para buscar precios de productos
│   │   │               ├── domain
│   │   │               │   ├── model
│   │   │               │   │   └── ProductPricing.java  # Entidad que representa la información de precios de productos
│   │   │               │   └── ports
│   │   │               │       ├── ProductPricingRepositoryPort.java  # Interfaz para el repositorio de precios de productos
│   │   │               │       └── ProductPricingServicePort.java  # Interfaz para los servicios relacionados con los precios de productos
│   │   │               ├── infrastructure
│   │   │               │   ├── exceptions
│   │   │               │   │   ├── ErrorResponse.java  # Respuesta de error para las excepciones
│   │   │               │   │   └── GlobalExceptionHandler.java  # Manejador global de excepciones
│   │   │               │   ├── input
│   │   │               │   │   └── rest
│   │   │               │   │       └── ProductPricingController.java  # Controlador REST para la gestión de precios de productos
│   │   │               │   ├── mapper
│   │   │               │   │   └── ProductPricingMapper.java  # Mapper que convierte entre las entidades y DTOs
│   │   │               │   └── output
│   │   │               │       └── persistence
│   │   │               │           ├── ProductPricingEntity.java  # Entidad JPA que representa los precios de productos en la base de datos
│   │   │               │           ├── ProductPricingJpaRepository.java  # Repositorio JPA para interactuar con la base de datos
│   │   │               │           └── ProductPricingRepositoryAdapter.java  # Adaptador que implementa la interfaz del repositorio
│   │   │               └── TechnicalApplication.java  # Clase principal de la aplicación, contiene el método `main`
│   │   └── resources
│   │       ├── application.yml        # Configuración de la aplicación
│   │       ├── data.sql               # Datos iniciales para la base de datos en memoria
│   │       ├── static                 # Archivos estáticos (imágenes, CSS, etc.)
│   │       └── templates              # Plantillas de la aplicación
│   └── test
│       └── java
│           └── com
│               └── inditex
│                   └── technical
│                       ├── application
│                       │   └── usecase
│                       │       └── FindProductPricingUseCaseTest.java  # Pruebas unitarias para el caso de uso de búsqueda de precios de productos
│                       ├── infrastructure
│                       │   ├── input
│                       │   │   └── rest
│                       │   │       └── ProductPricingControllerTest.java  # Pruebas unitarias para el controlador REST de precios de productos
│                       │   ├── mapper
│                       │   │   └── ProductPricingMapperTest.java  # Pruebas unitarias para el mapper de precios de productos
│                       │   └── output
│                       │       └── persistence
│                       │           └── ProductPricingRepositoryAdapterTest.java  # Pruebas unitarias para el adaptador de repositorio de precios de productos
│                       └── TechnicalApplicationTests.java  # Pruebas de integración para la aplicación


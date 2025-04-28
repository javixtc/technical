# 🧠 Product Pricing API

Una API REST desarrollada con Spring Boot que expone información de precios de productos en función de las fechas y prioridades. Está basada en principios de **arquitectura hexagonal** (puertos y adaptadores) y cubre la lógica con pruebas automatizadas.

---

## 🧠 Descripción del problema
Dentro del sistema core de ecommerce de Inditex, se mantiene una base de datos que almacena los precios de los productos, que varían según la marca, el producto y el rango de fechas. La información relevante de la tabla PRICES incluye el identificador del producto, el rango de fechas de validez del precio, la tarifa que se aplica en ese periodo, y la prioridad en caso de solapamiento de fechas. La API debe proporcionar un punto de acceso REST para consultar los precios de los productos basándose en la fecha de consulta, el producto y la marca.

---

## 🚀 Tecnologías

La API está construida utilizando las siguientes tecnologías, que ofrecen ventajas específicas para este tipo de problemas:

- Java 21: Lenguaje de programación de última generación, utilizado para aprovechar nuevas características del lenguaje y la JVM para mejorar el rendimiento y la productividad.

- Spring Boot 3.4.3: Framework robusto y bien soportado para construir aplicaciones Java, utilizado para crear una API REST que sea fácil de desplegar y configurar.

- Spring Data JPA: Para interactuar con la base de datos H2 de forma eficiente, utilizando la abstracción de JPA para simplificar las consultas y el acceso a datos.

- H2: Base de datos en memoria que permite realizar pruebas rápidas y manejar datos de ejemplo de forma eficiente sin necesidad de configurar una base de datos persistente.

- Lombok: Biblioteca que reduce el boilerplate de código en Java, como getters, setters y constructores, lo que mejora la legibilidad del código.

- OpenAPI: Utilizado para documentar y generar especificaciones de la API, lo que facilita la integración con otros servicios y herramientas.

- MapStruct: Herramienta para la conversión eficiente entre objetos, utilizada para mapear entre las entidades JPA y los DTOs.

- JUnit 5 + Mockito: Frameworks para realizar pruebas unitarias e integrar la lógica del sistema con simulaciones de dependencias, asegurando el correcto funcionamiento de la API.

- Maven: Herramienta de construcción que facilita la gestión de dependencias y la construcción de la aplicación.

---

## 🧱 Arquitectura

La arquitectura de este proyecto sigue los principios de **Clean Architecture**, donde las diferentes preocupaciones están separadas en capas bien definidas, lo que facilita la mantenibilidad, escalabilidad y facilidad de pruebas.

### Capa de Aplicación (application)

**Propósito**: Contiene la lógica de negocio y los casos de uso específicos de la aplicación. Aquí se implementan los servicios y las reglas de negocio.

**Componentes**:
- **FindProductPricingUseCase.java**: Caso de uso principal de la aplicación que implementa la lógica para obtener los precios de los productos según diferentes condiciones, como la fecha y la identificación del producto y la marca.

### Capa de Dominio (domain)

**Propósito**: Contiene las entidades y los contratos esenciales para la lógica de negocio. Aquí no se encuentra ningún detalle de implementación sobre cómo se realiza el almacenamiento o la interfaz con el mundo exterior.

**Componentes**:
- **exceptions**: Define excepciones personalizadas como `PriceNotFoundException.java` para manejar errores específicos de la lógica de negocio.
- **model**: Aquí se encuentra la clase `ProductPricing.java`, que representa el modelo de datos que contiene la información del precio de un producto.
- **ports**: Define los puertos de entrada y salida para interactuar con la capa de infraestructura. En este caso:
    - **ProductPricingRepositoryPort.java**: Puerto para acceder a la persistencia de los datos relacionados con el precio de los productos.
    - **ProductPricingServicePort.java**: Puerto que define los servicios necesarios para la gestión de precios de productos en la lógica de negocio.

### Capa de Infraestructura (infrastructure)

**Propósito**: Contiene las implementaciones técnicas de la infraestructura, como el acceso a bases de datos, la configuración de los controladores REST, y la manipulación de datos.

**Componentes**:
- **dto**: Contiene los DTOs (Data Transfer Objects) que se utilizan para estructurar las respuestas y errores de las APIs.
    - **ErrorResponse.java**: DTO para manejar las respuestas de error en las APIs.
    - **ProductPricingResponse.java**: DTO que estructura la respuesta que se envía al cliente con la información de los precios de productos.
- **exceptions**: Contiene el manejador global de excepciones, `GlobalExceptionHandler.java`, que maneja las excepciones globales en la aplicación.
- **input/rest**: Contiene los controladores REST que gestionan las solicitudes HTTP.
    - **ProductPricingController.java**: Controlador que expone la API para consultar los precios de los productos y maneja las solicitudes entrantes.
- **mapper**: Contiene la lógica para convertir las entidades de dominio en DTOs y viceversa.
    - **ProductPricingMapper.java**: Mapper que convierte entre las entidades de dominio, DTOs y entidades de base de datos. Los métodos en este mapper se refactorizan para hacerlos más claros y comprensibles:
        - `toDomainFromDto(ProductPricingResponse dto)`: Convierte un DTO (`ProductPricingResponse`) a la entidad de dominio (`ProductPricing`).
        - `toDtoFromDomain(ProductPricing domain)`: Convierte una entidad de dominio (`ProductPricing`) a un DTO (`ProductPricingResponse`).
        - `toDomainFromEntity(ProductPricingEntity entity)`: Convierte una entidad de base de datos (`ProductPricingEntity`) a la entidad de dominio (`ProductPricing`).
        - `toEntityFromDomain(ProductPricing domain)`: Convierte una entidad de dominio (`ProductPricing`) a una entidad de base de datos (`ProductPricingEntity`).
- **output/persistence**: Implementa los adaptadores para interactuar con la base de datos o cualquier otra fuente de datos.
    - **ProductPricingEntity.java**: Representación de los precios de productos en la base de datos utilizando JPA.
    - **ProductPricingJpaRepository.java**: Repositorio de Spring Data JPA para interactuar con la base de datos.
    - **ProductPricingRepositoryAdapter.java**: Adaptador que implementa el puerto de repositorio, usando el repositorio JPA para acceder a los datos.

### Capa de Configuración

**Propósito**: Define la configuración de la aplicación, como las conexiones a la base de datos y otros servicios externos.

**Componentes**:
- **TechnicalApplication.java**: La clase principal que configura y arranca la aplicación Spring Boot.
- **application.yml**: Archivo de configuración para la aplicación (por ejemplo, configuración de base de datos, puertos, perfiles de Spring, etc.).

### Capa de Pruebas (test)

**Propósito**: Contiene las pruebas unitarias e integración de cada componente del sistema.

**Componentes**:
- **application/usecase**: Contiene pruebas de la lógica de negocio.
    - **FindProductPricingUseCaseTest.java**: Prueba unitaria del caso de uso de precios de productos.
- **infrastructure/input/rest**: Contiene pruebas de integración para los controladores REST.
    - **ProductPricingControllerTest.java**: Pruebas de integración del controlador REST que maneja las solicitudes relacionadas con los precios de los productos.
- **infrastructure/mapper**: Contiene pruebas para los mappers de entidades y DTOs.
    - **ProductPricingMapperTest.java**: Pruebas unitarias para el mapper que convierte entre las entidades y los DTOs.
- **infrastructure/output/persistence**: Contiene pruebas de integración para los repositorios y adaptadores de persistencia.
    - **ProductPricingRepositoryAdapterTest.java**: Pruebas de integración que verifican la correcta interacción con la base de datos usando el repositorio JPA.

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
├── README.md                    # Documentación general del proyecto
├── mvnw                         # Wrapper de Maven para ejecutar el proyecto
├── mvnw.cmd                     # Script de Windows para ejecutar el wrapper de Maven
├── pom.xml                      # Archivo de configuración de Maven, contiene las dependencias y configuraciones del proyecto
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── inditex
│   │   │           └── technical
│   │   │               ├── application
│   │   │               │   └── usecase
│   │   │               │       └── FindProductPricingUseCase.java     # Caso de uso que implementa la lógica de negocio para encontrar los precios de productos
│   │   │               ├── domain
│   │   │               │   ├── exceptions
│   │   │               │   │   └── PriceNotFoundException.java        # Excepción personalizada para el caso en que no se encuentren precios de productos
│   │   │               │   ├── model
│   │   │               │   │   └── ProductPricing.java                # Modelo de datos que representa el precio de un producto
│   │   │               │   └── ports
│   │   │               │       ├── ProductPricingRepositoryPort.java  # Puerto para la interacción con la persistencia de datos relacionados con el precio de productos
│   │   │               │       └── ProductPricingServicePort.java     # Puerto para la lógica de negocio que maneja los precios de productos
│   │   │               ├── infrastructure
│   │   │               │   ├── dto
│   │   │               │   │   ├── ErrorResponse.java                 # DTO para estructurar la respuesta de error de la API
│   │   │               │   │   └── ProductPricingResponse.java        # DTO que contiene la respuesta de precio de un producto
│   │   │               │   ├── exceptions
│   │   │               │   │   └── GlobalExceptionHandler.java        # Manejador global de excepciones para manejar errores de la aplicación
│   │   │               │   ├── input
│   │   │               │   │   └── rest
│   │   │               │   │       └── ProductPricingController.java  # Controlador REST que maneja las solicitudes relacionadas con los precios de productos
│   │   │               │   ├── mapper
│   │   │               │   │   └── ProductPricingMapper.java          # Mapper para convertir entre las entidades de dominio y los DTOs de la API
│   │   │               │   └── output
│   │   │               │       └── persistence
│   │   │               │           ├── ProductPricingEntity.java     # Entidad JPA que representa los precios de productos en la base de datos
│   │   │               │           ├── ProductPricingJpaRepository.java # Repositorio de Spring Data JPA para acceder a la base de datos de precios de productos
│   │   │               │           └── ProductPricingRepositoryAdapter.java # Adaptador que implementa el puerto de repositorio para interactuar con el repositorio JPA
│   │   │               └── TechnicalApplication.java                   # Clase principal de la aplicación que configura y ejecuta la aplicación Spring Boot
│   │   └── resources
│   │       ├── application.yml     # Archivo de configuración de Spring Boot (por ejemplo, puertos, configuración de base de datos, etc.)
│   │       ├── data.sql            # Script SQL con datos iniciales para la base de datos
│   │       ├── static              # Archivos estáticos (por ejemplo, imágenes, CSS, JS)
│   │       └── templates           # Plantillas (si se usa Thymeleaf o similar)
│   └── test
│       └── java
│           └── com
│               └── inditex
│                   └── technical
│                       ├── application
│                       │   └── usecase
│                       │       └── FindProductPricingUseCaseTest.java # Pruebas unitarias para el caso de uso de precios de productos (pruebas de lógica de negocio)
│                       ├── infrastructure
│                       │   ├── input
│                       │   │   └── rest
│                       │   │       └── ProductPricingControllerTest.java # Pruebas de integración para el controlador REST que maneja las solicitudes de precios de productos
│                       │   ├── mapper
│                       │   │   └── ProductPricingMapperTest.java       # Pruebas unitarias para el mapper de precios de productos
│                       │   └── output
│                       │       └── persistence
│                       │           └── ProductPricingRepositoryAdapterTest.java # Pruebas de integración para el adaptador de repositorio de precios de productos
│                       └── TechnicalApplicationTests.java               # Pruebas unitarias generales de la aplicación



# 🎽 T-Shirt Store - Spring Boot Application

Una aplicación completa de comercio electrónico para tiendas de camisetas personalizadas, implementada con Spring Boot y patrones de diseño avanzados.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Patrones de Diseño Implementados](#patrones-de-diseño-implementados)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [API REST](#api-rest)
- [Testing](#testing)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Contribuir](#contribuir)

## ✨ Características

- **Gestión de Camisetas**: CRUD completo para productos de camisetas
- **Personalización**: Sistema de personalización con diferentes categorías (Casual, Sport, Premium)
- **Gestión de Órdenes**: Sistema completo de órdenes de compra
- **Procesamiento de Pagos**: Implementación de servicio de pagos con patrón Singleton
- **Validación**: Validación completa de datos con mensajes en español
- **Documentación API**: Documentación automática con Swagger/OpenAPI
- **Manejo de Excepciones**: Manejo centralizado de errores
- **Perfiles de Configuración**: Desarrollo (H2) y Producción (MySQL)

## 🏗️ Patrones de Diseño Implementados

### 1. Builder Pattern
**Ubicación**: `com.project.tshirts.builder.TShirt`

El patrón Builder se utiliza para crear objetos `TShirt` de forma inmutable y flexible:

```java
TShirt tshirt = TShirt.builder()
    .size("M")
    .color("Azul")
    .print("Logo Java")
    .fabric("Algodón")
    .price(29.99)
    .sku("TSH-001-M-AZUL")
    .category("CASUAL")
    .build();
```

**Ventajas**:
- Inmutabilidad del objeto TShirt
- Código más legible y mantenible
- Validación de campos requeridos
- Flexibilidad para crear diferentes variaciones

### 2. Abstract Factory Pattern
**Ubicación**: `com.project.tshirts.factory.*`

Implementación de fábricas abstractas para crear diferentes tipos de camisetas:

- **TShirtFactory**: Interfaz base
- **CasualTShirtFactory**: Crea camisetas casuales
- **SportTShirtFactory**: Crea camisetas deportivas
- **PremiumTShirtFactory**: Crea camisetas premium

```java
TShirtFactory factory = new SportTShirtFactory();
TShirtEntity sportTShirt = factory.createTShirt(dto);
```

**Ventajas**:
- Extensibilidad para nuevos tipos de camisetas
- Lógica de negocio encapsulada por categoría
- Precios y características específicas por tipo

### 3. Singleton Pattern
**Ubicación**: `com.project.tshirts.service.impl.ClassicPaymentService`

Implementación clásica del patrón Singleton para el servicio de pagos:

```java
ClassicPaymentService paymentService = ClassicPaymentService.getInstance();
```

**Características**:
- Doble verificación de bloqueo (Double-Checked Locking)
- Thread-safe
- Única instancia en toda la aplicación

## 🛠️ Requisitos

- Java 17 o superior
- Maven 3.6+
- Spring Boot 3.2.0
- Base de datos (H2 para desarrollo, MySQL para producción)

## 📦 Instalación

1. **Clonar el repositorio**:
```bash
git clone https://github.com/tu-usuario/tshirt-store.git
cd tshirt-store
```

2. **Compilar el proyecto**:
```bash
mvn clean install
```

3. **Ejecutar la aplicación**:
```bash
mvn spring-boot:run
```

## ⚙️ Configuración

### Perfiles de Spring

#### Desarrollo (dev)
- Base de datos: H2 en memoria
- Puerto: 8080
- Swagger: Habilitado
- SQL logging: Habilitado

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

#### Producción (prod)
- Base de datos: MySQL
- Puerto: Configurable via variable de entorno PORT
- Swagger: Deshabilitado por defecto
- SQL logging: Deshabilitado

Variables de entorno requeridas:
```bash
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
export PORT=8080
export SWAGGER_ENABLED=true
```

## 🚀 Uso

### Iniciar la aplicación

```bash
# Modo desarrollo
mvn spring-boot:run

# Modo producción
mvn spring-boot:run -Dspring.profiles.active=prod
```

### Acceder a la documentación Swagger

Una vez iniciada la aplicación:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

### Consola H2 (solo desarrollo)

http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (vacío)

## 📡 API REST

### Endpoints Principales

#### Camisetas (T-Shirts)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tshirts` | Obtener todas las camisetas |
| GET | `/api/tshirts/{id}` | Obtener camiseta por ID |
| POST | `/api/tshirts` | Crear nueva camiseta |
| GET | `/api/tshirts/category/{category}` | Filtrar por categoría |
| GET | `/api/tshirts/filter` | Filtrar por múltiples criterios |

#### Órdenes (Orders)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/orders` | Obtener todas las órdenes |
| GET | `/api/orders/{id}` | Obtener orden por ID |
| POST | `/api/orders` | Crear nueva orden |
| GET | `/api/orders/customer/{email}` | Obtener órdenes por email |
| PUT | `/api/orders/{id}/status` | Actualizar estado de orden |

#### Pagos (Payments)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/payments/process` | Procesar pago |
| POST | `/api/payments/process/classic` | Procesar pago (Singleton clásico) |
| GET | `/api/payments/status/{orderId}` | Obtener estado de pago |
| POST | `/api/payments/refund` | Procesar reembolso |

### Ejemplos de uso

#### Crear una camiseta

```bash
curl -X POST http://localhost:8080/api/tshirts \
  -H "Content-Type: application/json" \
  -d '{
    "size": "M",
    "color": "Azul",
    "print": "Logo Java",
    "fabric": "Algodón",
    "category": "CASUAL"
  }'
```

#### Crear una orden

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Juan Pérez",
    "customerEmail": "juan@example.com",
    "items": [
      {
        "tshirtId": 1,
        "quantity": 2
      }
    ]
  }'
```

#### Procesar pago

```bash
curl -X POST http://localhost:8080/api/payments/process \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1,
    "amount": 59.98,
    "paymentMethod": "CREDIT_CARD",
    "paymentDetails": "1234-5678-9012-3456"
  }'
```

## 🧪 Testing

### Ejecutar tests unitarios

```bash
mvn test
```

### Tests implementados

1. **Builder Pattern Tests** (`TShirtTest.java`)
   - Creación de camisetas con Builder
   - Verificación de inmutabilidad
   - Manejo de valores nulos

2. **Factory Pattern Tests** (`TShirtFactoryTest.java`)
   - Creación de diferentes tipos de camisetas
   - Estrategias de precios por categoría
   - Generación de SKUs únicos

3. **Singleton Pattern Tests** (`ClassicPaymentServiceTest.java`)
   - Verificación de única instancia
   - Acceso concurrente
   - Mantenimiento de estado

### Cobertura de tests

```bash
mvn jacoco:report
```

Reporte generado en: `target/site/jacoco/index.html`

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/project/tshirts/
│   │   ├── builder/          # Patrón Builder
│   │   ├── config/           # Configuración de Spring
│   │   ├── controller/       # Controladores REST
│   │   ├── dto/              # Objetos de Transferencia de Datos
│   │   ├── exception/        # Manejo de excepciones
│   │   ├── factory/          # Patrón Abstract Factory
│   │   ├── model/            # Entidades JPA
│   │   ├── repository/       # Repositorios Spring Data
│   │   ├── service/          # Interfaces de servicio
│   │   └── service/impl/     # Implementaciones de servicio
│   └── resources/
│       ├── application.yml   # Configuración de la aplicación
│       └── data.sql         # Datos de prueba
└── test/
    └── java/com/project/tshirts/
        ├── builder/          # Tests del patrón Builder
        ├── factory/          # Tests del patrón Factory
        └── service/impl/     # Tests del patrón Singleton
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

### Guías de contribución

- Seguir las convenciones de código Java
- Escribir tests unitarios para nuevas funcionalidades
- Documentar APIs con Swagger/OpenAPI
- Mantener la cobertura de tests por encima del 80%

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autores

- **Tu Nombre** - *Trabajo inicial* - [TuUsuario](https://github.com/TuUsuario)

## 🙏 Agradecimientos

- Spring Boot team por el excelente framework
- La comunidad de código abierto por las librerías y herramientas
- Inspiración en proyectos de comercio electrónico existentes

---

**Nota**: Este proyecto fue creado como parte del curso de Patrones de Diseño de Software, demostrando la implementación práctica de Builder, Abstract Factory y Singleton patterns en una aplicación empresarial real.
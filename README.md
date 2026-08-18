# Chronus — Hito 3: arquitectura limpia y dominio modelado

**Chronus** es la entrega del **Hito 3** del Curso de Java de [Desafío Latam](https://desafiolatam.com/) y [Globant](https://www.globant.com/).

El proyecto implementa el backend Java puro de una aplicación de agendamiento para profesionales de la salud. Gestiona citas, datos de pacientes, pagos y recordatorios, sin depender de bases de datos, frameworks web ni servicios externos reales.

---

## Propósito y visión del negocio

En un centro de salud, coordinar horarios, mantener los datos de contacto de los pacientes y confirmar pagos requiere reglas claras para evitar errores de agenda y comunicaciones incompletas.

**Chronus** centraliza esas reglas en clases Java puras. El sistema valida que las citas sean futuras y que sus identificadores no se repitan, modela los datos de contacto con value objects, acepta únicamente pagos enteros positivos y prepara recordatorios por email y WhatsApp.

---

## Criterios evaluables del Hito 3

La entrega se revisa contra los tres criterios de la rúbrica compartida.

### 1. Arquitectura de carpetas y capas limpias

El código está separado en tres capas:

- `domain`: entidades, value objects, excepciones, servicios de dominio y contratos de repositorio.
- `application`: casos de uso y servicios de notificación.
- `infrastructure`: adaptadores de persistencia y notificación.

El dominio no depende de aplicación ni infraestructura. El dominio y la aplicación tampoco importan Spring, JPA, Jackson u otros frameworks externos.

### 2. Implementación de patrones tácticos

El dominio utiliza entidades con identidad explícita y ciclo de actualización, además de value objects inmutables implementados como `record`:

- `PatientId`
- `FullName`
- `Email`
- `PhoneNumber`
- `AppointmentId`
- `AppointmentDateTime`
- `PaymentId`
- `PaymentAmount`

Cada value object valida sus reglas en el constructor compacto. Las entidades mantienen sus datos tipados con value objects, crean sus identificadores y nombres mediante esos objetos al instanciarse, y exponen su estado mediante getters descriptivos.

### 3. Desacoplamiento por contratos de repositorios

`AppointmentRepository`, `PatientRepository` y `PaymentRepository` viven en `domain.repository` y funcionan como contratos de persistencia. Sus implementaciones en memoria viven en `infrastructure.persistence`. Los casos de uso reciben repositorios y servicios mediante inyección por constructor.

---

## Reglas de negocio

El núcleo de la aplicación se orquesta desde `CreatePatientUseCase`, `CreateAppointmentUseCase`, `AcceptPaymentUseCase` y `SendAppointmentReminderUseCase`.

1. **Fecha de cita válida:** una cita debe estar estrictamente en el futuro. Una fecha pasada o igual al momento actual genera `InvalidDateAppointmentException`.
2. **Identidad de cita:** no se pueden registrar dos citas con el mismo identificador. La segunda solicitud genera `RuntimeException`.
3. **Datos del paciente:** el nombre, el email y el teléfono se modelan con value objects auto-validados (`FullName`, `Email` y `PhoneNumber`), que rechazan valores nulos, vacíos o con formato inválido.
4. **Pago válido:** el monto debe ser un número entero estrictamente positivo. Montos negativos, cero o fraccionarios generan `InvalidPaymentException`.
5. **Recordatorios:** una cita puede enviar un recordatorio al email y WhatsApp registrados para el paciente.

---

## Decisiones de diseño y arquitectura

- **Tres capas:** `domain` (modelo y contratos), `application` (casos de uso) e `infrastructure` (adaptadores). El dominio no importa aplicación ni infraestructura.
- **Java puro en el núcleo:** `domain` y `application` no usan Spring, JPA ni Jackson. No hay `@Service`, `@Repository` ni `@Entity` de framework.
- **Casos de uso concretos:** cada acción de negocio es una clase en `application.usecase` que recibe sus dependencias mediante el constructor y expone `execute(...)`.
- **Repositorios como frontera:** las interfaces viven en `domain.repository`. Las listas en memoria están en `infrastructure.persistence`, incluyendo la creación y consulta de pacientes.
- **Inyección por constructor:** los casos de uso reciben repositorios y servicios de notificación, nunca las clases concretas.
- **Doubles de prueba:** la suite usa mocks de Mockito para los casos de uso y repositorios en memoria para las pruebas de persistencia.
- **Regla de dependencia:** `ArchitectureTest` (ArchUnit) falla el build si dominio o aplicación se acopla a infraestructura o a un framework.

---

## Requisitos

- JDK 17 o superior.
- Maven 3.8 o superior.

El bytecode se compila mediante `maven.compiler.release` para Java 17.

---

## Instrucciones de ejecución y pruebas

Ejecuta los siguientes comandos desde la raíz del proyecto.

### 1. Ejecutar la suite automatizada

```bash
mvn clean test
```

Ejecuta las pruebas JUnit 5, los dobles de Mockito y el reporte de cobertura en consola.

### 2. Verificar la cobertura obligatoria

```bash
mvn clean verify
```

Ejecuta la suite, genera el reporte JaCoCo y valida los mínimos configurados de cobertura.

### 3. Generar solo el reporte JaCoCo

Después de ejecutar los tests:

```bash
mvn jacoco:report
```

---

## Reporte de cobertura

El reporte visual de JaCoCo está disponible en:

[Abrir reporte JaCoCo](https://prgm-code.github.io/Hito1-Java-DL/)

También se puede regenerar localmente con `mvn clean verify`; el archivo se encuentra en:

```text
target/site/jacoco/index.html
```

---

## Estructura del proyecto

```text
chronus/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/chronus/
    │   ├── application/
    │   │   ├── service/
    │   │   │   ├── EmailNotifier.java
    │   │   │   └── WhatsAppNotifier.java
    │   │   └── usecase/
    │   │       ├── AcceptPaymentUseCase.java
    │   │       ├── CreateAppointmentUseCase.java
    │   │       ├── CreatePatientUseCase.java
    │   │       └── SendAppointmentReminderUseCase.java
    │   ├── domain/
    │   │   ├── entity/
    │   │   │   ├── Appointment.java
    │   │   │   ├── Patient.java
    │   │   │   └── Payment.java
    │   │   ├── exception/
    │   │   │   ├── InvalidDateAppointmentException.java
    │   │   │   ├── InvalidEmailException.java
    │   │   │   ├── InvalidPatientDataException.java
    │   │   │   ├── InvalidPaymentException.java
    │   │   │   └── InvalidPhoneNumberException.java
    │   │   ├── repository/
    │   │   │   ├── AppointmentRepository.java
    │   │   │   ├── PatientRepository.java
    │   │   │   └── PaymentRepository.java
    │   │   ├── service/
    │   │   │   └── AppointmentConflictChecker.java
    │   │   └── valueobject/
    │   │       ├── AppointmentDateTime.java
    │   │       ├── AppointmentId.java
    │   │       ├── Email.java
    │   │       ├── FullName.java
    │   │       ├── PatientId.java
    │   │       ├── PaymentAmount.java
    │   │       ├── PaymentId.java
    │   │       └── PhoneNumber.java
    │   └── infrastructure/
    │       ├── notification/
    │       │   ├── NoOpEmailNotifier.java
    │       │   └── NoOpWhatsAppNotifier.java
    │       └── persistence/
    │           ├── InMemoryAppointmentRepository.java
    │           ├── InMemoryPatientRepository.java
    │           └── InMemoryPaymentRepository.java
    └── test/java/com/chronus/
        ├── ArchitectureTest.java
        ├── application/usecase/
        │   ├── AcceptPaymentUseCaseTest.java
        │   ├── CreateAppointmentUseCaseTest.java
        │   ├── CreatePatientUseCaseTest.java
        │   └── SendAppointmentReminderUseCaseTest.java
        ├── domain/
        │   ├── entity/
        │   │   ├── AppointmentTest.java
        │   │   ├── PatientTest.java
        │   │   └── PaymentTest.java
        │   ├── service/
        │   │   └── AppointmentConflictCheckerTest.java
        │   └── valueobject/
        │       ├── AppointmentDateTimeTest.java
        │       ├── AppointmentIdTest.java
        │       ├── EmailTest.java
        │       ├── FullNameTest.java
        │       ├── PatientIdTest.java
        │       ├── PaymentAmountTest.java
        │       ├── PaymentIdTest.java
        │       └── PhoneNumberTest.java
        └── infrastructure/
            ├── notification/
            │   ├── NoOpEmailNotifierTest.java
            │   └── NoOpWhatsAppNotifierTest.java
            └── persistence/
                ├── InMemoryAppointmentRepositoryTest.java
                ├── InMemoryPatientRepositoryTest.java
                └── InMemoryPaymentRepositoryTest.java
```

---

## Tecnologías

- Java 17
- Maven
- JUnit 5
- Mockito
- ArchUnit
- JaCoCo

# Chronus — Sistema de Gestión de Citas Médicas

**Chronus** es el **Hito 1** del Curso de Java de [Desafío Latam](https://desafiolatam.com/) y [Globant](https://www.globant.com/).

El proyecto implementa el núcleo de dominio de una aplicación de agendamiento para profesionales de la salud. Gestiona citas, datos de pacientes, pagos y recordatorios, sin depender de bases de datos, frameworks web ni servicios externos reales.

---

## Propósito y visión del negocio

En un centro de salud, coordinar horarios, mantener los datos de contacto de los pacientes y confirmar pagos requiere reglas claras para evitar errores de agenda y comunicaciones incompletas.

**Chronus** centraliza esas reglas en clases Java puras. El sistema valida que las citas sean futuras y no colisionen, exige información de contacto para cada paciente, acepta únicamente pagos enteros positivos y prepara recordatorios por email y WhatsApp.

---

## Reglas de negocio

El núcleo de la aplicación se distribuye entre `AppointmentService`, `PaymentService` y `AppointmentReminder`.

1. **Fecha de cita válida:** una cita debe estar estrictamente en el futuro. Una fecha pasada o igual al momento actual genera `InvalidDateAppointmentException`.
2. **Sin colisiones:** no se pueden registrar dos citas en la misma fecha y hora. La segunda solicitud genera `OccupiedAppointmentException`.
3. **Datos del paciente:** el nombre completo, email y teléfono son obligatorios para crear un `Patient`. La ausencia de alguno genera `InvalidPatientDataException`.
4. **Pago válido:** el monto debe ser un número entero estrictamente positivo. Montos negativos, cero o fraccionarios generan `InvalidPaymentException`.
5. **Recordatorios:** una cita puede enviar un recordatorio al email y WhatsApp registrados para el paciente.

---

## Decisiones de diseño y arquitectura

- **Dominio puro:** las entidades `Appointment`, `Patient` y `Payment` usan únicamente Java estándar y no tienen anotaciones de Spring, JPA ni dependencias web.
- **Reglas centralizadas:** los servicios de dominio contienen las validaciones, persistencia en memoria y preparación de notificaciones.
- **Inyección por constructor:** los repositorios y notificadores se entregan a los servicios mediante sus constructores, facilitando el reemplazo por dobles de prueba.
- **Doubles de prueba:** la suite combina mocks de Mockito, dummies y repositorios en memoria según el escenario que se evalúa.
- **Excepciones propias:** las reglas inválidas se representan mediante excepciones específicas, sin mezclar la lógica con valores de error genéricos.

---

## Estrategia de pruebas y cobertura

Las pruebas automatizadas siguen el patrón **Arrange – Act – Assert (AAA)** y cubren escenarios exitosos, valores límite y excepciones de negocio.

| Área | Casos cubiertos |
| --- | --- |
| Citas | Creación válida, fecha pasada y colisión de horario |
| Pacientes | Datos de contacto válidos, nombre ausente, email vacío y teléfono ausente |
| Pagos | Pago válido, negativo, cero y fraccionario |
| Recordatorios | Envío por email y WhatsApp con los datos de `Juanito Pérez` |
| Colaboradores | Repositorios, notificador por email, notificador por WhatsApp, dummies y mocks |

La última ejecución de la suite valida **24 pruebas** y el reporte JaCoCo del paquete de dominio marca:

- **100% Class Coverage** — 14/14 clases.
- **100% Method Coverage** — 30/30 métodos.
- **100% Branch Coverage** — 12/12 ramas.
- **100% Line Coverage** — 77/77 líneas.

Además, `jacoco:check` hace fallar el build si la cobertura de líneas o ramas baja de 100%.

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

El reporte HTML generado se incluye en este repositorio. Después de clonarlo, se puede abrir directamente:

[Abrir reporte JaCoCo](target/site/jacoco/index.html)

También se puede regenerar con `mvn clean verify`; el archivo se encuentra en:

```text
target/site/jacoco/index.html
```

![Reporte JaCoCo con 100% de cobertura](image.png)

El mismo resumen se imprime automáticamente en la terminal mediante `jacoco-console-reporter`.

![Resumen de cobertura en consola](jacoco-console.png)

---

## Estructura del proyecto

```text
chronus/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/chronus/domain/
│   │           ├── exception/
│   │           │   ├── InvalidDateAppointmentException.java
│   │           │   ├── InvalidPatientDataException.java
│   │           │   ├── InvalidPaymentException.java
│   │           │   └── OccupiedAppointmentException.java
│   │           ├── Appointment.java
│   │           ├── AppointmentReminder.java
│   │           ├── AppointmentRepository.java
│   │           ├── AppointmentService.java
│   │           ├── EmailNotifier.java
│   │           ├── Patient.java
│   │           ├── Payment.java
│   │           ├── PaymentRepository.java
│   │           ├── PaymentService.java
│   │           └── WhatsAppNotifier.java
│   └── test/
│       └── java/
│           └── com/chronus/domain/
│               ├── AppointmentReminderTest.java
│               ├── AppointmentRepositoryTest.java
│               ├── AppointmentServiceTest.java
│               ├── EmailNotifierTest.java
│               ├── PatientTest.java
│               ├── PaymentRepositoryTest.java
│               ├── PaymentServiceDummyTest.java
│               ├── PaymentServiceMockitoTest.java
│               ├── PaymentServiceTest.java
│               └── WhatsAppNotifierTest.java
├── pom.xml
└── README.md
```

---

## Tecnologías

- Java 17
- Maven
- JUnit 5
- Mockito
- JaCoCo

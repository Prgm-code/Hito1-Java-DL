# Chronus

**Chronus** es el **Hito 1** del Curso de Java de [Desafío Latam](https://desafiolatam.com/) y [Globant](https://www.globant.com/).

Es el core de dominio de un sistema de agendamiento para profesionales de la salud. Este primer hito permite agendar, consultar disponibilidad y cancelar turnos sin depender de una base de datos, un framework web ni servicios reales de mensajería.

## Hito 1

El entregable formal consiste en la publicación de un repositorio estructurado en GitHub que cumpla con los siguientes tres requerimientos:

1. **Core de Entidades de Dominio Puro (3 Puntos):** Estructura base del modelo de negocio de su temática autónoma escrita en Java puro, libre de acoplamientos a frameworks o bases de datos físicas. Las clases deben poseer nombres consistentes alineados a su glosario técnico interno.

2. **Suite Automatizada con JUnit 5 y Mockito (3 Puntos):** Enfoque exhaustivo de casos de prueba estructurados rigurosamente bajo el Patrón AAA (Arrange, Act, Assert). El sistema debe controlar de forma limpia las excepciones de negocio personalizadas mediante `assertThrows` e interceptar las dependencias utilizando dobles de prueba e inyección por constructor.

3. **Cobertura Matemática del 100% en Métodos Críticos (4 Puntos):** El repositorio de código debe respaldar, mediante la suite automatizada, una cobertura lógica verificable del 100% (Branch/Line Coverage) en todos sus métodos y flujos de negocio centrales. No se aceptarán líneas de lógica condicional desprotegidas.

## Desarrollo dirigido por pruebas

El servicio fue construido con el ciclo TDD **Red → Green → Refactor**. La suite utiliza el patrón AAA (`Arrange`, `Act`, `Assert`) y cubre tanto los caminos exitosos como las excepciones y los valores límite.

Los escenarios incluyen:

- Fechas pasadas y exactamente iguales al reloj.
- Horarios anteriores, posteriores o no alineados con los slots permitidos.
- Colisiones de agenda.
- Guardado y notificación de un turno válido.
- Disponibilidad futura, del día actual y de días pasados.
- Turnos confirmados, pendientes y cancelados al calcular disponibilidad.
- Cancelación tardía, válida, repetida y de un ID inexistente.
- El límite exacto de 24 horas.

## Requisitos

- JDK 17 o superior.
- Maven 3.8 o superior.

El bytecode se compila para Java 17 mediante `maven.compiler.release`.

## Ejecutar las pruebas

Desde la raíz del proyecto:

```bash
mvn clean test
```

## Verificar cobertura

El siguiente comando ejecuta la suite, genera el reporte y hace fallar el build si el dominio baja de 100% de líneas o ramas:

```bash
mvn clean verify
```

También se puede regenerar únicamente el reporte después de ejecutar los tests:

```bash
mvn test jacoco:report
```

## Evidencia JaCoCo

Después de ejecutar `mvn clean verify`, abrir:

```text
target/site/jacoco/index.html
```

La configuración exige matemáticamente:

- 100% Line Coverage.
- 100% Branch Coverage.

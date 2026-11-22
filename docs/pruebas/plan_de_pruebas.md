# Plan de Pruebas — BiblioUni

## 1. Identificación del plan
- **Nombre del plan:** Plan de Pruebas Formales y Unitarias — BiblioUni
- **Versión:** 1.0
- **Responsable:** Kevin Perez Rojas

## 2. Alcance
- **Funcionalidades que se probarán:**
  - Gestión de materiales bibliográficos (`MaterialService`: creación de materiales, búsqueda por ID existente e inexistente y validación de disponibilidad).
  - Gestión de préstamos (`PrestamoService`: registro de préstamo exitoso, rechazo por material no disponible, validación de límite máximo de préstamos activos por usuario y cálculo de multa por devolución tardía).
  - Validaciones de usuario (`UsuarioService`: validación de formato de correo electrónico).
- **Funcionalidades que no se probarán:**
  - Pruebas de rendimiento, carga y estrés de alta concurrencia.
  - Pruebas de penetración o seguridad avanzada de infraestructura.
  - Interfaz gráfica o flujos externos fuera de los servicios de negocio y endpoints definidos.

## 3. Enfoque de pruebas
- **Pruebas unitarias:** Validación aislada de la lógica de negocio en la capa de servicios (`MaterialService`, `PrestamoService`, `UsuarioService`) utilizando **JUnit 5** y **Mockito** (`@Mock`, `@InjectMocks`) siguiendo el patrón AAA (Arrange, Act, Assert).
- **Pruebas de integración:** Verificación de la interacción entre servicios, repositorios y base de datos en memoria (**H2**).
- **Pruebas de API:** Validación manual y automatizada de los endpoints REST expuestos mediante **Swagger** y **Postman**.

## 4. Criterios de entrada
- El código fuente del proyecto compila sin errores mediante Maven (`mvn test-compile`).
- El pipeline de integración continua (CI) se encuentra en verde.
- Las dependencias de pruebas (`JUnit 5`, `Mockito`, `H2`, `JaCoCo`) están configuradas en el `pom.xml`.
- Los endpoints base están operativos y documentados en Swagger.

## 5. Criterios de salida
- 100% de los casos de prueba unitarios diseñados ejecutados y aprobados (`mvn test`).
- Cobertura de código en la capa de servicios superior al 60% medida con JaCoCo.
- 0 defectos críticos o bloqueantes pendientes en las funcionalidades dentro del alcance.

## 6. Recursos
- **Herramientas:** JUnit 5, Mockito, Maven, JaCoCo, Postman, Swagger, Git / GitHub, Visual Studio Code.
- **Entorno:** Base de datos en memoria H2 para entorno de pruebas y objetos simulados (mocks) para pruebas unitarias aisladas.
- **Responsable:** Kevin Perez Rojas.

## 7. Riesgos y contingencias
- **Riesgo 1 — Dependencia directa de base de datos en tests unitarios:** Que las pruebas fallen por estado previo en la base de datos.
  - *Contingencia:* Aislar completamente la capa de servicio mockeando los repositorios con Mockito (`@ExtendWith(MockitoExtension.class)`).
- **Riesgo 2 — Ambigüedad en reglas de negocio (límite de préstamos o cálculo de multas):** Interpretaciones distintas en los valores extremos.
  - *Contingencia:* Definir valores límite exactos en los casos de prueba formales antes de implementar el código.
- **Riesgo 3 — Pérdida de trazabilidad en los ciclos TDD:** Mezclar cambios de prueba e implementación en un mismo commit.
  - *Contingencia:* Realizar commits separados y etiquetados estrictamente como `test(red)`, `feat(green)` y `refactor`.
# Estrategia de Pruebas - Proyecto BibliUni

## 1. Introducción
El presente documento define la estrategia de pruebas para el sistema **BibliUni**, una aplicación de gestión bibliotecaria basada en una API REST. El propósito de este plan es garantizar la calidad del software mediante la aplicación de buenas prácticas alineadas con los fundamentos del **ISTQB** y los criterios de calidad de la norma **ISO/IEC 25010**.

## 2. Objetivos de Calidad (Basado en ISO/IEC 25010)
Para asegurar que el sistema BibliUni cumpla con altos estándares de calidad, nos enfocaremos en las siguientes características:
* **Fiabilidad (Reliability):** Asegurar que las operaciones críticas (como el registro de préstamos y la gestión de stock de libros) se ejecuten sin fallos inesperados.
* **Adecuación Funcional:** Verificar que cada endpoint de la API cumpla estrictamente con los requerimientos del negocio (libros, usuarios y préstamos).
* **Mantenibilidad:** Mantener un código limpio y modular que facilite la corrección de defectos y futuras ampliaciones.

## 3. Niveles y Tipos de Pruebas (ISTQB)

### 3.1. Pruebas Unitarias (Component Testing)
* **Objetivo:** Validar de forma aislada los componentes individuales del sistema (métodos de servicio, validadores de negocio y lógica de modelos).
* **Herramienta sugerida:** Frameworks de pruebas nativos (como JUnit para Java o PyTest para Python).
* **Alcance:** Pruebas sobre la lógica de cálculo de fechas de devolución y validación de disponibilidad de stock.

### 3.2. Pruebas de Integración (Integration Testing)
* **Objetivo:** Verificar la comunicación correcta entre los diferentes módulos del sistema, en especial la interacción entre los **Controladores REST**, los **Servicios** y la **Base de Datos (SQLite)**.
* **Alcance:** Comprobar que las consultas SQL y la persistencia de datos funcionen de manera integrada al realizar peticiones a la API.

### 3.3. Pruebas de Sistema / Funcionales de API (System Testing)
* **Objetivo:** Evaluar el comportamiento global del sistema frente a los requerimientos funcionales mediante la ejecución de peticiones HTTP a los endpoints.
* **Casos principales a evaluar:**
  * `GET /api/libros`: Consulta exitosa del catálogo de libros.
  * `POST /api/prestamos`: Registro correcto de un préstamo de libro y actualización de stock.
  * Validación de respuestas ante errores comunes (ej. intentar prestar un libro sin stock o consultar un usuario que no existe, esperando códigos HTTP adecuados como `400` o `404`).

## 4. Gestión de Defectos y Criterios de Salida
* **Defecto vs. Fallo:** Se registrará como defecto cualquier discrepancia encontrada entre el resultado esperado y el obtenido durante la ejecución de las pruebas. Si el sistema llega a interrumpir su ejecución de forma anómala ante una petición del usuario, se clasificará como fallo crítico.
* **Criterios de salida:** La fase de pruebas se considerará exitosa cuando se haya ejecutado el 100% de los casos de prueba planeados para las funcionalidades principales de libros, usuarios y préstamos, y no existan defectos abiertos de tipo crítico o bloqueante.
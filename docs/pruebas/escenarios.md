# Escenarios de Prueba Narrativos - Proyecto BibliUni

## 1. Listado de Escenarios de Prueba

| ID | Componente Bajo Prueba | Descripción del Escenario (Lenguaje Natural) | Tipo de Prueba | Prioridad |
| :--- | :--- | :--- | :--- | :--- |
| **EP-001** | `LibroController` | Verificar que se pueda consultar exitosamente el catálogo completo de libros mediante el endpoint `GET /api/libros`. | API | Alta |
| **EP-002** | `LibroService` | Comprobar que un libro se registre correctamente con todos sus datos obligatorios (título, autor, ISBN y stock inicial). | Unitaria | Alta |
| **EP-003** | `PrestamoService` | Validar que un estudiante pueda registrar un préstamo de un libro disponible de forma exitosa. | Integración | Alta |
| **EP-004** | `PrestamoController` | **[Camino Triste]** Verificar que la API rechace el préstamo de un libro con stock en cero, retornando un código `400 Bad Request`. | API | Alta |
| **EP-005** | `UsuarioService` | **[Camino Triste]** Comprobar que el sistema impida registrar un préstamo si el ID del usuario consultado no existe en la base de datos. | Unitaria | Alta |
| **EP-006** | `PrestamoService` | Verificar la regla de negocio que impide a un estudiante tener más de 3 préstamos activos simultáneamente. | Unitaria | Media |
| **EP-007** | `LibroRepository` | Comprobar la correcta persistencia y actualización del stock de un libro en la base de datos SQLite tras realizar una devolución. | Integración | Alta |
| **EP-008** | `PrestamoController` | **[Gancho Innovador]** Validar el funcionamiento del sistema de notificaciones automáticas o extensión de plazo digital para préstamos próximos a vencer. | API | Media |
| **EP-009** | `ReporteService` | **[Gancho Innovador]** Verificar la generación de un reporte analítico de libros más solicitados por los estudiantes en formato JSON. | Integración | Baja |
| **EP-010** | `UsuarioController` | **[Camino Triste]** Validar que la API maneje correctamente datos de entrada inválidos (ej. campos vacíos o fechas con formato erróneo) al registrar un usuario. | API | Media |

---

## 2. Detalle de Escenarios Críticos y de Camino Triste

* **Control de Stock y Disponibilidad (EP-004):** Garantiza que las reglas de negocio impidan sobregiros en el inventario físico de la biblioteca.
* **Integridad de Usuarios (EP-005):** Asegura que no se puedan asociar transacciones a entidades fantasmas o no registradas.
* **Innovación en el Servicio (EP-008 y EP-009):** Incorporan valores agregados a la API como analíticas y alertas automatizadas para mejorar la experiencia del usuario.
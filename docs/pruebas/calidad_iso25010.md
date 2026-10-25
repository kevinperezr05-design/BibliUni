# Evaluación de Características de Calidad - ISO/IEC 25010 (BibliUni)

## 1. Tabla de Evaluación de las 8 Características de Calidad

| Característica ISO/IEC 25010 | Relevancia para BibliUni | Justificación | Ejemplo de Medición en BibliUni |
| :--- | :--- | :--- | :--- |
| **1. Adecuación / Funcionalidad** | **Alta** | El sistema debe cumplir con los requerimientos esenciales de gestión de libros, usuarios y préstamos de la biblioteca. | Verificar que el endpoint `POST /api/prestamos` registre correctamente el préstamo y descuente el stock disponible del libro. |
| **2. Eficiencia de Rendimiento** | **Media** | Aunque es un entorno académico, la API debe responder a tiempo razonable ante consultas concurrentes de los usuarios. | Medir el tiempo de respuesta del endpoint `GET /api/libros` bajo una carga de 50 peticiones simultáneas (esperando < 500ms). |
| **3. Compatibilidad** | **Media** | La API REST debe ser accesible e interoperable desde diferentes clientes o navegadores web sin inconvenientes. | Comprobar que las respuestas JSON sean interpretadas correctamente tanto por Postman como por un frontend web genérico. |
| **4. Usabilidad** | **Media** | Los mensajes devueltos por la API (códigos de estado y descripciones de error) deben ser claros para quien los consuma. | Validar que al intentar prestar un libro agotado, la API retorne un código `400 Bad Request` acompañado de un mensaje claro ("Libro sin stock"). |
| **5. Fiabilidad (Reliability)** | **Alta** | El sistema no debe fallar de forma inesperada ante operaciones críticas de datos y debe mantener la consistencia en SQLite. | Ejecutar pruebas de estrés para asegurar que no ocurran pérdidas de datos ni bloqueos al registrar transacciones consecutivas. |
| **6. Seguridad** | **Alta** | Los datos de los usuarios y el control de acceso a los préstamos deben estar protegidos frente a accesos indebidos. | Comprobar que no se puedan realizar modificaciones o consultas administrativas sin una validación previa de credenciales o roles. |
| **7. Mantenibilidad** | **Alta** | Facilita la corrección de errores y futuras ampliaciones del software gracias a una arquitectura modular y código limpio. | Evaluar la complejidad ciclomática del código fuente y asegurar que los servicios y controladores estén desacoplados. |
| **8. Portabilidad** | **Baja** | Al ejecutarse principalmente en un entorno local de desarrollo o contenedores, no requiere múltiples adaptaciones complejas de hardware. | Verificar que el proyecto pueda desplegarse y ejecutarse sin modificaciones en diferentes sistemas operativos (Windows, Linux, macOS) mediante Git y Python/Java. |

---

## 2. Las 3 Características Más Críticas y su Abordaje en las Pruebas

Para el proyecto **BibliUni**, las tres características más determinantes para el éxito del software son:

1. **Adecuación Funcional:**
   * **Por qué es crítica:** Es la razón de ser de la aplicación. Si los libros no se registran bien o los préstamos fallan en la lógica de negocio, el sistema pierde su utilidad principal.
   * **Cómo se abordará en las pruebas:** Mediante pruebas funcionales de caja negra y pruebas de sistema a través de los endpoints, validando entradas, salidas y reglas de negocio con casos de prueba exhaustivos.

2. **Fiabilidad:**
   * **Por qué es crítica:** Una biblioteca maneja registros transaccionales (inventario de libros y estado de usuarios). Un fallo de integridad corrompería la base de datos local.
   * **Cómo se abordará en las pruebas:** Mediante pruebas de integración y transaccionales conectadas a SQLite, verificando que las operaciones críticas realicen `rollback` o manejen excepciones de manera robusta ante fallos simulados.

3. **Mantenibilidad:**
   * **Por qué es crítica:** Al tratarse de un proyecto en evolución dentro del ciclo de desarrollo de software, un código ordenado y testeable permite escalar la aplicación fácilmente.
   * **Cómo se abordará en las pruebas:** Implementando pruebas unitarias desacopladas (con JUnit o PyTest) para cada componente del servicio, asegurando alta cohesión y bajo acoplamiento en el código fuente.
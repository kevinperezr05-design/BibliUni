# Casos de Prueba Formales — BiblioUni

## Resumen de cobertura por técnica
- **Total de casos:** 15 (CP-001 a CP-015)
- **Partición de equivalencia:** 7 casos (CP-001, CP-002, CP-003, CP-004, CP-005, CP-006, CP-014, CP-015)
- **Valores límite:** 7 casos (CP-007, CP-008, CP-009, CP-010, CP-011, CP-012, CP-013)
- **Caminos tristes:** 6 casos (CP-002, CP-004, CP-006, CP-010, CP-011, CP-015)

---

### CP-001: Crear material bibliográfico con datos válidos
- **Funcionalidad bajo prueba:** Crear material (`MaterialService.crearMaterial`)
- **Precondiciones:** El servicio de materiales está operativo y no existe conflicto de duplicidad.
- **Datos de entrada:** `titulo = "Clean Code"`, `autor = "Robert C. Martin"`, `tipo = "LIBRO"`, `copiasDisponibles = 3`
- **Pasos a ejecutar:**
  1. Instanciar un objeto `Material` con los datos de entrada.
  2. Invocar `materialService.crearMaterial(material)`.
- **Resultado esperado:** El material se guarda correctamente, se retorna la entidad con su ID asignado y `copiasDisponibles = 3`.
- **Técnica aplicada:** Partición de equivalencia (clase válida).

### CP-002: Crear material bibliográfico con título vacío (Camino triste)
- **Funcionalidad bajo prueba:** Crear material (`MaterialService.crearMaterial`)
- **Precondiciones:** El servicio de materiales está operativo.
- **Datos de entrada:** `titulo = ""`, `autor = "Robert C. Martin"`, `tipo = "LIBRO"`, `copiasDisponibles = 2`
- **Pasos a ejecutar:**
  1. Instanciar un objeto `Material` con el campo `titulo` vacío.
  2. Invocar `materialService.crearMaterial(material)`.
- **Resultado esperado:** El sistema rechaza la operación y lanza `IllegalArgumentException` sin persistir el registro.
- **Técnica aplicada:** Partición de equivalencia (clase inválida / camino triste).

### CP-003: Buscar material por ID existente
- **Funcionalidad bajo prueba:** Buscar material por ID (`MaterialService.buscarPorId`)
- **Precondiciones:** Existe en el repositorio un material registrado con `id = 1L`.
- **Datos de entrada:** `id = 1L`
- **Pasos a ejecutar:**
  1. Invocar `materialService.buscarPorId(1L)`.
- **Resultado esperado:** Retorna un `Optional<Material>` presente con el material correspondiente al ID `1L`.
- **Técnica aplicada:** Partición de equivalencia (clase válida).

### CP-004: Buscar material por ID inexistente (Camino triste)
- **Funcionalidad bajo prueba:** Buscar material por ID (`MaterialService.buscarPorId`)
- **Precondiciones:** No existe ningún material en el repositorio con `id = 999L`.
- **Datos de entrada:** `id = 999L`
- **Pasos a ejecutar:**
  1. Invocar `materialService.buscarPorId(999L)`.
- **Resultado esperado:** Retorna `Optional.empty()` (o lanza excepción de recurso no encontrado según contrato del servicio).
- **Técnica aplicada:** Partición de equivalencia (clase inválida / camino triste).

### CP-005: Validar correo institucional o personal válido
- **Funcionalidad bajo prueba:** Validar formato de correo (`UsuarioService.validarCorreo`)
- **Precondiciones:** El servicio de usuarios está disponible.
- **Datos de entrada:** `correo = "kevin.perez@uniempresarial.edu.co"`
- **Pasos a ejecutar:**
  1. Invocar `usuarioService.validarCorreo("kevin.perez@uniempresarial.edu.co")`.
- **Resultado esperado:** Retorna `true` indicando que el formato del correo electrónico es válido.
- **Técnica aplicada:** Partición de equivalencia (clase válida).

### CP-006: Validar correo con formato inválido sin arroba ni dominio (Camino triste)
- **Funcionalidad bajo prueba:** Validar formato de correo (`UsuarioService.validarCorreo`)
- **Precondiciones:** El servicio de usuarios está disponible.
- **Datos de entrada:** `correo = "kevin.perez.uniempresarial"`
- **Pasos a ejecutar:**
  1. Invocar `usuarioService.validarCorreo("kevin.perez.uniempresarial")`.
- **Resultado esperado:** Retorna `false` (o lanza `IllegalArgumentException`) rechazando el formato inválido.
- **Técnica aplicada:** Partición de equivalencia (clase inválida / camino triste).

### CP-007: Registrar préstamo cuando el usuario tiene 0 préstamos activos
- **Funcionalidad bajo prueba:** Registrar préstamo (`PrestamoService.registrarPrestamo`)
- **Precondiciones:** El material con `id = 10L` tiene `copiasDisponibles = 2` y el usuario con `id = 1L` tiene `0` préstamos activos.
- **Datos de entrada:** `usuarioId = 1L`, `materialId = 10L`, `prestamosActivos = 0`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.registrarPrestamo(1L, 10L)`.
- **Resultado esperado:** El préstamo se registra exitosamente y las copias disponibles del material disminuyen a `1`.
- **Técnica aplicada:** Valores límite (límite inferior: 0 préstamos activos).

### CP-008: Registrar préstamo cuando el usuario tiene 2 préstamos activos (justo debajo del límite)
- **Funcionalidad bajo prueba:** Registrar préstamo (`PrestamoService.registrarPrestamo`)
- **Precondiciones:** El material con `id = 10L` está disponible (`copiasDisponibles = 1`) y el usuario `id = 1L` tiene `2` préstamos activos (límite máximo permitido = 3).
- **Datos de entrada:** `usuarioId = 1L`, `materialId = 10L`, `prestamosActivos = 2`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.registrarPrestamo(1L, 10L)`.
- **Resultado esperado:** El préstamo se registra exitosamente, quedando el usuario con `3` préstamos activos.
- **Técnica aplicada:** Valores límite (valor interior inmediato al límite superior: 2 préstamos).

### CP-009: Registrar préstamo cuando el usuario ya tiene 3 préstamos activos (en el límite máximo)
- **Funcionalidad bajo prueba:** Registrar préstamo (`PrestamoService.registrarPrestamo`)
- **Precondiciones:** El material con `id = 10L` está disponible y el usuario `id = 1L` ya alcanzó el límite de `3` préstamos activos.
- **Datos de entrada:** `usuarioId = 1L`, `materialId = 10L`, `prestamosActivos = 3`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.registrarPrestamo(1L, 10L)`.
- **Resultado esperado:** El sistema impide crear un cuarto préstamo y lanza `IllegalStateException("El usuario ha alcanzado el límite máximo de 3 préstamos activos")`.
- **Técnica aplicada:** Valores límite (límite máximo exacto: 3 préstamos).

### CP-010: Registrar préstamo cuando el usuario tiene 4 préstamos activos (sobre el límite — Camino triste)
- **Funcionalidad bajo prueba:** Registrar préstamo (`PrestamoService.registrarPrestamo`)
- **Precondiciones:** El usuario `id = 1L` registra `4` préstamos activos en el sistema.
- **Datos de entrada:** `usuarioId = 1L`, `materialId = 10L`, `prestamosActivos = 4`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.registrarPrestamo(1L, 10L)`.
- **Resultado esperado:** El sistema rechaza la operación lanzando `IllegalStateException` y no guarda el préstamo.
- **Técnica aplicada:** Valores límite (valor por encima del límite: 4 préstamos / camino triste).

### CP-011: Registrar préstamo con material sin copias disponibles (Camino triste)
- **Funcionalidad bajo prueba:** Registrar préstamo (`PrestamoService.registrarPrestamo`)
- **Precondiciones:** El material con `id = 5L` existe pero tiene `copiasDisponibles = 0` (no disponible).
- **Datos de entrada:** `usuarioId = 1L`, `materialId = 5L`, `copiasDisponibles = 0`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.registrarPrestamo(1L, 5L)`.
- **Resultado esperado:** Falla el registro del préstamo, lanza `IllegalStateException("Material no disponible")` y no invoca `prestamoRepository.save(...)`.
- **Técnica aplicada:** Valores límite (`0` copias disponibles / camino triste).

### CP-012: Calcular multa con 0 días de retraso (devolución a tiempo)
- **Funcionalidad bajo prueba:** Calcular multa por devolución tardía (`PrestamoService.calcularMulta`)
- **Precondiciones:** Tarifa diaria configurada en `$2.000 COP`.
- **Datos de entrada:** `diasRetraso = 0`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.calcularMulta(0)`.
- **Resultado esperado:** Retorna `0.0` (sin cobro de multa).
- **Técnica aplicada:** Valores límite (límite inferior: 0 días de retraso).

### CP-013: Calcular multa con 1 día de retraso (límite mínimo de mora)
- **Funcionalidad bajo prueba:** Calcular multa por devolución tardía (`PrestamoService.calcularMulta`)
- **Precondiciones:** Tarifa diaria configurada en `$2.000 COP`.
- **Datos de entrada:** `diasRetraso = 1`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.calcularMulta(1)`.
- **Resultado esperado:** Retorna `2000.0` correspondiente a 1 día de mora.
- **Técnica aplicada:** Valores límite (valor mínimo con multa: 1 día de retraso).

### CP-014: Calcular multa con 5 días de retraso
- **Funcionalidad bajo prueba:** Calcular multa por devolución tardía (`PrestamoService.calcularMulta`)
- **Precondiciones:** Tarifa diaria configurada en `$2.000 COP`.
- **Datos de entrada:** `diasRetraso = 5`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.calcularMulta(5)`.
- **Resultado esperado:** Retorna `10000.0` (`5 * 2000.0`).
- **Técnica aplicada:** Partición de equivalencia (clase válida de múltiples días de retraso).

### CP-015: Calcular multa con días de retraso negativos (Camino triste)
- **Funcionalidad bajo prueba:** Calcular multa por devolución tardía (`PrestamoService.calcularMulta`)
- **Precondiciones:** El servicio de préstamos está operativo.
- **Datos de entrada:** `diasRetraso = -2`
- **Pasos a ejecutar:**
  1. Invocar `prestamoService.calcularMulta(-2)`.
- **Resultado esperado:** Lanza `IllegalArgumentException("Los días de retraso no pueden ser negativos")`.
- **Técnica aplicada:** Partición de equivalencia (clase inválida / camino triste).
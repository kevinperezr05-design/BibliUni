package com.bibliouni.servicio;

import com.bibliouni.modelo.Material;
import com.bibliouni.modelo.Prestamo;
import com.bibliouni.repositorio.MaterialRepository;
import com.bibliouni.repositorio.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    @Test
    void deberiaRegistrarPrestamoExitosoCuandoMaterialEstaDisponible() {
        // Arrange
        Long usuarioId = 1L;
        Long materialId = 10L;
        Material materialDisponible = new Material(materialId, "Java Efectivo", true);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(materialDisponible));
        when(prestamoRepository.save(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Prestamo resultado = prestamoService.registrarPrestamo(usuarioId, materialId);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(materialId, resultado.getMaterialId());
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    void deberiaFallarAlRegistrarPrestamoCuandoMaterialNoEstaDisponible() {
        // Arrange
        Long usuarioId = 1L;
        Long materialId = 5L;
        Material materialNoDisponible = new Material(materialId, "Libro Agotado", false);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(materialNoDisponible));

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> prestamoService.registrarPrestamo(usuarioId, materialId));
        verify(prestamoRepository, never()).save(any());
    }

    @Test
    void deberiaCalcularMultaPorDevolucionTardia() {
        // Arrange
        int diasRetraso = 3;

        // Act
        double multa = prestamoService.calcularMulta(diasRetraso);

        // Assert
        assertEquals(6000.0, multa);
    }

@Test
    void deberiaRechazarPrestamoCuandoUsuarioTieneTresPrestamosActivos() {
        // Arrange
        Long usuarioId = 1L;
        Long materialId = 10L;
        Material materialDisponible = new Material(materialId, "Clean Architecture", true);
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(materialDisponible));
        when(prestamoRepository.countByUsuarioId(usuarioId)).thenReturn(3L);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> prestamoService.registrarPrestamo(usuarioId, materialId));
        verify(prestamoRepository, never()).save(any());
    }
    @Test
    void deberiaLanzarExcepcionCuandoDiasDeRetrasoSonNegativos() {
        // Arrange
        int diasRetraso = -2;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> prestamoService.calcularMulta(diasRetraso));
    }
    @Test
    void deberiaRetornarCeroCuandoDiasDeRetrasoEsCero() {
        // Arrange
        int diasRetraso = 0;

        // Act
        double multa = prestamoService.calcularMulta(diasRetraso);

        // Assert
        assertEquals(0.0, multa);
    }
}

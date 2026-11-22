package com.bibliouni.servicio;

import com.bibliouni.modelo.Material;
import com.bibliouni.repositorio.MaterialRepository;
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
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;

    @Test
    void deberiaCrearMaterialExitosamente() {
        // Arrange
        Material material = new Material(1L, "Clean Code", true);
        when(materialRepository.save(any(Material.class))).thenReturn(material);

        // Act
        Material resultado = materialService.crearMaterial(material);

        // Assert
        assertNotNull(resultado);
        assertEquals("Clean Code", resultado.getTitulo());
        verify(materialRepository, times(1)).save(material);
    }

    @Test
    void deberiaBuscarMaterialPorIdExistente() {
        // Arrange
        Long idExistente = 1L;
        Material material = new Material(idExistente, "Clean Code", true);
        when(materialRepository.findById(idExistente)).thenReturn(Optional.of(material));

        // Act
        Optional<Material> resultado = materialService.buscarPorId(idExistente);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(idExistente, resultado.get().getId());
        verify(materialRepository, times(1)).findById(idExistente);
    }

    @Test
    void deberiaRetornarVacioAlBuscarMaterialPorIdInexistente() {
        // Arrange
        Long idInexistente = 999L;
        when(materialRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        Optional<Material> resultado = materialService.buscarPorId(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(materialRepository, times(1)).findById(idInexistente);
    }
}
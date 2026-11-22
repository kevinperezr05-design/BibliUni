package com.bibliouni.servicio;

import com.bibliouni.modelo.Material;
import com.bibliouni.modelo.Prestamo;
import com.bibliouni.repositorio.MaterialRepository;
import com.bibliouni.repositorio.PrestamoRepository;

public class PrestamoService {

    private static final double TARIFA_MULTA_POR_DIA = 2000.0;
    private static final int LIMITE_PRESTAMOS_ACTIVOS = 3;

    private final PrestamoRepository prestamoRepository;
    private final MaterialRepository materialRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, MaterialRepository materialRepository) {
        this.prestamoRepository = prestamoRepository;
        this.materialRepository = materialRepository;
    }

    public Prestamo registrarPrestamo(Long usuarioId, Long materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material no encontrado"));

        if (!material.isDisponible()) {
            throw new IllegalStateException("El material no está disponible");
        }
        
            validarLimitePrestamosActivos(usuarioId);
        

        Prestamo prestamo = new Prestamo(null, usuarioId, materialId);
        return prestamoRepository.save(prestamo);
    }
    private void validarLimitePrestamosActivos(Long usuarioId) {
        if (prestamoRepository.countByUsuarioId(usuarioId) >= LIMITE_PRESTAMOS_ACTIVOS) {
            throw new IllegalStateException("El usuario ha alcanzado el límite máximo de 3 préstamos activos");
        }
    }

    public double calcularMulta(int diasRetraso) {
        validarDiasRetraso(diasRetraso);
        return diasRetraso * TARIFA_MULTA_POR_DIA;
    }

    private void validarDiasRetraso(int diasRetraso) {
        if (diasRetraso < 0) {
            throw new IllegalArgumentException("Los días de retraso no pueden ser negativos");
        }
    }
}
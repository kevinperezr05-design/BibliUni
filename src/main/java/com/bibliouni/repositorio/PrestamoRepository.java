package com.bibliouni.repositorio;

import com.bibliouni.modelo.Prestamo;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    long countByUsuarioId(Long usuarioId);
}
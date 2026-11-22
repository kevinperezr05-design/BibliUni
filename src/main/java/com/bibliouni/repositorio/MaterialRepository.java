package com.bibliouni.repositorio;

import com.bibliouni.modelo.Material;
import java.util.Optional;

public interface MaterialRepository {
    Material save(Material material);
    Optional<Material> findById(Long id);
}
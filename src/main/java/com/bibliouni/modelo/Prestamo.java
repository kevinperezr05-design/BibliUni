package com.bibliouni.modelo;

public class Prestamo {
    private Long id;
    private Long usuarioId;
    private Long materialId;

    public Prestamo() {
    }

    public Prestamo(Long id, Long usuarioId, Long materialId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.materialId = materialId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}
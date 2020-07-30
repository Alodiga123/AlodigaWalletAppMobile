package com.alodiga.app.wallet.duallibrary.model;

public class ObjDireccion {

    private String ciudadId;
    private String codigoPostal;
    private String condadoId;
    private String direccion;
    private String direccionId;
    private String estadoId;
    private String paisId;

    public ObjDireccion(String ciudadId, String codigoPostal, String condadoId, String direccion, String direccionId, String estadoId, String paisId) {
        this.ciudadId = ciudadId;
        this.codigoPostal = codigoPostal;
        this.condadoId = condadoId;
        this.direccion = direccion;
        this.direccionId = direccionId;
        this.estadoId = estadoId;
        this.paisId = paisId;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCondadoId() {
        return condadoId;
    }

    public void setCondadoId(String condadoId) {
        this.condadoId = condadoId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }
}

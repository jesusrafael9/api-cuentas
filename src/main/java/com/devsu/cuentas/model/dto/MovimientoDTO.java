package com.devsu.cuentas.model.dto;

import java.time.LocalDateTime;

public class MovimientoDTO {

    private Long id;
    private String tipoMovimiento;
    private LocalDateTime fecha;
    private Double valor;
    private Double saldo;
    private Long cuentaId;

    // Getters y setters

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Long getId() {
        return id;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

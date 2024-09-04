package com.devsu.cuentas.model.enums;

public enum Errors {
    CUENTA_NO_ENCONTRADA("Cuenta no encontrada"),
    CLIENTE_NO_ENCONTRADO("Cliente no encontrado con ID: "),
    MOVIMIENTO_NO_ENCONTRADO("Movimiento no encontrado"),
    SALDO_INSUFICIENTE("Saldo insuficiente"),
    NO_SE_REALIZA_OPERACION("No se pudo realizar la operación"),
    TIPO_MOVIMIENTO_NO_VALIDO("Tipo de movimiento no válido");

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

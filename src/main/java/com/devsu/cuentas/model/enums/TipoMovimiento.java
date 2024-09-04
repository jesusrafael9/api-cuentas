package com.devsu.cuentas.model.enums;

public enum TipoMovimiento {

    RETIRO,

    DEPOSITO;

    public static TipoMovimiento fromString(String name) {
        for (TipoMovimiento tipo : TipoMovimiento.values()) {
            if (tipo.name().equalsIgnoreCase(name)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No enum constant " + TipoMovimiento.class.getCanonicalName() + "." + name);
    }
}

-- Crear la tabla Cuenta
CREATE TABLE Cuenta (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        numero_cuenta VARCHAR(20) NOT NULL,
                        tipo_cuenta VARCHAR(20) NOT NULL,
                        saldo DECIMAL(15, 2) NOT NULL,
                        cliente_id BIGINT NOT NULL
);

-- Crear la tabla Movimiento
CREATE TABLE Movimiento (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            fecha TIMESTAMP NOT NULL,
                            tipo_movimiento VARCHAR(20) NOT NULL,
                            valor DECIMAL(15, 2) NOT NULL,
                            saldo DECIMAL(15, 2) NOT NULL,
                            cuenta_id BIGINT NOT NULL,
                            FOREIGN KEY (cuenta_id) REFERENCES Cuenta(id)
);
package com.devsu.cuentas.service;

import com.devsu.cuentas.exception.ResourceNotFoundException;
import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.model.Movimiento;
import com.devsu.cuentas.model.enums.Errors;
import com.devsu.cuentas.model.enums.TipoMovimiento;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> getMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento saveMovimiento(Movimiento movimiento) {
        movimiento.setFecha(LocalDateTime.now());

        Cuenta cuenta = movimiento.getCuenta();
        Double nuevoSaldo = 0.0;

        switch (TipoMovimiento.fromString(movimiento.getTipoMovimiento().name())) {
            case RETIRO:
                if (cuenta.getSaldoInicial() < movimiento.getValor()) {
                    throw new ResourceNotFoundException(Errors.SALDO_INSUFICIENTE.getMessage());
                }
                nuevoSaldo = cuenta.getSaldoInicial() - movimiento.getValor();
                break;
            case DEPOSITO:
                nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
                break;
            default:
                throw new ResourceNotFoundException(Errors.TIPO_MOVIMIENTO_NO_VALIDO.getMessage());
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setSaldo(nuevoSaldo);

        return movimientoRepository.save(movimiento);
    }

    public void deleteMovimiento(Long id) {
        try {
            Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Errors.MOVIMIENTO_NO_ENCONTRADO.getMessage() +": "+ id));
            Cuenta cuenta = movimiento.getCuenta();
            Double nuevoSaldo = cuenta.getSaldoInicial() - movimiento.getValor();
            cuenta.setSaldoInicial(nuevoSaldo);
            cuentaRepository.save(cuenta);

            movimientoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(Errors.NO_SE_REALIZA_OPERACION.getMessage());
        }

    }

    public List<Movimiento> getMovimientosByCuentaId(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    public List<Movimiento> getMovimientosByCuentaIdAndFecha(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
    }
}

package com.devsu.cuentas.controller;

import com.devsu.cuentas.exception.ResourceNotFoundException;
import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.model.Movimiento;
import com.devsu.cuentas.model.dto.MovimientoDTO;
import com.devsu.cuentas.model.enums.Errors;
import com.devsu.cuentas.model.enums.TipoMovimiento;
import com.devsu.cuentas.service.CuentaService;
import com.devsu.cuentas.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoService.getAllMovimientos();
        List<MovimientoDTO> movimientoDTOs = new ArrayList<>();

        for (Movimiento movimiento : movimientos) {
            MovimientoDTO movimientoDTO = new MovimientoDTO();
            movimientoDTO.setId(movimiento.getId());
            movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento().name());
            movimientoDTO.setFecha(movimiento.getFecha());
            movimientoDTO.setValor(movimiento.getValor());
            movimientoDTO.setSaldo(movimiento.getSaldo());
            movimientoDTO.setCuentaId(movimiento.getCuenta().getId());

            movimientoDTOs.add(movimientoDTO);
        }

        return ResponseEntity.ok(movimientoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> getMovimientoById(@PathVariable Long id) {

        Optional<Movimiento> movimiento = movimientoService.getMovimientoById(id);
        return movimiento
                .map(mov -> {
                    MovimientoDTO movimientoDTO = new MovimientoDTO();
                    movimientoDTO.setId(mov.getId());
                    movimientoDTO.setTipoMovimiento(mov.getTipoMovimiento().name());
                    movimientoDTO.setFecha(mov.getFecha());
                    movimientoDTO.setValor(mov.getValor());
                    movimientoDTO.setSaldo(mov.getSaldo());
                    movimientoDTO.setCuentaId(mov.getCuenta().getId());
                    return movimientoDTO;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody MovimientoDTO movimientoDTO) {

            Optional<Cuenta> cuenta = cuentaService.getCuentaById(movimientoDTO.getCuentaId());
            if (cuenta.isEmpty()) {
                throw new ResourceNotFoundException(Errors.CUENTA_NO_ENCONTRADA.getMessage());
            }
            Movimiento movimiento = new Movimiento();
            movimiento.setTipoMovimiento(TipoMovimiento.fromString(movimientoDTO.getTipoMovimiento()));
            movimiento.setValor(movimientoDTO.getValor());
            movimiento.setSaldo(movimientoDTO.getSaldo());
            movimiento.setCuenta(cuenta.get());

            Movimiento nuevoMovimiento = movimientoService.saveMovimiento(movimiento);

            return nuevoMovimiento;
    }

    @PutMapping("/{id}")
    public Movimiento updateMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoDetails) {

        Optional<Movimiento> movimientoOpt = movimientoService.getMovimientoById(id);

        if (movimientoOpt.isEmpty()) {
            throw new ResourceNotFoundException(Errors.MOVIMIENTO_NO_ENCONTRADO.getMessage());
        }
        try {
            Movimiento movimiento = movimientoOpt.get();
            movimiento.setTipoMovimiento(TipoMovimiento.fromString(movimientoDetails.getTipoMovimiento().name()));
            movimiento.setValor(movimientoDetails.getValor());
            movimientoService.saveMovimiento(movimiento);
            return movimiento;
        } catch (Exception e) {
            throw new ResourceNotFoundException(Errors.NO_SE_REALIZA_OPERACION.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<Movimiento>> getMovimientosByCuentaId(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(movimientoService.getMovimientosByCuentaId(cuentaId));
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<MovimientoDTO>> getReporteMovimientos(
            @RequestParam Long cuentaId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, formatter);
        LocalDateTime fin = LocalDateTime.parse(fechaFin, formatter);
        List<MovimientoDTO> movimientoDTOs = new ArrayList<>();

        List<Movimiento> movimientos = movimientoService.getMovimientosByCuentaIdAndFecha(cuentaId, inicio, fin);

        movimientos.sort((m1, m2) -> m2.getFecha().compareTo(m1.getFecha()));

        for (Movimiento movimiento : movimientos) {
            MovimientoDTO movimientoDTO = new MovimientoDTO();
            movimientoDTO.setId(movimiento.getId());
            movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento().name());
            movimientoDTO.setFecha(movimiento.getFecha());
            movimientoDTO.setValor(movimiento.getValor());
            movimientoDTO.setSaldo(movimiento.getSaldo());
            movimientoDTO.setCuentaId(movimiento.getCuenta().getId());

            movimientoDTOs.add(movimientoDTO);
        }

        return ResponseEntity.ok(movimientoDTOs);
    }

}

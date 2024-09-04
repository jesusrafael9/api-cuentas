package com.devsu.cuentas.controller;

import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.model.Movimiento;
import com.devsu.cuentas.model.dto.MovimientoDTO;
import com.devsu.cuentas.model.enums.TipoMovimiento;
import com.devsu.cuentas.service.CuentaService;
import com.devsu.cuentas.service.MovimientoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovimientoControllerTest {

    @Mock
    private MovimientoService movimientoService;

    @InjectMocks
    private MovimientoController movimientoController;

    @Mock
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenGetMovimientoById_thenReturnMovimientoDTO() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        movimiento.setTipoMovimiento(TipoMovimiento.DEPOSITO);
        movimiento.setSaldo(100.0);
        movimiento.setFecha(LocalDateTime.of(2021, 9, 1, 0, 0)); // Corrected date format

        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        movimiento.setCuenta(cuenta);

        when(movimientoService.getMovimientoById(1L)).thenReturn(Optional.of(movimiento));

        ResponseEntity<MovimientoDTO> response = movimientoController.getMovimientoById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getCuentaId());
        assertEquals("DEPOSITO", response.getBody().getTipoMovimiento());
        assertEquals(100.0, response.getBody().getSaldo());
        assertEquals("2021-09-01T00:00", response.getBody().getFecha().toString()); // Adjusted assertion
    }

    @Test
    void whenGetMovimientoById_thenReturnNotFound() {
        when(movimientoService.getMovimientoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<MovimientoDTO> response = movimientoController.getMovimientoById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void whenSaveMovimiento_thenReturnMovimiento() {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setCuentaId(1L);
        movimientoDTO.setTipoMovimiento("Deposito");
        movimientoDTO.setSaldo(100.0);
        movimientoDTO.setFecha(LocalDateTime.of(2021, 9, 1, 0, 0));

        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(TipoMovimiento.DEPOSITO);
        movimiento.setSaldo(100.0);
        movimiento.setFecha(LocalDateTime.of(2021, 9, 1, 0, 0));

        when(cuentaService.getCuentaById(1L)).thenReturn(Optional.of(cuenta));
        when(movimientoService.saveMovimiento(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento response = movimientoController.createMovimiento(movimientoDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }
}
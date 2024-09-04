package com.devsu.cuentas.service;

import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.model.Movimiento;
import com.devsu.cuentas.model.enums.TipoMovimiento;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllMovimientos_thenReturnMovimientosList() {
        List<Movimiento> movimientos = List.of(new Movimiento(), new Movimiento());
        when(movimientoRepository.findAll()).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getAllMovimientos();

        assertEquals(2, result.size());
    }

    @Test
    void whenGetMovimientoById_thenReturnMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        when(movimientoRepository.findById(anyLong())).thenReturn(Optional.of(movimiento));

        Optional<Movimiento> result = movimientoService.getMovimientoById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void whenSaveMovimiento_thenReturnSavedMovimiento() {
        Movimiento movimiento = new Movimiento();
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setSaldoInicial(1000.0);
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(TipoMovimiento.DEPOSITO);
        movimiento.setValor(320.0);

        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));

        Movimiento result = movimientoService.saveMovimiento(movimiento);

        assertNotNull(result);
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    void whenDeleteMovimiento_thenVerifyDeletion() {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1L);
        movimiento.setValor(320.0);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setSaldoInicial(1000.0);
        movimiento.setCuenta(cuenta);

        when(movimientoRepository.findById(anyLong())).thenReturn(Optional.of(movimiento));
        doNothing().when(movimientoRepository).deleteById(anyLong());

        movimientoService.deleteMovimiento(1L);

        verify(movimientoRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenGetMovimientosByCuentaId_thenReturnMovimientosList() {
        List<Movimiento> movimientos = List.of(new Movimiento(), new Movimiento());
        when(movimientoRepository.findByCuentaId(anyLong())).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getMovimientosByCuentaId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void whenGetMovimientosByCuentaIdAndFecha_thenReturnMovimientosList() {
        List<Movimiento> movimientos = List.of(new Movimiento(), new Movimiento());
        LocalDateTime start = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 12, 31, 23, 59);
        when(movimientoRepository.findByCuentaIdAndFechaBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getMovimientosByCuentaIdAndFecha(1L, start, end);

        assertEquals(2, result.size());
    }
}
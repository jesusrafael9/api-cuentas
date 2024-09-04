package com.devsu.cuentas.service;

import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetCuentaById_thenReturnCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);

        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));

        Optional<Cuenta> foundCuenta = cuentaService.getCuentaById(1L);

        assertTrue(foundCuenta.isPresent());
        assertEquals(1L, foundCuenta.get().getId());
    }

    @Test
    void whenGetCuentaById_thenReturnEmpty() {
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Cuenta> foundCuenta = cuentaService.getCuentaById(1L);

        assertFalse(foundCuenta.isPresent());
    }
}
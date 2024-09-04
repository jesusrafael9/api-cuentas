package com.devsu.cuentas.controller;

import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cuentaController).build();
    }

    @Test
    public void whenGetCuentaById_thenReturnCuenta() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("Activa");
        cuenta.setClienteId(1L);

        when(cuentaService.getCuentaById(anyLong())).thenReturn(Optional.of(cuenta));

        mockMvc.perform(get("/cuentas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.tipoCuenta").value("Ahorro"))
                .andExpect(jsonPath("$.saldoInicial").value(1000.0))
                .andExpect(jsonPath("$.estado").value("Activa"))
                .andExpect(jsonPath("$.clienteId").value(1L));
    }

    @Test
    public void whenGetCuentaById_thenReturnNotFound() throws Exception {
        when(cuentaService.getCuentaById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cuentas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateCuenta_thenReturnCuenta() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado("Activa");
        cuenta.setClienteId(1L);

        when(cuentaService.saveCuenta(cuenta)).thenReturn(cuenta);

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"numeroCuenta\": \"1234567890\",\n" +
                                "    \"tipoCuenta\": \"Ahorro\",\n" +
                                "    \"saldoInicial\": 1000.0,\n" +
                                "    \"estado\": \"Activa\",\n" +
                                "    \"clienteId\": 1\n" +
                                "}"))
                .andExpect(status().isOk());
    }
}

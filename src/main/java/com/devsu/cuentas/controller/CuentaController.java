package com.devsu.cuentas.controller;

import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.getCuentaById(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        try {
            Cuenta nuevaCuenta = cuentaService.saveCuenta(cuenta);
            return ResponseEntity.ok(nuevaCuenta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaDetails) {
        Optional<Cuenta> cuentaOpt = cuentaService.getCuentaById(id);

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            cuenta.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
            cuenta.setTipoCuenta(cuentaDetails.getTipoCuenta());
            cuenta.setSaldoInicial(cuentaDetails.getSaldoInicial());
            cuenta.setEstado(cuentaDetails.getEstado());
            cuenta.setClienteId(cuentaDetails.getClienteId());

            return ResponseEntity.ok(cuentaService.saveCuenta(cuenta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

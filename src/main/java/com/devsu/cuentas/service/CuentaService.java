package com.devsu.cuentas.service;

import com.devsu.cuentas.cliente.ClienteFeignClient;
import com.devsu.cuentas.exception.ResourceNotFoundException;
import com.devsu.cuentas.model.Cliente;
import com.devsu.cuentas.model.Cuenta;
import com.devsu.cuentas.model.enums.Errors;
import com.devsu.cuentas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> getCuentaById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta saveCuenta(Cuenta cuenta) {
        try {
            Optional<Cliente> cliente = clienteFeignClient.getClienteById(cuenta.getClienteId());
            if (cliente.isEmpty()) {
                throw new ResourceNotFoundException(Errors.CLIENTE_NO_ENCONTRADO.getMessage() + cuenta.getClienteId());
            }
            return cuentaRepository.save(cuenta);
        } catch (Exception e){
            throw new ResourceNotFoundException(Errors.NO_SE_REALIZA_OPERACION.getMessage());
        }
    }

    public void deleteCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}

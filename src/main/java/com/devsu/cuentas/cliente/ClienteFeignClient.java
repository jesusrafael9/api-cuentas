package com.devsu.cuentas.cliente;

import com.devsu.cuentas.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "cliente-service", url = "http://localhost:8080/clientes")
public interface ClienteFeignClient {

    @GetMapping("/{id}")
    Optional<Cliente> getClienteById(@PathVariable("id") Long id);
}
package org.sofka.api.cuenta.bancaria.cuenta.clients;

import java.util.Optional;
import org.sofka.api.cuenta.bancaria.cliente.controller.dto.salida.BaseResponseDto;
import org.sofka.api.cuenta.bancaria.cuenta.model.ClienteModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cliente", url = "${msvc.cliente.url}")
public interface ClienteClientRest {

    @GetMapping("/{identificacion}")
    ResponseEntity<BaseResponseDto> obtenerClientePorIdentificacion(@PathVariable String identificacion);


}

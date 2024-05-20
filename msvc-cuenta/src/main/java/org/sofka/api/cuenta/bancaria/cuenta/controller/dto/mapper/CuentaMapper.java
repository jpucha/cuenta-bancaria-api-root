package org.sofka.api.cuenta.bancaria.cuenta.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.sofka.api.cuenta.bancaria.cuenta.controller.dto.salida.CuentaSalidaDto;
import org.sofka.api.cuenta.bancaria.cuenta.model.entity.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    //@Mapping(target = "nombresCliente", source= "cliente.nombre")
    CuentaSalidaDto toCuentaResponseDto(Cuenta cuenta);

}

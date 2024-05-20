/**
 *
 */
package org.sofka.api.cuenta.bancaria.cuenta.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.sofka.api.cuenta.bancaria.cuenta.clients.ClienteClientRest;
import org.sofka.api.cuenta.bancaria.cuenta.controller.dto.entrada.CuentaEntradaDto;
import org.sofka.api.cuenta.bancaria.cuenta.enumeration.EstadoEmun;
import org.sofka.api.cuenta.bancaria.cuenta.exception.CuentaException;
import org.sofka.api.cuenta.bancaria.cuenta.model.ClienteModel;
import org.sofka.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.sofka.api.cuenta.bancaria.cuenta.repository.CuentaRepository;
import org.sofka.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <b> Servicio para la Cuenta. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Jenny Pucha $, $Date: 20 abr. 2024 $]
 *     </p>
 */
@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteClientRest clienteRest;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cuenta create(CuentaEntradaDto cuentaEntradaDto) throws CuentaException {
        try {
            Optional<ClienteModel> clienteEncontrado = clienteRest.obtenerClientePorIdentificacion(
                cuentaEntradaDto.getIdentificacion());
            if (clienteEncontrado.isPresent()) {
                if (EstadoEmun.INACTIVO.getDescripcion()
                    .equals(clienteEncontrado.get().getEstado())) {
                    throw new CuentaException("El cliente encontrado con estado inactivo.");
                }
            } else {
                Cuenta cuenta = Cuenta.builder()
                    .estado(EstadoEmun.ACTIVO.getDescripcion())
                    .numeroCuenta(cuentaEntradaDto.getNumeroCuenta())
                    .saldoInicial(BigDecimal.valueOf(cuentaEntradaDto.getSaldoInicial()))
                    .tipoCuenta(cuentaEntradaDto.getTipoCuenta())
                    .idCliente(clienteEncontrado.get().getClienteId()).build();
                return cuentaRepository.save(cuenta);

            }
        } catch (CuentaException e) {
            throw new CuentaException(e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cuenta update(CuentaEntradaDto cuentaEntradaDto) throws CuentaException {

        try {
            Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(
                cuentaEntradaDto.getNumeroCuenta());
            if (!cuenta.isPresent()) {
                throw new CuentaException("No existe la cuenta ingresada.");
            }
            Cuenta cuentaActualizar = cuenta.get();
            cuentaActualizar.setNumeroCuenta(cuentaEntradaDto.getNumeroCuenta());
            cuentaActualizar.setTipoCuenta(cuentaEntradaDto.getTipoCuenta());
            cuentaActualizar
                .setSaldoInicial(
                    BigDecimal.valueOf(Double.valueOf(cuentaEntradaDto.getSaldoInicial())));
            cuentaActualizar.setEstado(cuentaEntradaDto.getEstado());
            return cuentaRepository.save(cuentaActualizar);
        } catch (CuentaException e) {
            throw new CuentaException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Long id) throws CuentaException {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findById(id);
            if (!cuenta.isPresent()) {
                throw new CuentaException(
                    "No se puede eliminar la cuenta con id: " + id + " no existe.");
            }
            cuentaRepository.deleteById(cuenta.get().getIdCuenta());
        } catch (CuentaException e) {
            throw new CuentaException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cuenta> obtenerCuentasPorCliente(String identificacion) throws CuentaException {
        try {
            Optional<ClienteModel> clienteEncontrado = clienteRest.obtenerClientePorIdentificacion(
                identificacion);

            if (!clienteEncontrado.isPresent()) {
                throw new CuentaException("No existe el cliente, favor registrarlo primero.");
            }
            List<Cuenta> listaCuenta = cuentaRepository.findByIdCliente(
                clienteEncontrado.get().getClienteId());
            if (ObjectUtils.isEmpty(listaCuenta)) {
                throw new CuentaException(
                    "No existe cuenta(s) para el número de identificación ingresado."
                        + clienteEncontrado.get().getIdentificacion());
            }
            return listaCuenta;

        } catch (CuentaException e) {
            throw new CuentaException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cuenta> obtenerPorNumeroCuenta(int numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ClienteModel> obtenerClientePorIdentificacion(String id) {
        return clienteRest.obtenerClientePorIdentificacion(id);
    }

}

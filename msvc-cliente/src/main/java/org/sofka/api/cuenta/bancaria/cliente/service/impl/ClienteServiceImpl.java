/**
 *
 */
package org.sofka.api.cuenta.bancaria.cliente.service.impl;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.sofka.api.cuenta.bancaria.cliente.controller.dto.entrada.ClienteEntradaDto;
import org.sofka.api.cuenta.bancaria.cliente.exception.ClienteException;
import org.sofka.api.cuenta.bancaria.cliente.model.entity.Cliente;
import org.sofka.api.cuenta.bancaria.cliente.repository.ClienteRepository;
import org.sofka.api.cuenta.bancaria.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b> Servicio para el cliente. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Jenny Pucha $, $Date: 18 may. 2024 $]
 *     </p>
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cliente create(ClienteEntradaDto clienteEntradaDto) throws ClienteException {
        try {
            Optional<Cliente> clienteEncontrado = clienteRepository
                .obtenerPorIdentificacion(clienteEntradaDto.getIdentificacion());
            if (clienteEncontrado.isPresent()) {
                throw new ClienteException("No es posible crear este cliente porque ya existe.");
            } else {
                Cliente cliente = Cliente.builder().direccion(clienteEntradaDto.getDireccion())
                    .edad(clienteEntradaDto.getEdad())
                    .genero(clienteEntradaDto.getGenero())
                    .identificacion(clienteEntradaDto.getIdentificacion())
                    .nombre(clienteEntradaDto.getNombre())
                    .telefono(clienteEntradaDto.getTelefono())
                    .contrasena(clienteEntradaDto.getContrasena()).estado(Boolean.TRUE.toString())
                    .build();

                return clienteRepository.save(cliente);
            }
        } catch (ClienteException e) {
            throw new ClienteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cliente> read() {
        return clienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cliente update(ClienteEntradaDto clienteEntradaDto) throws ClienteException {
        try {
            Optional<Cliente> clienteEncontrado = clienteRepository.obtenerPorIdentificacion(
                clienteEntradaDto.getIdentificacion());

            if (clienteEncontrado.isPresent()) {
                Cliente cliente = clienteEncontrado.get();
                cliente.setNombre(clienteEntradaDto.getNombre());
                cliente.setGenero(clienteEntradaDto.getGenero());
                cliente.setEdad(clienteEntradaDto.getEdad());
                //cliente.setIdentificacion(clienteEntradaDto.getIdentificacion());
                cliente.setDireccion(clienteEntradaDto.getDireccion());
                cliente.setTelefono(clienteEntradaDto.getTelefono());
                cliente.setContrasena(clienteEntradaDto.getContrasena());
                cliente.setEstado(clienteEntradaDto.getEstado());
                return clienteRepository.save(cliente);

            } else {
                throw new ClienteException("Cliente no se encuentra registrado");
            }
        } catch (ClienteException e) {
            throw new ClienteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(String identificacion) throws ClienteException {
        try {
            Optional<Cliente> clienteEncontrado = clienteRepository.obtenerPorIdentificacion(
                identificacion);
            if (!clienteEncontrado.isPresent()) {
                throw new ClienteException("No se puede eliminar, cliente no encontrado");
            }
            clienteRepository.deleteById(clienteEncontrado.get().getClienteId());

        } catch (ClienteException e) {
            throw new ClienteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cliente> obtenerPorIdentificacion(String identificacion)
        throws ClienteException {
        try {
            Optional<Cliente> clienteEncontrado = clienteRepository
                .obtenerPorIdentificacion(identificacion);
            if (!clienteEncontrado.isPresent()) {
                throw new ClienteException("No existe cliente con la identificacion ingresada");
            }
            return clienteEncontrado;
        } catch (ClienteException e) {
            throw new ClienteException(e);
        }
    }
}

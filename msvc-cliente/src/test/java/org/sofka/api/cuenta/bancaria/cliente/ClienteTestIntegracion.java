/**
 *
 */
package org.sofka.api.cuenta.bancaria.cliente;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.sofka.api.cuenta.bancaria.cliente.controller.ClienteController;
import org.sofka.api.cuenta.bancaria.cliente.model.entity.Cliente;
import org.sofka.api.cuenta.bancaria.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

/**
 * <b> Clase test integracion para el cliente. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Jenny Pucha $, $Date: 18 may. 2024 $]
 *     </p>
 */
@WebMvcTest(ClienteController.class)
public class ClienteTestIntegracion {

    @MockBean
    private ClienteService iClienteService;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Test
    public void get_obtenerPorIdentificacion_Returns_200_OK() throws Exception {
        String identificacionEntrada = "1720693500";
        Cliente clienteSalidaEsperada = Cliente.builder().nombre("Jose Lema ")
            .direccion("Otavalo sn y principal ")
            .telefono("098254785").contrasena("1234").estado("True").build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(iClienteService.obtenerPorIdentificacion(identificacionEntrada)).thenReturn(
            Optional.ofNullable(clienteSalidaEsperada));

        mvc.perform(
                get("/api/clientes/1720693500").contentType(MediaType.APPLICATION_JSON))
            //Then
            .andExpect(status().isOk());

        verify(iClienteService, times(1)).obtenerPorIdentificacion(identificacionEntrada);

    }

}

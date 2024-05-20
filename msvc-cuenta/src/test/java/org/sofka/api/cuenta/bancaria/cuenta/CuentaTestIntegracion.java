package org.sofka.api.cuenta.bancaria.cuenta;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.sofka.api.cuenta.bancaria.cuenta.controller.CuentaController;
import org.sofka.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.sofka.api.cuenta.bancaria.cuenta.service.CuentaService;
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
@WebMvcTest(CuentaController.class)
public class CuentaTestIntegracion {

    @MockBean
    private CuentaService iCuentaService;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenObtenerCuentaPorNumeroIdentificacionThenNotNull() throws Exception {
        String identificacionEntrada = "1720693500";

        Cuenta cuentaEsperada = Cuenta.builder().numeroCuenta(456789).idCuenta(1L).idCliente(1L).
            tipoCuenta("Ahorros").saldoInicial(BigDecimal.valueOf(1000L)).estado("True").build();

        List<Cuenta> cuentaEsperadaList = new ArrayList<>();
        cuentaEsperadaList.add(cuentaEsperada);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(iCuentaService.obtenerCuentasPorCliente(identificacionEntrada)).thenReturn(
            cuentaEsperadaList);

        mvc.perform(
                get("/api/cuentas/1720693500").contentType(MediaType.APPLICATION_JSON))
            //Then
            .andExpect(status().isOk());

        verify(iCuentaService, times(1)).obtenerCuentasPorCliente(identificacionEntrada);
    }

}

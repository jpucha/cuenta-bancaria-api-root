package org.sofka.api.cuenta.bancaria.cuenta;

import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sofka.api.cuenta.bancaria.cuenta.exception.CuentaException;
import org.sofka.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.sofka.api.cuenta.bancaria.cuenta.model.entity.Movimiento;
import org.sofka.api.cuenta.bancaria.cuenta.repository.CuentaRepository;
import org.sofka.api.cuenta.bancaria.cuenta.repository.MovimientoRepository;
import org.sofka.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.sofka.api.cuenta.bancaria.cuenta.service.impl.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class MovimientoTest {

    @InjectMocks
    MovimientoServiceImpl movimientoService;

    @Mock
    MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Test
    void whenObtenerPorNumeroCuentaThenNotNull() throws CuentaException {
        Movimiento movimientoEsperado_uno = Movimiento.builder().idMovimiento(1L).idCuenta(1L)
            .tipoMovimiento("credito").valor(BigDecimal.valueOf(100L))
            .saldo(BigDecimal.valueOf(1100L)).saldoAnterior(BigDecimal.valueOf(1000L)).build();
        Movimiento movimientoEsperado_dos = Movimiento.builder().idMovimiento(1L).idCuenta(1L)
            .tipoMovimiento("debito").valor(BigDecimal.valueOf(100L))
            .saldo(BigDecimal.valueOf(1000L)).saldoAnterior(BigDecimal.valueOf(1100L)).build();
        List<Movimiento> listaMovimientoEsperado = new ArrayList<>();
        listaMovimientoEsperado.add(movimientoEsperado_uno);
        listaMovimientoEsperado.add(movimientoEsperado_dos);

        Cuenta cuentaEsperada = Cuenta.builder().numeroCuenta(456789).idCuenta(1L).idCliente(1L).
            tipoCuenta("Ahorros").saldoInicial(BigDecimal.valueOf(1000L)).estado("True").build();

        List<Movimiento> respuestaActual;
        when(this.cuentaRepository.findByNumeroCuenta(456789)).thenReturn(
            Optional.ofNullable(cuentaEsperada));
        when(this.movimientoRepository.findByIdCuenta(1L)).thenReturn(listaMovimientoEsperado);
        respuestaActual = this.movimientoService.obtenerPorNumeroCuenta(456789);
        Assert.assertNotNull(respuestaActual);
    }
}

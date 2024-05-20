package org.sofka.api.cuenta.bancaria.cuenta.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * <b> Interfaz para obtener los datos del reporte entre fechas. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Jenny Pucha $, $Date: 19 may. 2024 $]
 *          </p>
 */
public interface ReporteDto {

    Date getFecha();

    String getCliente();

    int getNumeroCuenta();

    String getTipo();

    BigDecimal getSaldoInicial();

    String getEstado();

    BigDecimal getMovimiento();

    BigDecimal getSaldoDisponible();

    String getTipoMovimiento();

}

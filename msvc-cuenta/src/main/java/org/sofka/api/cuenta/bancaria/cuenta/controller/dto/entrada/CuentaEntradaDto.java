/**
 *
 */
package org.sofka.api.cuenta.bancaria.cuenta.controller.dto.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * <b> Clase Dto para la entrada de la cuenta. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Jenny Pucha $, $Date: 19 may. 2024 $]
 *          </p>
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaEntradaDto {

    @NotBlank
    private String identificacion;

    @NotBlank
    private String tipoCuenta;

    @NotNull
    private double saldoInicial;

    private String estado;

    @NotNull
    private int numeroCuenta;

}

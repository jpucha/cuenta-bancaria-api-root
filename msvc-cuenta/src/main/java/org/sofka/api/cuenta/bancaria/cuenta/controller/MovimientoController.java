/**
 *
 */
package org.sofka.api.cuenta.bancaria.cuenta.controller;

import javax.validation.Valid;
import org.sofka.api.cuenta.bancaria.cliente.controller.dto.salida.BaseResponseDto;
import org.sofka.api.cuenta.bancaria.cuenta.controller.dto.entrada.MovimientoEntradaDto;
import org.sofka.api.cuenta.bancaria.cuenta.controller.util.ValidaWsUtil;
import org.sofka.api.cuenta.bancaria.cuenta.exception.CuentaException;
import org.sofka.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.sofka.api.cuenta.bancaria.cuenta.service.MovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b> Clase controlador para los movimientos contables. </b>
 *
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Jenny Pucha $, $Date: 21 abr. 2022 $]
 *     </p>
 */
@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private static final Logger log = LoggerFactory.getLogger(MovimientoController.class);
    private static final String ERROR_MN = "Ha ocurrido un error {}";

    @Autowired
    private MovimientoService service;

    @Autowired
    private CuentaService cuentaService;

    /**
     * <b> Metodo que crea los movimientos. </b>
     * <p>
     * [Author: Jenny Pucha, Date: 19 may. 2024]
     * </p>
     *
     * @param movimientoEntradaDto parametro de entrada
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> guardar(
        @Valid @RequestBody MovimientoEntradaDto movimientoEntradaDto, BindingResult resultado) {
        try {
            if (resultado.hasErrors()) {
                return ValidaWsUtil.validar(resultado);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseDto.builder().data(service.create(movimientoEntradaDto)).build());
        } catch (CuentaException e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.internalServerError()
                .body(BaseResponseDto.builder().message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que obtiene todos los movimientos existentes. </b>
     * <p>
     * [Author: Jenny Pucha, Date: 19 may. 2024]
     * </p>
     *
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     **/
    @GetMapping(path = "/{numeroCuenta}")
    public ResponseEntity<BaseResponseDto> obtenerPorNumeroCuenta(@PathVariable int numeroCuenta)
        throws CuentaException {
        try {
            return ResponseEntity.ok().body(
                BaseResponseDto.builder().data(service.obtenerPorNumeroCuenta(numeroCuenta))
                    .build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.internalServerError()
                .body(BaseResponseDto.builder().message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que actualiza el movimiento. </b>
     * <p>
     * [Author: Jenny Pucha, Date: 19 may. 2024]
     * </p>
     *
     * @param movimientoEntradaDto parametro de entrada
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     */
    @PutMapping
    public ResponseEntity<BaseResponseDto> actualizar(
        @Valid @RequestBody MovimientoEntradaDto movimientoEntradaDto, BindingResult resultado) {
        try {
            if (resultado.hasErrors()) {
                return ValidaWsUtil.validar(resultado);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseDto.builder().data(service.update(movimientoEntradaDto)).build());
        } catch (CuentaException e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.internalServerError()
                .body(BaseResponseDto.builder().message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que elimina un registro por su id. </b>
     * <p>
     * [Author: Jenny Pucha, Date: 19 may. 2024]
     * </p>
     *
     * @param id parametro de entrada
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> eliminar(@PathVariable("id") Long id) {
        try {
            service.deleteByIdMovimiento(id);
            return ResponseEntity.ok()
                .body(BaseResponseDto.builder().message("Registro Eliminado").build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.internalServerError()
                .body(BaseResponseDto.builder().message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que genera el reporte por fechas, la fecha(yyyy-MM-dd) esta separada por
     * coma(,). </b>
     * <p>
     * [Author: Jenny Pucha, Date: 21 abr. 2024]
     * </p>
     *
     * @param fecha fechas de entrada
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     **/
    @GetMapping(value = "/reportes")
    public ResponseEntity<BaseResponseDto> generarReporte(@RequestParam String identificacion,
        @RequestParam String fecha) {
        try {
            return ResponseEntity.ok().body(
                BaseResponseDto.builder().data(service.generarReporte(identificacion, fecha))
                    .build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.internalServerError()
                .body(BaseResponseDto.builder().message(e.getCause().getMessage()).build());
        }
    }
}

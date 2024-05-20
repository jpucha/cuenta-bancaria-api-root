/**
 * 
 */
package org.sofka.api.cuenta.bancaria.cuenta.enumeration;

/**
 * 
 * <b> Enumeracion de los estados. </b>
 * 
 * @author Jenny Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Jenny Pucha $, $Date: 20 abr. 2024 $]
 *          </p>
 */
public enum EstadoEmun {

	ACTIVO("A", "True"), INACTIVO("I", "False");

	private String codigo;
	private String descripcion;

	private EstadoEmun(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

}

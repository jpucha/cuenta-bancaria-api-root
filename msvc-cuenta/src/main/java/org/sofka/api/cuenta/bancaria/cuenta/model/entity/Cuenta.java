package org.sofka.api.cuenta.bancaria.cuenta.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sofka.api.cuenta.bancaria.cuenta.model.ClienteModel;

/**
 * 
 * <b> Clase entidad de la tabla cuenta. </b>
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
@DynamicUpdate
@Entity
public class Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cuenta")
	private Long idCuenta;

	@NotBlank
	private String estado;

	@NotNull
	@Column(name="numero_cuenta", unique = true)
	private int numeroCuenta;

	@NotNull
	@Column(name = "saldo_inicial")
	private BigDecimal saldoInicial;

	@NotBlank
	@Column(name = "tipo_cuenta")
	private String tipoCuenta;

	@NotNull
	@Column(name = "id_cliente")
	private Long idCliente;

	@Transient
	private List<ClienteModel> clienteList;

	/*bi-directional many-to-one association to Cliente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	@JsonIgnore
	private ClienteModel cliente;*/

	// bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy = "cuenta")
	@JsonIgnore
	private List<Movimiento> movimientos;

}
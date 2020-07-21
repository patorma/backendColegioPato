package com.patricocontreras.backendColegioPato.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
@Entity
@Table(name="profesores")
public class Profesor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	@Column(nullable=false)
	private String nombre;
	
	@NotEmpty
	private boolean activo;
	
	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@NotNull(message = "la asignatura no puede ser vacia")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "asignatura_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private Asignatura asignatura;
	
	@NotNull(message = "El colegio no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="colegio_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private Colegio colegio;
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}

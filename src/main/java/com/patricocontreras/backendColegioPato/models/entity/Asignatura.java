package com.patricocontreras.backendColegioPato.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
@Entity
@Table(name="asignaturas")
public class Asignatura implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	@Column(nullable=false)
	private String nombre;
	
	/*@OneToOne(mappedBy ="asignatura")
	private Profesor profesor;¨*
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

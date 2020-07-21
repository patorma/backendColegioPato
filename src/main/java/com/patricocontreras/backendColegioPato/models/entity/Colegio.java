package com.patricocontreras.backendColegioPato.models.entity;

import java.io.Serializable;
//import java.util.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "colegios")
public class Colegio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty    
	@Size(min = 4, max=100)
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty
	@Size(min = 8,max = 255)
	@Column(nullable=false)
	private String direccion;

	@Column(name = "fecha_creacion")
	private LocalDate fechaCreacion;
	
	@PrePersist
	public void prePersit() {
		fechaCreacion= LocalDate.now();
	}
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "colegio",cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer","hadler"})
	private List<Profesor> profesores;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

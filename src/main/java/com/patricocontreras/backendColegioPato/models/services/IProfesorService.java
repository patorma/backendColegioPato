package com.patricocontreras.backendColegioPato.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.patricocontreras.backendColegioPato.models.entity.Asignatura;
import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

public interface IProfesorService {
	
	public List<Profesor> findAll();
	
	public Page<Profesor> findAll(Pageable pageable);
	
	public Profesor findById(Long id);
	
	public Profesor save(Profesor profesor);
	
	public void delete(Long id);

	public List<Asignatura> findAllAsignaturas();
	
	public List<Colegio> findAllColegios();
}

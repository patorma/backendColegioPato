package com.patricocontreras.backendColegioPato.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patricocontreras.backendColegioPato.models.entity.Asignatura;
import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

public interface IProfesorDao extends JpaRepository<Profesor, Long> {
	
	@Query("from Asignatura") 
	public List<Asignatura>  findAllAsignaturas();
	
	@Query("from Colegio")
	public List<Colegio> findAllColegios();

}

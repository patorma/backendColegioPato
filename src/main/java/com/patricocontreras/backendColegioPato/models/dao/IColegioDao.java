package com.patricocontreras.backendColegioPato.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

public interface IColegioDao  extends JpaRepository<Colegio, Long>  {
	
	@Query("from Profesor")
	public List<Profesor> findAllProfesores();

}

package com.patricocontreras.backendColegioPato.models.services;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

public interface IColegioService {
	
	public List<Colegio> findAll();
	
	public Page<Colegio> findAll(Pageable pageable);
	
	public Colegio findById(Long id);
	
	public Colegio save(Colegio colegio);
	
	public void delete(Long id);
	
	public List<Profesor> findAllProfesores();

}

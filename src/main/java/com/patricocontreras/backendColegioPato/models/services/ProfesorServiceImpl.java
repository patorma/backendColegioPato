package com.patricocontreras.backendColegioPato.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patricocontreras.backendColegioPato.models.dao.IProfesorDao;
import com.patricocontreras.backendColegioPato.models.entity.Asignatura;
import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

@Service
public class ProfesorServiceImpl implements IProfesorService {
	
	@Autowired
	IProfesorDao profesorDao;

	@Override
	@Transactional(readOnly = true)
	public List<Profesor> findAll() {
		
		return profesorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Profesor> findAll(Pageable pageable) {
		
		return profesorDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Profesor findById(Long id) {
	
		return profesorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Profesor save(Profesor profesor) {
	
		return profesorDao.save(profesor);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		profesorDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() {
		
		return profesorDao.findAllAsignaturas();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Colegio> findAllColegios() {
	
		return profesorDao.findAllColegios();
	}

}

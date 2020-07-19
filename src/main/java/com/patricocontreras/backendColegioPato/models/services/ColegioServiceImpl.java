package com.patricocontreras.backendColegioPato.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patricocontreras.backendColegioPato.models.dao.IColegioDao;
import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;

@Service
public class ColegioServiceImpl implements IColegioService {
	
	@Autowired
	private IColegioDao colegioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Colegio> findAll() {
		// TODO Auto-generated method stub
		return colegioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Colegio> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return colegioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Colegio findById(Long id) {
		// TODO Auto-generated method stub
		return colegioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Colegio save(Colegio colegio) {
		return colegioDao.save(colegio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		colegioDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Profesor> findAllProfesores() {
		
		return colegioDao.findAllProfesores();
	}

}

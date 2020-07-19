package com.patricocontreras.backendColegioPato.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patricocontreras.backendColegioPato.models.entity.Asignatura;

public interface IAsignaturaDao extends JpaRepository<Asignatura, Long> {
	

}

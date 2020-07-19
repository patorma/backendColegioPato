package com.patricocontreras.backendColegioPato.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.services.IColegioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ColegioRestController {
	
	@Autowired
	private IColegioService colegioService;
	
	@GetMapping("/colegios")
	public List<Colegio> index(){
		return colegioService.findAll();
	}
	
	@GetMapping("/colegios/page/{page}")
	public Page<Colegio> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return colegioService.findAll(pageable);
	}
	
	// aqui quede

}

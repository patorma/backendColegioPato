package com.patricocontreras.backendColegioPato.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;
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
	
	@GetMapping("/colegios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
		Colegio colegio = null;
		//a estudiar
		Map<String, Object> response  = new HashMap<>();
		try {
			colegio = colegioService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(colegio == null) {
			response.put("mensaje", "El colegio ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new  ResponseEntity<Colegio> (colegio,HttpStatus.OK);
	}
	
	// antes de ejecutar en todo su esplendor el metodo create se deben validar los datos
	// osea intercepta el objeto colegio y valida cada valor , cada atributo desde el request body
	// se agrega @Valid y despues se inyecta al metodo create el objeto que tiene todos
	//los mensajes de error
	// donde podemos saber si tenemos algun problema que en este caso es el objeto result
	
	@PostMapping("/colegios")
	public ResponseEntity<?>create(@Valid @RequestBody Colegio colegio,BindingResult result){
		
		//Es el nuevo colegio creado
		Colegio colegioNew = null;
		Map<String, Object> response = new HashMap<>();
		
		// se valida si contiene errores el objeto 
				if(result.hasErrors()) {
					// se debe obtener los mensajes de errror de cada campo 
					// y convertir estos en una lista de errores de tipo string
					
					// se debe convertir esta lista de fielderrors en String
					
					// se debe convertir esta lista de fielderrors en String
					List<String> errors = result.getFieldErrors()
							.stream()
							.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())// muy parecido  al operador map en angular (rxjs), mismo concepto!
							.collect(Collectors.toList());// ahora podemos convertir de regreso el stream  aun tipo List
					response.put("errors", errors);
					// se responde con un responseentity con listados de error
					return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
					
					// en lo anterior se recibe un field errors y lo convertimos a string
				}
				
				try {
					colegioNew = colegioService.save(colegio);
					
				}catch(DataAccessException e) {
					response.put("mensaje", "Error al realizar el insert en la base de datos!");
					response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				//se podria pasar un map con un mensaje y con el colegio creado
				response.put("mensaje", "El colegio ha sido creado con éxito! ");
				response.put("colegio", colegioNew);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/colegios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Colegio colegio,BindingResult result,@PathVariable Long id){
		//obtenemos el colegio de la bd por Id
		Colegio colegioActual = colegioService.findById(id);
		
		//Colegio ya actualizado
		Colegio colegioUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			// se debe obtener los mensajes de erqror de cada campo 
		   // y convertir estos en una lista de errores de tipo string
			// se debe convertir esta lista de fielderrors en String
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())// muy parecido  al operador map en angular (rxjs), mismo concepto!
					.collect(Collectors.toList());// ahora podemos convertir de regreso el stream  aun tipo List
			response.put("errors", errors);
			// se responde con un responseentity con listados de error
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			
			// en lo anterior se recibe un field errors y lo convertimos a string
		}
		
		if(colegioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el colegio ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			//modificamos los datos del colegio actual con los datos del cliente que te envien
			colegioActual.setNombre(colegio.getNombre());
			colegioActual.setDireccion(colegio.getDireccion());
			
			colegioUpdated = colegioService.save(colegioActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el colegio en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El colegio ha sido actualizado con éxito!");
		response.put("colegio",colegioUpdated);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
	}
	
	@DeleteMapping("/colegios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		//Map para guardar el contenido que enviaremos en el ResponseEntity con mensajes
		Map<String, Object> response = new HashMap<>();
		try {
		//Automaticamente se valida que el id del cliente existe en la BD	
		colegioService.delete(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el colegio de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El colegio eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/colegios/profesores")
	public List<Profesor> listarProfesores(){
		return colegioService.findAllProfesores();
	}
	

}

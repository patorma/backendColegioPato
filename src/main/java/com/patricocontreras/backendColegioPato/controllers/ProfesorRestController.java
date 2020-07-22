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

import com.patricocontreras.backendColegioPato.models.entity.Asignatura;
import com.patricocontreras.backendColegioPato.models.entity.Colegio;
import com.patricocontreras.backendColegioPato.models.entity.Profesor;
import com.patricocontreras.backendColegioPato.models.services.ProfesorServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProfesorRestController {
	
	@Autowired
	ProfesorServiceImpl profesorService;
	
	@GetMapping("/profesores")
	public List<Profesor> index(){
		return profesorService.findAll();
	}
	
	// muestra los profesores por paginacion
	@GetMapping("/profesores/page/{page}")
	public Page<Profesor> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return profesorService.findAll(pageable);
	}
	
	@GetMapping("profesores/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
		Profesor profesor = null;
		
		Map<String, Object> response  = new HashMap<>();
		try {
			// se busca el registro de un profesor por el id que le pasamos por id
			// y le asignamos ese valor al profesor inicializado en null
			profesor = profesorService.findById(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// si no encuentra el profesor por el id 
		if(profesor == null) {
			response.put("mensaje", "El profesor ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new  ResponseEntity<Profesor> (profesor,HttpStatus.OK);
		
	}
	
	// antes de ejecutar en todo su esplendor el metodo create se deben validar los datos
		// osea intercepta el objeto profesor y validar cada valor , cada atributo desde el
	//request body
		// se agrega @Valid y despues se inyecta al metodo create el objeto que tiene todos
		//los mensajes de error
		// donde podemos saber si tenemos algun problema que en este caso es el objeto result
	
     @PostMapping("/profesores")	
	 public ResponseEntity<?> create(@Valid @RequestBody Profesor profesor,BindingResult result){
		 
    	//El profesorNew es el nuevo profesor creado
    	 Profesor profesorNew = null;
    	 Map<String, Object> response = new HashMap<>();
    	 
    	// se valida si contiene errores el objeto 
    	 if(result.hasErrors()) {
    			// se debe obtener los mensajes de errror de cada campo 
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
    	 
    	 try {
    		 profesorNew = profesorService.save(profesor);
    	 }catch (DataAccessException e) {
    		 response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    	//se podria pasar un map con un mensaje y con el profesor creado
			response.put("mensaje", "El profesor ha sido creado con éxito! ");
			response.put("profesor", profesorNew);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	 }
     
     @PutMapping("/profesores/{id}")
     public ResponseEntity<?> update(@Valid @RequestBody Profesor profesor,BindingResult result,@PathVariable Long id){
    	//obtenemos el profesor de la bd por Id
    	 Profesor profesorActual = profesorService.findById(id);
    	 
    	//Profesor ya actualizado
    	 Profesor profesorUpdated = null;
    	 
    	 Map<String, Object> response = new HashMap<>();
    	 
    	 if(result.hasErrors()) {
 			List<String> errors = result.getFieldErrors()
 					.stream()
 					.map(err -> "El campo '"+ err.getField() + "' "+err.getDefaultMessage())
 					.collect(Collectors.toList());
 			response.put("errors", errors);
 			
 			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
         }
    	 //  si no encuentra el profesor a actualizar por id
    	 if(profesorActual == null) {
    		 response.put("mensaje", "Error: no se pudo editar, el profesor ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
    		 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
    	  }
    	 
    	 try {
    		//modificamos los datos del profesor actual con los datos del profesor que te envien
    		profesorActual.setNombre(profesor.getNombre());
    		profesorActual.setActivo(profesor.getActivo());;
    		profesorActual.setFechaNacimiento(profesor.getFechaNacimiento());
    		profesorActual.setColegio(profesor.getColegio());
    		profesorActual.setAsignatura(profesor.getAsignatura());
    		
    		profesorUpdated = profesorService.save(profesorActual);
    		 
    	 }catch(DataAccessException e) {
    		 response.put("mensaje", "Error al actualizar el profesor en la base de datos!");
 			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
 			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
    	 
    	 response.put("mensaje", "El profesor ha sido actualizado con éxito!");
    	 response.put("profesor",profesorUpdated);
    	 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
       }
     
     @DeleteMapping("/profesores/{id}")
     public ResponseEntity<?> delete(@PathVariable Long id){
    	 
    	//Map para guardar el contenido que enviaremos en el ResponseEntity con mensajes
 		Map<String, Object> response = new HashMap<>();
 		
 		try {
 			//Automaticamente se valida que el id del cliente existe en la BD
 			profesorService.delete(id);
 			
 		}catch(DataAccessException e) {
 			response.put("mensaje", "Error al eliminar al profesor de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
 		}
 		
 		response.put("mensaje", "El profesor eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
     }
     
     @GetMapping("/profesores/asignaturas")
     public List<Asignatura> listarAsignaturas(){
    	 return profesorService.findAllAsignaturas();
    	 
     }
     
     @GetMapping("profesores/colegios")
     public List<Colegio> listarColegios(){
    	 return profesorService.findAllColegios();
    	 
     }

}

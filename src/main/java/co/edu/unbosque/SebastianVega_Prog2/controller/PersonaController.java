package co.edu.unbosque.SebastianVega_Prog2.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.SebastianVega_Prog2.model.Persona;
import co.edu.unbosque.SebastianVega_Prog2.repository.PersonaRepository;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/personaapi")
public class PersonaController {
	
	@Autowired
	private PersonaRepository perrep;
	
	
	@PostMapping
	public ResponseEntity<String> agregar(@RequestParam String nombre,@RequestParam Integer cedula){
		
		Persona ag = new Persona();
		ag.setNombre(nombre);
		ag.setCedula(cedula);
		
		perrep.save(ag);
		
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body("Creado correctamente");
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
	    String name = ex.getParameterName();
	    return "Error: " + name + " es nulo";
	    // Actual exception handling
	}
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public String handleRepeatParams(SQLIntegrityConstraintViolationException ex) {
	    String name = ex.getMessage();
	    return "Error: " + name;
	    // Actual exception handling
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestParam Integer cedula1,@RequestParam String nombre,@RequestParam Integer cedula2){
		
		Optional<Persona> up = perrep.findByCedula(cedula1);
		
		if(!up.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro");
		}
		
		perrep.delete(up.get());
		
		Persona mod = new Persona();
		mod.setNombre(nombre);
		mod.setCedula(cedula2);
		
		perrep.save(mod);
		return ResponseEntity.status(HttpStatus.FOUND).body("Modificado correctamente");
		
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<Persona> buscar(@RequestParam Integer cedula1){
		
		Optional<Persona> found = perrep.findByCedula(cedula1);
		
		if(!found.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(found.get());
		
	}
	
	@GetMapping("/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam Integer cedula1){
		
		Optional<Persona> found = perrep.findByCedula(cedula1);
		
		if(!found.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro");
		}
		perrep.delete(found.get());
		return ResponseEntity.status(HttpStatus.FOUND).body("Eliminado correctamente");
		
	}
	
	@GetMapping("/lista")
	public ResponseEntity<List<Persona>> listar(){
		
		List<Persona> list = perrep.findAll();
		
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(list);
		
	}

}

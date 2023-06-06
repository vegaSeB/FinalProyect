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

import co.edu.unbosque.SebastianVega_Prog2.repository.VehiculoRepository;
import co.edu.unbosque.SebastianVega_Prog2.model.Persona;
import co.edu.unbosque.SebastianVega_Prog2.model.Vehiculo;
import co.edu.unbosque.SebastianVega_Prog2.repository.PersonaRepository;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vehiculoapi")
public class VehiculoController {

	@Autowired
	VehiculoRepository verep;

	@Autowired
	PersonaRepository perrep;

	@PostMapping
	public ResponseEntity<String> agregar(@RequestParam String placa, @RequestParam Integer cedulad) {

		Optional<Persona> dueño = perrep.findByCedula(cedulad);
		
		if(!dueño.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado (404)");
		}
		
		Vehiculo nuevo = new Vehiculo();
		
		nuevo.setPlaca(placa);
		nuevo.setIdd(dueño.get());
		
		verep.save(nuevo);
		return ResponseEntity.status(HttpStatus.CREATED).body("Creado (201)");

	}
	
	@GetMapping("/eliminar")
	public ResponseEntity<String> delete(@RequestParam String placa){
		
		Optional<Vehiculo> del = verep.findByPlaca(placa);
		
		if(!del.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
		}
		verep.delete(del.get());
		return ResponseEntity.status(HttpStatus.FOUND).body("Eliminado");
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestParam String placa1, @RequestParam Integer cedulad,@RequestParam String placa2) {

		Optional<Vehiculo> up = verep.findByPlaca(placa1);
		
		if(!up.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body("No se encontro");
		}
		
		verep.delete(up.get());

		Optional<Persona> comp = perrep.findByCedula(cedulad);

		if (comp.isPresent()) {
			
			Vehiculo na = new Vehiculo();
			
			na.setPlaca(placa2);
			na.setIdd(comp.get());
			verep.save(na);
			return ResponseEntity.status(HttpStatus.CREATED).body("Modificado");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cedula no encontrada");

	}
	
	@GetMapping("/buscar")
	public ResponseEntity<Vehiculo> buscar(@RequestParam String placa){
		
		Optional<Vehiculo> found = verep.findByPlaca(placa);
		
		if(!found.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(found.get());
		
	}
	
	@GetMapping("/lista")
	public ResponseEntity<List<Vehiculo>> listar(){
		
		List<Vehiculo> list = verep.findAll();
		
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(list);
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

}

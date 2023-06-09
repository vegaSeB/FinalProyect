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
import co.edu.unbosque.SebastianVega_Prog2.model.Parte;
import co.edu.unbosque.SebastianVega_Prog2.model.PartesBase;
import co.edu.unbosque.SebastianVega_Prog2.model.Vehiculo;
import co.edu.unbosque.SebastianVega_Prog2.repository.ParteRepository;
import co.edu.unbosque.SebastianVega_Prog2.repository.PartesBaseRepository;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/parteapi")
public class ParteController {

	@Autowired
	private ParteRepository parrep;

	@Autowired
	private VehiculoRepository autorep;

	@Autowired
	private PartesBaseRepository pbrep;

	@PostMapping
	public ResponseEntity<String> agregar(@RequestParam String placaa, @RequestParam String codigo) {


		Optional<PartesBase> pb = pbrep.findByCodigo(codigo);

		if (pb.isPresent()) {
			
			Parte np = new Parte();
			np.setCodigo(codigo);
			np.setDescripcion(pb.get().getDescripcion());
			np.setMulta(pb.get().getCosto());
			np.setInmov(pb.get().getInmoviliza());

			Optional<Vehiculo> comp = autorep.findByPlaca(placaa);

			if (comp.isPresent()) {
				np.setIdv(comp.get());
				parrep.save(np);
				return ResponseEntity.status(HttpStatus.CREATED).body("Creado (201)");
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Placa incorrecta");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Codigo incorrecto");

	}
	
	@GetMapping("/lista")
	public ResponseEntity<List<Parte>> getAll(){
		
		List<Parte> list = parrep.findAll();
		
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
		
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<Parte> buscar(@RequestParam String placav, @RequestParam String codigo){
		
		Optional<Vehiculo> found = autorep.findByPlaca(placav);
		
		if(!found.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		List<Parte> part = parrep.findByIdv(found.get());
		
		if(part.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		int i = 0;
		boolean aux = false;
		
		while(i < part.size() && !aux) {
			if(part.get(i).getCodigo().equals(codigo)) {
				aux = true;
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(part.get(i));
			}
			i++;
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestParam String placa1, @RequestParam String codigo1,@RequestParam String codigo,@RequestParam String placa2) {

		Optional<Vehiculo> comp = autorep.findByPlaca(placa1);
		
		if(!comp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro");
		}
		
		List<Parte> part = parrep.findByIdv(comp.get());
		
		if(part.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		int i = 0;
		boolean aux = false;
		
		while(i < part.size() && !aux) {
			if(part.get(i).getCodigo().equals(codigo)) {
				aux = true;
			}
			i++;
		}
		
		parrep.delete(part.get(i-1));
		
		Optional<PartesBase> pb = pbrep.findByCodigo(codigo);

		if (pb.isPresent()) {
			
			Parte np = new Parte();
			np.setCodigo(codigo);
			np.setDescripcion(pb.get().getDescripcion());
			np.setMulta(pb.get().getCosto());
			np.setInmov(pb.get().getInmoviliza());

			Optional<Vehiculo> ve = autorep.findByPlaca(placa2);

			if (ve.isPresent()) {
				np.setIdv(comp.get());
				parrep.save(np);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Modificado (202)");
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Placa incorrecta");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Codigo incorrecto");

	}
	
	@GetMapping("/eliminar")
	public ResponseEntity<String> eliminar(@RequestParam String placa, @RequestParam String codigo){
		
		Optional<Vehiculo> comp = autorep.findByPlaca(placa);
		
		if(!comp.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro");
		}
		
		List<Parte> del = parrep.findByIdv(comp.get());
		
		if(del.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro");		
		}
		
		int i = 0;
		boolean aux = false;
		
		while(i < del.size() && !aux) {
			if(del.get(i).getCodigo().equals(codigo)) {
				aux = true;
			}
			i++;
		}
		
		parrep.delete(del.get(i-1));
		return ResponseEntity.status(HttpStatus.FOUND).body("Eliminado");
		
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

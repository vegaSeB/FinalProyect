package co.edu.unbosque.SebastianVega_Prog2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping({"/index","/"})
	public String getIndex() {
		return "index";
 	}
	
	@GetMapping("/persona-ingreso")
	public String getPersonaI() {
		return "/persona-ingreso";
	}
	
	@GetMapping("/persona-modificar")
	public String getPersonaM() {
		return "persona-modificar";
	}
	
	@GetMapping("/persona-buscar")
	public String getPersonaB() {
		return "persona-buscar";
	}
	
	@GetMapping("/persona-eliminar")
	public String getPersonaE() {
		return "persona-eliminar";
	}
	
	@GetMapping("/persona-lista")
	public String getPersonaL() {
		return "persona-lista";
	}
	
	@GetMapping("/vehiculo-ingreso")
	public String getVehiculoI() {
		return "vehiculo-ingreso";
	}
	
	@GetMapping("/vehiculo-modificar")
	public String getVehiculoM() {
		return "vehiculo-modificar";
	}
	
	@GetMapping("/vehiculo-buscar")
	public String getVehiculoB() {
		return "vehiculo-buscar";
	}
	
	@GetMapping("/vehiculo-eliminar")
	public String getVehiculoE() {
		return "vehiculo-eliminar";
	}
	
	@GetMapping("/vehiculo-lista")
	public String getVehiculoaL() {
		return "vehiculo-lista";
	}
	
	@GetMapping("/parte-ingreso")
	public String getParteI() {
		return "parte-ingreso";
	}
	
	@GetMapping("/parte-modificar")
	public String getParteM() {
		return "parte-modificar";
	}
	
	@GetMapping("/parte-buscar")
	public String getParteB() {
		return "parte-buscar";
	}
	
	@GetMapping("/parte-eliminar")
	public String getParteE() {
		return "parte-eliminar";
	}
	
	@GetMapping("/parte-lista")
	public String getParteL() {
		return "parte-lista";
	}
	
}

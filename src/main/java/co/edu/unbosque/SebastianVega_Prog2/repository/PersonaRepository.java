package co.edu.unbosque.SebastianVega_Prog2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianVega_Prog2.model.Persona;

public interface PersonaRepository extends CrudRepository<Persona,Integer>{

	public Optional<Persona> findById(Integer Id);
	
	public Optional<Persona> findByCedula(Integer cedula);
	
	public List<Persona> findAll();
	
}

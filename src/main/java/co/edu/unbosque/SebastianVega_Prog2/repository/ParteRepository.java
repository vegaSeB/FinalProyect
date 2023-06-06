package co.edu.unbosque.SebastianVega_Prog2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianVega_Prog2.model.Parte;
import co.edu.unbosque.SebastianVega_Prog2.model.Vehiculo;

public interface ParteRepository extends CrudRepository<Parte,Integer>{

	public Optional<Parte> findById(Integer id);
	
	public Optional<Parte> findByCodigo(String codigo);
	
	public List<Parte> findAll();
	
	public List<Parte> findByIdv(Vehiculo idv);
	
}

package co.edu.unbosque.SebastianVega_Prog2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianVega_Prog2.model.Persona;
import co.edu.unbosque.SebastianVega_Prog2.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo,Integer>{

	public Optional<Vehiculo> findById(Integer Id);
	
	public Optional<Vehiculo> findByPlaca(String placa);
	
	public List<Vehiculo> findByIdd(Persona Idd);
	
	public List<Vehiculo> findAll();
	
}

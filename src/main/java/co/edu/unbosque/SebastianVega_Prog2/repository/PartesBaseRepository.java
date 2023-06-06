package co.edu.unbosque.SebastianVega_Prog2.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianVega_Prog2.model.PartesBase;

public interface PartesBaseRepository extends CrudRepository<PartesBase,Integer>{
public Optional<PartesBase> findByCodigo(String codigo);
}

package co.edu.unbosque.SebastianVega_Prog2.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private Integer cedula;
	
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Vehiculo> cedulad;

	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Vehiculo> getCedulad() {
		return cedulad;
	}

	public void setCedulad(Set<Vehiculo> cedulad) {
		this.cedulad = cedulad;
	}
}

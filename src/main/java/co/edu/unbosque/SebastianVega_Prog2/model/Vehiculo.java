package co.edu.unbosque.SebastianVega_Prog2.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique = true)
	private String placa;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_dueño")
	private Persona idd;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Parte> placaa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Persona getIdd() {
		return idd;
	}

	public void setIdd(Persona idd) {
		this.idd = idd;
	}

	public Set<Parte> getPlacaa() {
		return placaa;
	}

	public void setPlacaa(Set<Parte> placaa) {
		this.placaa = placaa;
	}
}

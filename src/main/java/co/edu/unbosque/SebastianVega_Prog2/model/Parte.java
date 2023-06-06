package co.edu.unbosque.SebastianVega_Prog2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Parte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	
	private String codigo;
	private String descripcion;
	private String multa;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_vehiculo")
	private Vehiculo idv;
	
	private String inmov;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMulta() {
		return multa;
	}
	public void setMulta(String multa) {
		this.multa = multa;
	}
	public String getInmov() {
		return inmov;
	}
	public void setInmov(String inmov) {
		this.inmov = inmov;
	}
	public Vehiculo getIdv() {
		return idv;
	}
	public void setIdv(Vehiculo idv) {
		this.idv = idv;
	}
	
}

package co.edu.unbosque.SebastianVega_Prog2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PartesBase {

	@Id
	Integer est_id;
	
	String codigo;
	String descripcion;
	String costo;
	String inmoviliza;
	
	public Integer getEst_id() {
		return est_id;
	}
	public void setEst_id(Integer est_id) {
		this.est_id = est_id;
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
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}
	public String getInmoviliza() {
		return inmoviliza;
	}
	public void setInmoviliza(String inmoviliza) {
		this.inmoviliza = inmoviliza;
	}
	
}

package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity

public class Imovel extends Model {
	
	public int codigoAnuncio;
	public String bairro;
	public int quantidadeComodos;
	public double areaInterna;
	public double areaExterna;
	public String tipoImovel;
	
	@ManyToOne
	@JoinColumn(name="idtipoimovel")
	public TipoImovel tipoimovel;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Imovel() {
		this.status= Status.ATIVO;
	}
	
	

}

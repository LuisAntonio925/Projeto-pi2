package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity

public class TipoImovel extends Model {
	
	public String descricao;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	

}

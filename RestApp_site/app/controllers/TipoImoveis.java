package controllers;

import java.util.List;

import models.Imovel;
import models.Status;
import models.TipoImovel;
import play.mvc.Controller;

public class TipoImoveis extends Controller{
	
	public static void form() {
		render();
	}
	
	public static void listar(String termo) {
	
		  List<TipoImovel> tiposImoveis = null;
	        if (termo == null) {
	            tiposImoveis = TipoImovel.find("status <> ?1", Status.INATIVO).fetch();
	        } else {
	            tiposImoveis = TipoImovel.find("lower(descricao) like ?1 and status <> ?2",
	                                        "%" + termo.toLowerCase() + "%",
	                                        Status.INATIVO).fetch();
	        }
	        render(tiposImoveis, termo);
	}
	
	 public static void detalhar(Long id) {
	        TipoImovel tipoImovel = TipoImovel.findById(id);
	        render(tipoImovel);
	    }
	    
	    public static void editar(Long id) {
	        TipoImovel tImovel = TipoImovel.findById(id);
	        renderTemplate("TipoImoveis/form.html", tImovel);
	    }
	    
	    public static void salvar(TipoImovel tipoImovel) {
	    	 // 🔍 Verifica se já existe um tipo de imóvel com a mesma descrição (ignorando maiúsculas/minúsculas),
	        // e com ID diferente (para não acusar duplicidade ao editar)
	        TipoImovel existente = TipoImovel.find(
	            "lower(descricao) = ?1 and id <> ?2",
	            tipoImovel.descricao.toLowerCase(),
	            tipoImovel.id == null ? 0 : tipoImovel.id
	        ).first();

	        // 🚫 Se já existir, exibe mensagem de erro e volta para o formulário
	        if (existente != null) {
	            flash.error("Já existe um tipo de imóvel com essa descrição!");
	            renderTemplate("TipoImoveis/form.html", tipoImovel);
	        }
	        tipoImovel.save();
	        flash.success("Tipo de imóvel cadastrado com sucesso!");
	        detalhar(tipoImovel.id);
	    }
	    
	    public static void remover(Long id) {
	        TipoImovel tImovel = TipoImovel.findById(id);
	        if (tImovel != null) {
	            tImovel.status = Status.INATIVO;
	            tImovel.save();
	        }
	        listar(null);
	    }
	




}

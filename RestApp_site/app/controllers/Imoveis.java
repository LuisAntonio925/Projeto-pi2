package controllers;

import java.util.List;

import models.Imovel;
import models.Status;
import models.TipoImovel;
import play.mvc.Controller;

public class Imoveis extends Controller{
	
	public static void form() {
		List<TipoImovel> tiposImoveis = TipoImovel.findAll();
		render(tiposImoveis);
		
	}
	
	public static void listar(String termo) {
		List<Imovel> listaImovel = null;
		
		if (termo == null) {
			listaImovel = Imovel.find("status <> ?1", Status.INATIVO).fetch();	
		} else {
			listaImovel = Imovel.find("(lower(bairro) like ?1" +"or lower(tipoImovel) like ?1) and status <> ?2",
					"%" + termo.toLowerCase() + "%",
					Status.INATIVO).fetch();
		}
		render(listaImovel, termo);
	
	}
	public static void salvar(Imovel imovel) {
		  // Verifica se já existe um imóvel com o mesmo código de anúncio.
	    // A cláusula "id <> ?2" garante que não estamos comparando com o próprio imóvel durante uma edição.
	    Imovel imovelExistente = Imovel.find(
	        "codigoAnuncio = ?1 and id <> ?2",
	        imovel.codigoAnuncio,                      // código do imóvel sendo cadastrado
	        imovel.id == null ? 0 : imovel.id          // se for novo (id == null), passa 0 só para evitar erro
	    ).first();

	    // 🚫 Se já existir outro imóvel com esse mesmo código, impede o cadastro
	    if (imovelExistente != null) {
	        // Exibe uma mensagem de erro
	        flash.error("Já existe um imóvel com esse código de anúncio!");
	        
	        // Recarrega os tipos de imóvel para o formulário
	        List<TipoImovel> tiposImoveis = TipoImovel.findAll();
	        
	        // Reabre o formulário com os dados preenchidos e o erro
	        renderTemplate("Imoveis/form.html", imovel, tiposImoveis);
	    }
		
		
		imovel.save();
		
		flash.success("Cadastrado com Suceeso!!!");
		listar(null);
		
	}
	public static void remover(long id) {
		
		Imovel imov = Imovel.findById(id);
		imov.status = Status.INATIVO;
		imov.save();
		flash.success("Removido com Suceeso!!!");
		listar(null);
		
	}
	public static void editar(long id) {
		Imovel imov = Imovel.findById(id);
		List<TipoImovel> tiposImoveis = TipoImovel.findAll();
		renderTemplate("Imoveis/form.html", imov, tiposImoveis);
		
	}
	

}

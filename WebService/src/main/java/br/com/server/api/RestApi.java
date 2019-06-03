package br.com.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.server.model.IngredienteValor;
import br.com.server.model.Lanche;
import br.com.server.model.Pedido;
import br.com.server.model.TipoLanche;
import br.com.server.service.Service;

/**
 * Classe responsável pelas API´s Http requisitadas pelo cliente, interagindo os serviços do servidor.
 * 
 * @author Wesley de Araújo Barros
 */

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestApi {
	
	private IngredienteValor ingredienteValor = new IngredienteValor();
	private final Service service = new Service(ingredienteValor);
	
	@GET
	@Path("/calcular-lanche/{tipo}")
	public Lanche calcularLanche(@PathParam("tipo") TipoLanche tipo) {
		return service.calcularLanche(tipo);
	}
	
	@POST
	@Path("/calcular-pedido")
	@Consumes(MediaType.APPLICATION_JSON)
	public Pedido calcularPedido(Pedido pedido) {
		return service.calcularPedido(pedido);
	}
}

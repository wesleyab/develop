package br.com.server.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe responsável pelas informações do pedido.
 * 
 * @author Wesley de Araújo Barros
 */
public class Pedido {
	
	public Pedido() {
	}

	public TipoLanche lanche;
	public List<Adicional> adicionais;
	private BigDecimal total;

	public TipoLanche getLanche() {
		return lanche;
	}

	public void setLanche(TipoLanche lanche) {
		this.lanche = lanche;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}

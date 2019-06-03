package br.com.server.model;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Classe responsável por armazanar os ingredientes adicionais.
 * 
 * @author Wesley de Araújo Barros
 */
public class Adicional {
	
	public static HashMap<TipoIngrediente, BigDecimal> adValores = new HashMap<>();
	
	public TipoIngrediente ingrediente;
	public Integer qtde;
	
	public Adicional() {
	}

	public TipoIngrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(TipoIngrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

}

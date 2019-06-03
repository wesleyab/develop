package br.com.server.model;

import java.math.BigDecimal;

/**
 * Classe responsável pelas informações do lanche.
 * 
 * @author Wesley de Araújo Barros
 */
public class Lanche implements Comparable<Lanche>{
	
	public String descricao;
	public TipoLanche tipo;
	public BigDecimal valor;
	
	public Lanche() {
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoLanche getTipo() {
		return tipo;
	}

	public void setTipo(TipoLanche tipo) {
		this.tipo = tipo;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(Lanche o) {
		return this.descricao.compareTo(o.descricao);
	}
	

}

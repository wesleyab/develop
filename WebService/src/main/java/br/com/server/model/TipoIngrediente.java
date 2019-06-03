package br.com.server.model;

import java.math.BigDecimal;

/**
 * Enumeração dos tipos de ingredientes.
 * 
 * @author Wesley de Araújo Barros
 */
public enum TipoIngrediente {
	
	ALFACE,
	
	BACON,
	
	BURGER,
	
	OVO,
	
	QUEIJO;
	
	public static String nome(TipoIngrediente tipo) {
		switch (tipo) {
		case ALFACE:
			return "Alface";
		case BACON:
			return "Bacon";
		case BURGER:
			return "Hambúrguer de carne";
		case OVO:
			return "Ovo";
		case QUEIJO:
			return "Queijo";
		default:
			break;
		}
		return null;
	}
}

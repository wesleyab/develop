package br.com.server.model;

import java.math.BigDecimal;

public class IngredienteValor {
	
	public BigDecimal valor(TipoIngrediente tipo) {
		switch (tipo) {
		case ALFACE:
			return new BigDecimal(0.40);
		case BACON:
			return new BigDecimal(2.00);
		case BURGER:
			return new BigDecimal(3.00);
		case OVO:
			return new BigDecimal(0.80);
		case QUEIJO:
			return new BigDecimal(1.50);
		default:
			break;
		}
		return BigDecimal.ZERO;
	}

}

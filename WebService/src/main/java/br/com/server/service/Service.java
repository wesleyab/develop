package br.com.server.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.server.model.Adicional;
import br.com.server.model.IngredienteValor;
import br.com.server.model.Lanche;
import br.com.server.model.Pedido;
import br.com.server.model.TipoIngrediente;
import br.com.server.model.TipoLanche;

/**
 * Classe responsável pelos serviços de inteligência para gerir as regras de
 * negócio da aplicação.
 * 
 * @author Wesley de Araújo Barros
 */
public class Service {
	
	private IngredienteValor ingredienteValor;
	
	public Service(IngredienteValor ingredienteValor) {
		this.ingredienteValor = ingredienteValor;
	}

	/**
	 * Calcula o valor do lanche perante seus respectivos ingredientes.
	 * 
	 * @param tipoLanche
	 *            {@link TipoLanche} Tipo do lanche pedido.
	 * @return {@Lanche} Contendo o valor calculado por seus ingredientes.
	 */
	public Lanche calcularLanche(TipoLanche tipoLanche) {
		Lanche lanche = new Lanche();

		List<TipoIngrediente> ingredientesLanche = new ArrayList<>();
		ingredientesLanche.addAll(TipoLanche.ingredientes(tipoLanche));

		lanche.setValor(calcularTotal(ingredientesLanche, false));

		return lanche;
	}

	/**
	 * Serviço responsável por preparar os ingredientes do pedido e o valor
	 * final
	 * 
	 * @param {@link
	 * 			Pedido} Objeto com as informações do pedido.
	 * @return {@link Pedido} Objeto com o valor total do pedido.
	 */
	public Pedido calcularPedido(Pedido pedido) {
		Pedido resultado = new Pedido();
		boolean adicional = false;

		if (pedido == null) {
			return resultado;
		}

		List<TipoIngrediente> ingredientesLanche = new ArrayList<>();
		// Deve conter um lanche para efetuar o pedido.
		if (pedido.getLanche() != null) {
			ingredientesLanche.addAll(TipoLanche.ingredientes(pedido.getLanche()));

			// Adiciona os ingredientes extras ao lanche pedido.
			if (pedido.adicionais != null && !pedido.adicionais.isEmpty()) {
				adicional = true;
				for (Adicional extra : pedido.adicionais) {
					for (int i = 0; i < extra.qtde; i++) {
						ingredientesLanche.add(extra.ingrediente);
					}
				}
			}
			resultado.setTotal(calcularTotal(ingredientesLanche, adicional));
		}

		return resultado;
	}

	/**
	 * Calculo do valor total do pedido.
	 * 
	 * @param ingredientesLanche
	 *            Lista de {@link TipoIngrediente} ingredientes do lanche
	 *            pedido.
	 * @param adicional
	 *            Indicativo se o pedido possui adicionais.
	 * @return Valor calculado.
	 */
	public BigDecimal calcularTotal(List<TipoIngrediente> ingredientesLanche, boolean adicional) {
		BigDecimal total = BigDecimal.ZERO;

		for (TipoIngrediente ingrediente : ingredientesLanche) {
			total = total.add(ingredienteValor.valor(ingrediente));
		}

		// Caso o possua ingredientes adicionais, verificar e calcular as
		// promoções a seguir
		if (adicional) {
			// Calcular promoções: MUITA CARNE e MUITO QUEIJO
			total = muitaCarneOuQueijo(ingredientesLanche, total);
			// Calcular promoção:LIGHT
			total = light(ingredientesLanche, total);
		}
		return total.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Calculo da promoção muita carne ou queijo: A cada 3 porções desses
	 * ingredientes o cliente só paga 2. Se o lanche tiver 6 porções, o cliente
	 * pagará 4. Assim por diante...
	 * 
	 * @param ingredientesLanche
	 *            Lista de {@link TipoIngrediente} ingredientes do lanche
	 *            pedido.
	 * @param total
	 *            Valor total do pedido.
	 * @return Valor calculado pela promoção.
	 */
	private BigDecimal muitaCarneOuQueijo(List<TipoIngrediente> ingredientesLanche, BigDecimal total) {
		int carne = 0;
		int queijo = 0;

		for (TipoIngrediente ingrediente : ingredientesLanche) {
			if (ingrediente == TipoIngrediente.BURGER) {
				++carne;
			}
			if (ingrediente == TipoIngrediente.QUEIJO) {
				++queijo;
			}
		}

		// A cada 3 ingredientes de carne ou queijo um é grátis, então aplica-se
		// o desconto sobre o valor
		BigDecimal descCarne = new BigDecimal(carne / 3).setScale(1, RoundingMode.DOWN);
		BigDecimal descQueijo = new BigDecimal(queijo / 3).setScale(1, RoundingMode.DOWN);
		BigDecimal desconto = ingredienteValor.valor(TipoIngrediente.BURGER).multiply(descCarne)
				.add(ingredienteValor.valor(TipoIngrediente.QUEIJO).multiply(descQueijo));

		return total.subtract(desconto);
	}

	/**
	 * Calculo da promoção light, se o lanche tem alface e não tem bacon, ganha
	 * 10% de desconto
	 * 
	 * @param ingredientesLanche
	 *            Lista de {@link TipoIngrediente} ingredientes do lanche
	 *            pedido.
	 * @param total
	 *            Valor total do pedido.
	 * @return Valor calculado pela promoção.
	 */
	private BigDecimal light(List<TipoIngrediente> ingredientesLanche, BigDecimal total) {
		BigDecimal desconto = BigDecimal.ZERO;
		boolean alface = false;
		boolean bacon = false;

		// Verifico se o lanche tem ingredientes do tipo TipoIngrediente.ALFACE
		// e TipoIngrediente.BACON
		for (TipoIngrediente ingrediente : ingredientesLanche) {
			if (ingrediente == TipoIngrediente.ALFACE) {
				alface = true;
			}
			if (ingrediente == TipoIngrediente.BACON) {
				bacon = true;
			}
		}

		// Caso o pedido possui o ingrediente alface e não tem bacon, aplica-se
		// o desconto de 10% sobre o valor
		if (alface && !bacon) {
			desconto = total.multiply(new BigDecimal(10)).divide(new BigDecimal(100));
		}
		return total.subtract(desconto);
	}
}

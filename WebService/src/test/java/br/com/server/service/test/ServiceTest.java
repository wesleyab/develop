package br.com.server.service.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.server.model.Adicional;
import br.com.server.model.IngredienteValor;
import br.com.server.model.Lanche;
import br.com.server.model.Pedido;
import br.com.server.model.TipoIngrediente;
import br.com.server.model.TipoLanche;
import br.com.server.service.Service;

/**
 * Classe responsável pela cobertura de testes automatizados dos serviços da
 * classe {@link Service}.
 * 
 * @author Wesley de Araújo Barros
 */
public class ServiceTest {

	@Mock
	private IngredienteValor ingredienteValor;
	
	private Service service;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new Service(ingredienteValor);
		mockValoresIngredientes();
	}
	
	/**
	 * Mock do valor dos ingredientes, para que a inflação não influencie nos testes automatizados.
	 */
	private void mockValoresIngredientes() {
		Mockito.when(ingredienteValor.valor(TipoIngrediente.ALFACE)).thenReturn(new BigDecimal(0.40));
		Mockito.when(ingredienteValor.valor(TipoIngrediente.BACON)).thenReturn(new BigDecimal(2.00));
		Mockito.when(ingredienteValor.valor(TipoIngrediente.BURGER)).thenReturn(new BigDecimal(3.00));
		Mockito.when(ingredienteValor.valor(TipoIngrediente.OVO)).thenReturn(new BigDecimal(0.80));
		Mockito.when(ingredienteValor.valor(TipoIngrediente.QUEIJO)).thenReturn(new BigDecimal(1.50));
	}
	
	// Teste para calcular o valor do lanche solicitado
	@Test
	public void calcularLanche() {
		TipoLanche xBacon = TipoLanche.XBACON;
		TipoLanche xBurger = TipoLanche.XBURGER;
		TipoLanche xEgg = TipoLanche.XEGG;
		TipoLanche xEggBacon = TipoLanche.XEGGBACON;
		
		BigDecimal xBaconValor = new BigDecimal(6.50).setScale(2, RoundingMode.HALF_EVEN);
		Lanche actual = service.calcularLanche(xBacon);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(xBaconValor, actual.getValor());
		
		BigDecimal xBurgerValor = new BigDecimal(4.50).setScale(2, RoundingMode.HALF_EVEN);
		actual = service.calcularLanche(xBurger);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(xBurgerValor, actual.getValor());
		
		BigDecimal xEggValor = new BigDecimal(5.30).setScale(2, RoundingMode.HALF_EVEN);
		actual = service.calcularLanche(xEgg);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(xEggValor, actual.getValor());
		
		BigDecimal xEggBaconValor = new BigDecimal(7.30).setScale(2, RoundingMode.HALF_EVEN);
		actual = service.calcularLanche(xEggBacon);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(xEggBaconValor, actual.getValor());
	}

	// Teste para calcular valor do pedido sem promoções
	@Test
	public void calcularPedido() {
		Pedido pedido = new Pedido();
		pedido.setLanche(TipoLanche.XBURGER);

		Adicional ad1 = new Adicional();
		ad1.setIngrediente(TipoIngrediente.OVO);
		ad1.qtde = 1;
		Adicional ad2 = new Adicional();
		ad2.setIngrediente(TipoIngrediente.BACON);
		ad2.qtde = 2;
		pedido.adicionais = Arrays.asList(ad1, ad2);

		BigDecimal expected = new BigDecimal(9.30).setScale(2, RoundingMode.HALF_EVEN);

		Pedido actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
		
		// Sem adicionais
		pedido.adicionais = null;
		expected = new BigDecimal(4.50).setScale(2, RoundingMode.HALF_EVEN);

		actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
	}

	// Testes para o calculo da promoção: Light
	@Test
	public void calcularPedido_light() {
		Pedido pedido = new Pedido();
		pedido.setLanche(TipoLanche.XBURGER);

		Adicional ad = new Adicional();
		ad.setIngrediente(TipoIngrediente.ALFACE);
		ad.qtde = 2;
		pedido.adicionais = Arrays.asList(ad);

		BigDecimal expected = new BigDecimal(4.77).setScale(2, RoundingMode.HALF_EVEN);

		Pedido actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
	}

	// Testes para o calculo da promoção: Muita carne
	@Test
	public void calcularPedido_MuitaCarne() {
		Pedido pedido = new Pedido();
		pedido.setLanche(TipoLanche.XBACON);

		Adicional ad = new Adicional();
		ad.setIngrediente(TipoIngrediente.BURGER);
		ad.qtde = 2;
		pedido.adicionais = Arrays.asList(ad);

		BigDecimal expected = new BigDecimal(9.50).setScale(2, RoundingMode.HALF_EVEN);

		Pedido actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());

		ad.qtde = 6;
		pedido.adicionais = Arrays.asList(ad);
		expected = new BigDecimal(18.50).setScale(2, RoundingMode.HALF_EVEN);

		actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
	}

	// Testes para o calculo da promoção: Muito queijo
	@Test
	public void calcularPedido_MuitoQueijo() {
		Pedido pedido = new Pedido();
		pedido.setLanche(TipoLanche.XEGG);

		Adicional ad = new Adicional();
		ad.setIngrediente(TipoIngrediente.QUEIJO);
		ad.qtde = 2;
		pedido.adicionais = Arrays.asList(ad);

		BigDecimal expected = new BigDecimal(6.80).setScale(2, RoundingMode.HALF_EVEN);

		Pedido actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());

		ad.qtde = 6;
		pedido.adicionais = Arrays.asList(ad);
		expected = new BigDecimal(11.30).setScale(2, RoundingMode.HALF_EVEN);

		actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
	}

	// Testes para o calculo de todas as promoções
	@Test
	public void calcularPedido_TodasPromocoes() {
		Pedido pedido = new Pedido();
		pedido.setLanche(TipoLanche.XEGG);

		Adicional ad1 = new Adicional();
		ad1.setIngrediente(TipoIngrediente.BURGER);
		ad1.qtde = 3;
		Adicional ad2 = new Adicional();
		ad2.setIngrediente(TipoIngrediente.ALFACE);
		ad2.qtde = 2;
		Adicional ad3 = new Adicional();
		ad3.setIngrediente(TipoIngrediente.QUEIJO);
		ad3.qtde = 5;
		pedido.adicionais = Arrays.asList(ad1, ad2, ad3);

		BigDecimal expected = new BigDecimal(14.94).setScale(2, RoundingMode.HALF_EVEN);

		Pedido actual = service.calcularPedido(pedido);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(expected, actual.getTotal());
	}
}

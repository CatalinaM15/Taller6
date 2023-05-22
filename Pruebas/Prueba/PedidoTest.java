package Prueba;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Exception.RestriccionPrecioException;
import procesamiento.Ingrediente;
import procesamiento.Pedido;
import procesamiento.ProductoAjustado;
import procesamiento.ProductoMenu;

class PedidoTest {

	@Test
	void testAgregarProducto_SinRestriccion() throws RestriccionPrecioException {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);

		assertEquals(2, pedido.getItemsPedido().size());
		assertEquals(item1, pedido.getItemsPedido().get(0));
		assertEquals(item2, pedido.getItemsPedido().get(1));
	}

	@Test
	void testAgregarProducto_ConRestriccion() {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 1000);

		RestriccionPrecioException exception = assertThrows(RestriccionPrecioException.class, () -> {
			pedido.agregarProducto(item1);
			pedido.agregarProducto(item2);
		});

		assertEquals("El precio total del pedido supera el límite permitido.", exception.getMessage());
		assertEquals(item1, exception.getProducto());
		assertEquals(1, pedido.getItemsPedido().size());
		assertEquals(item1, pedido.getItemsPedido().get(0));
	}

	@Test
	void testAgregarModificado() {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
		ArrayList<Ingrediente> ingredientesA = new ArrayList<>();
		ingredientesA.add(new Ingrediente("Queso", 10));
		ingredientesA.add(new Ingrediente("Tomate", 5));
		ArrayList<Ingrediente> ingredientesE = new ArrayList<>();
		ingredientesE.add(new Ingrediente("Cebolla", 2));

		pedido.agregarModificado(item1, ingredientesA, ingredientesE);

		assertEquals(1, pedido.getItemsPedido().size());
		assertTrue(pedido.getItemsPedido().get(0) instanceof ProductoAjustado);
	}

	@Test
	void testGenerarTextoFactura() throws RestriccionPrecioException {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);

		String factura = pedido.generarTextoFactura();

		String expected = "ID No. 1\n" + "Nombre Cliente: Cliente 1\n" + "Direccion Cliente: Dirección 1\n"
				+ "Productos:\n" + "Hamburguesa - $100\n" + "Papas Fritas - $50\n" + "Precio neto: $150\n"
				+ "IVA: $28.50\n" + "Total: $178";

		assertEquals(expected, factura);
	}

	@Test
	void testGuardarFactura() throws RestriccionPrecioException {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);

		// Realizar la prueba sin verificar la escritura en archivo
		pedido.guardarFactura();
	}

}

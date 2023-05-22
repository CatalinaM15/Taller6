package Prueba;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100000);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 10000);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);

		assertEquals(2, pedido.getItemsPedido().size());
		assertEquals(item1, pedido.getItemsPedido().get(0));
		assertEquals(item2, pedido.getItemsPedido().get(1));
	}

	@Test
	void testAgregarProducto_ConRestriccion() {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 50000);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 300000);

		RestriccionPrecioException exception = assertThrows(RestriccionPrecioException.class, () -> {
			pedido.agregarProducto(item1);
			pedido.agregarProducto(item2);
		});

		assertEquals("El Producto " +item2.getNombre()+ " no ha sido posible de agregar al pedido ya que superan los 150.000", exception.getMessage());
		assertEquals(item2, exception.getProducto());
		assertEquals(1, pedido.getItemsPedido().size());
	}

	@Test
	void testAgregarModificado() {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100000);
		ArrayList<Ingrediente> ingredientesA = new ArrayList<>();
		ingredientesA.add(new Ingrediente("Queso", 10000));
		ingredientesA.add(new Ingrediente("Tomate", 5000));
		ArrayList<Ingrediente> ingredientesE = new ArrayList<>();
		ingredientesE.add(new Ingrediente("Cebolla", 2000));

		pedido.agregarModificado(item1, ingredientesA, ingredientesE);

		assertEquals(1, pedido.getItemsPedido().size());
		assertTrue(pedido.getItemsPedido().get(0) instanceof ProductoAjustado);
	}

	@Test
	void testGenerarTextoFactura() throws RestriccionPrecioException {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100000);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 10000);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);

		String factura = pedido.generarTextoFactura();

		String expected = "ID No. 1\n" + "Nombre Cliente: Cliente 1\n" + "Direccion Cliente: Dirección 1\n"
				+ "Productos:\n" + "Hamburguesa - $100000\n" + "Papas Fritas - $10000\n" + "Precio neto: $110000\n"
				+ "IVA: $20900,00\n" + "Total: $130900";

		assertEquals(expected, factura);
	}

	@Test
	void testGuardarFactura() throws RestriccionPrecioException, FileNotFoundException, IOException {
		Pedido pedido = new Pedido("Cliente 1", "Dirección 1");
		ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100000);
		ProductoMenu item2 = new ProductoMenu("Papas Fritas", 10000);

		pedido.agregarProducto(item1);
		pedido.agregarProducto(item2);
		pedido.guardarFactura();

		// Leer el contenido del archivo de factura
		String archivoFactura = "data/facturas.txt";
        String ultimoTramo = "";
        String linea;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoFactura))) {
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("ID No.")) {
                    ultimoTramo = "";
                }
                ultimoTramo += linea + "\n";
            }
        }

			// Verificar si el contenido del archivo es el esperado
			String facturaEsperada =  "ID No. 1\n" + "Nombre Cliente: Cliente 1\n" + "Direccion Cliente: Dirección 1\n"
					+ "Productos:\n" + "Hamburguesa - $100000\n" + "Papas Fritas - $10000\n" + "Precio neto: $110000\n"
					+ "IVA: $20900,00\n" + "Total: $130900;\n"; // Coloca aquí el contenido esperado de la factura
			assertEquals(facturaEsperada, ultimoTramo);

		}

	}



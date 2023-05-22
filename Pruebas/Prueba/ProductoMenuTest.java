package Prueba;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import procesamiento.ProductoMenu;

class ProductoMenuTest {

	@Test
	public void testGetNombre() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100);
	    String nombre = productoMenu.getNombre();
	    assertEquals("Hamburguesa", nombre);
	}

	@Test
	public void testGetPrecio() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100);
	    int precio = productoMenu.getPrecio();
	    assertEquals(100, precio);
	}

	@Test
	public void testGenerarTextoFactura() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100);
	    String textoFactura = productoMenu.generarTextoFactura();
	    assertEquals("Hamburguesa - $100\n", textoFactura);
	}


}

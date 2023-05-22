package Prueba;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import procesamiento.ProductoMenu;

class ProductoMenuTest {

	@Test
	public void testGetNombre() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100000);
	    String nombre = productoMenu.getNombre();
	    assertEquals("Hamburguesa", nombre);
	}

	@Test
	public void testGetPrecio() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100000);
	    long precio = productoMenu.getPrecio();
	    assertEquals(100000, precio);
	}

	@Test
	public void testGenerarTextoFactura() {
	    ProductoMenu productoMenu = new ProductoMenu("Hamburguesa", 100000);
	    String textoFactura = productoMenu.generarTextoFactura();
	    assertEquals("Hamburguesa - $100000\n", textoFactura);
	}


}

package Prueba;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import procesamiento.Ingrediente;
import procesamiento.ProductoAjustado;
import procesamiento.ProductoMenu;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductoAjustadoTest {
	@Test
	public void testGetPrecio_BaseSinAgregados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    int precio = productoAjustado.getPrecio();

	    assertEquals(100, precio);
	}

	@Test
	public void testGetPrecio_BaseConAgregados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<>();
	    ingredientesAgregados.add(new Ingrediente("Queso", 10));
	    ingredientesAgregados.add(new Ingrediente("Tomate", 5));

	    productoAjustado.agregarProductos(ingredientesAgregados, new ArrayList<>());

	    int precio = productoAjustado.getPrecio();

	    assertEquals(115, precio);
	}

	@Test
	public void testGenerarTextoFactura_BaseSinAgregadosNiEliminados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    String textoFactura = productoAjustado.generarTextoFactura();

	    String expected = "Hamburguesa - $100\nIngredientes agregados: \n";
	    assertEquals(expected, textoFactura);
	}

	@Test
	public void testGenerarTextoFactura_BaseConAgregadosYEliminados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<>();
	    ingredientesAgregados.add(new Ingrediente("Queso", 10));
	    ingredientesAgregados.add(new Ingrediente("Tomate", 5));

	    ArrayList<Ingrediente> ingredientesEliminados = new ArrayList<>();
	    ingredientesEliminados.add(new Ingrediente("Cebolla", 3));

	    productoAjustado.agregarProductos(ingredientesAgregados, ingredientesEliminados);

	    String textoFactura = productoAjustado.generarTextoFactura();

	    String expected = "Hamburguesa - $115\nIngredientes eliminados: Cebolla\nIngredientes agregados: \n" +
	            "Queso - Precio Adicional: $10\nTomate - Precio Adicional: $5\n";
	    assertEquals(expected, textoFactura);
	}

}

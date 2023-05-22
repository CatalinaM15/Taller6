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
	public void testGetPrecio_SinAgregados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100000);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    long precio = productoAjustado.getPrecio();

	    assertEquals(100000, precio);
	}

	@Test
	public void testGetPrecio_ConAgregados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100000);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<>();
	    ingredientesAgregados.add(new Ingrediente("Queso", 10000));
	    ingredientesAgregados.add(new Ingrediente("Tomate", 5000));

	    productoAjustado.agregarProductos(ingredientesAgregados, new ArrayList<>());

	    long precio = productoAjustado.getPrecio();

	    assertEquals(115000, precio);
	}

	@Test
	public void testGenerarTextoFactura_SinAgregadosNiEliminados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100000);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    String textoFactura = productoAjustado.generarTextoFactura();

	    String expected = "Hamburguesa - $100000\n";
	    assertEquals(expected, textoFactura);
	}

	@Test
	public void testGenerarTextoFactura_ConAgregadosYEliminados() {
	    ProductoMenu base = new ProductoMenu("Hamburguesa", 100000);
	    ProductoAjustado productoAjustado = new ProductoAjustado(base);

	    ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<>();
	    ingredientesAgregados.add(new Ingrediente("Queso", 10000));
	    ingredientesAgregados.add(new Ingrediente("Tomate", 5000));

	    ArrayList<Ingrediente> ingredientesEliminados = new ArrayList<>();
	    ingredientesEliminados.add(new Ingrediente("Cebolla", 3000));

	    productoAjustado.agregarProductos(ingredientesAgregados, ingredientesEliminados);

	    String textoFactura = productoAjustado.generarTextoFactura();

	    String expected = "Hamburguesa - $115000\nIngredientes eliminados: \nCebolla\nIngredientes agregados: \n" +
	            "Queso - Precio Adicional: $10000\nTomate - Precio Adicional: $5000\n";
	    assertEquals(expected, textoFactura);
	}

}

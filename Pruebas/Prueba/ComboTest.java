package Prueba;

import org.junit.jupiter.api.Test;

import procesamiento.Combo;
import procesamiento.ProductoMenu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComboTest {@Test
public void testGetNombre() {
    Combo combo = new Combo("Combo 1", 0.1);

    String nombre = combo.getNombre();

    assertEquals("Combo 1", nombre);
}

@Test
public void testAgregarItemACombo() {
    Combo combo = new Combo("Combo 1", 0.1);
    ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
    ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

    combo.agregarItemACombo(item1);
    combo.agregarItemACombo(item2);

    // Verificar que los items se hayan agregado correctamente
    assertEquals(2, combo.getItemsCombo().size());
    assertEquals(item1, combo.getItemsCombo().get(0));
    assertEquals(item2, combo.getItemsCombo().get(1));
}

@Test
public void testGetPrecio_SinDescuento() {
    Combo combo = new Combo("Combo 1", 0.0);
    ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
    ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

    combo.agregarItemACombo(item1);
    combo.agregarItemACombo(item2);

    int precio = combo.getPrecio();

    // El precio debe ser la suma de los precios de los items
    assertEquals(150, precio);
}

@Test
public void testGetPrecio_ConDescuento() {
    Combo combo = new Combo("Combo 1", 0.2);
    ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
    ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

    combo.agregarItemACombo(item1);
    combo.agregarItemACombo(item2);

    int precio = combo.getPrecio();

    // El precio debe ser la suma de los precios de los items con el descuento aplicado
    assertEquals(120, precio);
}

@Test
public void testGenerarTextoFactura() {
    Combo combo = new Combo("Combo 1", 0.1);
    ProductoMenu item1 = new ProductoMenu("Hamburguesa", 100);
    ProductoMenu item2 = new ProductoMenu("Papas Fritas", 50);

    combo.agregarItemACombo(item1);
    combo.agregarItemACombo(item2);

    String textoFactura = combo.generarTextoFactura();

    String expected = "Combo 1 - $120\n";
    assertEquals(expected, textoFactura);
}

}

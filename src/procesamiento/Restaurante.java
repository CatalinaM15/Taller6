package procesamiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exception.IngredienteRepetidoException;
import Exception.ProductoRepetidoException;
import Exception.RestriccionPrecioException;

public class Restaurante {
//	private List<Ingrediente> ingredientes;
	private Map<Integer, Ingrediente> ingr;
	private Map<Integer, ProductoMenu> menuBase;
	private Pedido pedidoEnCurso;
	private List<Pedido> pedidos;
	private List<Combo> combos;

	public Restaurante() {
//		ingredientes = new ArrayList<Ingrediente>();
		ingr = new HashMap<Integer, Ingrediente>();
		menuBase = new HashMap<Integer, ProductoMenu>();
		pedidos = new ArrayList<Pedido>();
		combos = new ArrayList<Combo>();
	}

	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		if (pedidoEnCurso != null) {
			pedidos.add(pedidoEnCurso);
		}

		pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);

	}

	public void cerrarYGuardarPedido0() throws IOException {
		pedidoEnCurso.guardarFactura();
	}

	public Pedido getPedidoEnCurso0() {
		return pedidoEnCurso;

	}

	public Map<Integer, ProductoMenu> getMenuBase0() {
		return menuBase;
	}

	public Pedido getPedidoPorID(int id) {
		for (Pedido pedido2 : pedidos) {
			int result = pedido2.getidPedido0();
			if (result == id) {
				return pedido2;
			}
		}
		return null;
	}

	public ProductoMenu seleccionarProductoMenu(int opcion) {
		if (opcion >= 1 && opcion <= menuBase.size()) {
			return menuBase.get(opcion - 1);
		} else {
			return null;
		}
	}

	public void cargarinformacionRestaurante() {
		try {
			cargaringredientes();
		} catch (IngredienteRepetidoException e) {

			Ingrediente ingred = e.getItem();
			int numero = buscarIngredientePorNombreInt(ingred.getNombre());
			this.eliminarItemrepetido("data/ingredientes.txt", numero);

		}
		try {

			cargarMenu();

		} catch (ProductoRepetidoException e) {
			Producto productoRepetido = e.getProducto();
			int numero = buscarProductoInteger(productoRepetido.getNombre());
			this.eliminarProductorepetido("data/menu.txt", numero);

		}
		cargarCombos();

	}

	public void eliminarItemrepetido(String nombreArchivo, int numeroLinea) {
		eliminarLinea(nombreArchivo, numeroLinea);
		try {
			cargaringredientes();
		} catch (IngredienteRepetidoException e1) {

			System.out.println("Error al procesar el archivo: " + e1.getMessage());
			e1.printStackTrace();
		}
	}

	public void eliminarProductorepetido(String nombreArchivo, int numeroLinea) {
		eliminarLinea(nombreArchivo, numeroLinea);
		try {
			cargarMenu();
		} catch (ProductoRepetidoException e1) {

			System.out.println("Error al procesar el archivo: " + e1.getMessage());
			e1.printStackTrace();
		}
	}

	public void eliminarLinea(String nombreArchivo, int numeroLinea) {
		try {
			File archivoEntrada = new File(nombreArchivo);

			BufferedReader br = new BufferedReader(new FileReader(archivoEntrada));
			StringBuilder contenido = new StringBuilder();

			String linea;
			int contador = 1;

			while ((linea = br.readLine()) != null) {
				if (contador != numeroLinea) {
					contenido.append(linea).append(System.lineSeparator());
				}
				contador++;
			}

			br.close();

			// Sobreescribir el archivo con el contenido actualizado
			FileWriter fw = new FileWriter(archivoEntrada);
			fw.write(contenido.toString());
			fw.close();
		} catch (IOException e) {
			System.out.println("Error al procesar el archivo: " + e.getMessage());
		}
	}

	public void generarMenuCombo() {
		int opcion = 1;
		System.out.println("Combos disponibles:");
		for (Combo combo : combos) {
			System.out.println(opcion + ". " + combo.getNombre() + " - $" + combo.getPrecio());
			opcion++;
		}
	}

	public void generarMenuBase() {
		System.out.println("Menú Base:");
		for (Map.Entry<Integer, ProductoMenu> entry : menuBase.entrySet()) {
			int opcion = entry.getKey();
			ProductoMenu menu = entry.getValue();
			System.out.println(opcion + ". " + menu.getNombre() + " - $" + menu.getPrecio());
		}
	}

	public void generarMenuItems() {
		System.out.println("Ingredientes disponibles:");
		for (Map.Entry<Integer, Ingrediente> entry : ingr.entrySet()) {
			int posicion = entry.getKey();
			Ingrediente ingrediente = entry.getValue();
			String nombre = ingrediente.getNombre();
			int costoAdicional = ingrediente.getCostoAdicional();
			System.out.println(posicion + ". " + nombre + "  + $" + costoAdicional);
		}
	}

	private void cargaringredientes() throws IngredienteRepetidoException {
		try (BufferedReader ir = new BufferedReader(new FileReader("data/ingredientes.txt"))) {
			String linea;
			int posicion = 1;
			while ((linea = ir.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int valor = Integer.parseInt(partes[1]);

				Ingrediente item = new Ingrediente(nombre, valor);

				if (ingr.containsKey(posicion)) {
					throw new IngredienteRepetidoException(item);
				} else {
					ingr.put(posicion, item);
				}

				posicion++;
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	private void cargarMenu() throws ProductoRepetidoException {
		//

		try (BufferedReader br = new BufferedReader(new FileReader("data/menu.txt"))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int precio = Integer.parseInt(partes[1]);

				ProductoMenu producto = new ProductoMenu(nombre, precio);
				if (buscarPorNombre(nombre) != null) {
					throw new ProductoRepetidoException(producto);
				} else {

					menuBase.put(menuBase.size() + 1, producto);

				}

			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	public ProductoMenu buscarPorNombre(String nombre) {
		for (Map.Entry<Integer, ProductoMenu> entry : menuBase.entrySet()) {
			ProductoMenu producto = entry.getValue();
			if (producto.getNombre().equals(nombre)) {
				return producto;
			}
		}
		return null;
	}

	public Integer buscarProductoInteger(String nombre) {
		for (Map.Entry<Integer, ProductoMenu> entry : menuBase.entrySet()) {
			ProductoMenu producto = entry.getValue();
			if (producto.getNombre().equals(nombre)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void paraAgregar(int AgregaCombo, int AgregaMenu, String Modificar) {
		if (Modificar.isEmpty() != true) {

			ProductoMenu modificado = this.seleccionarProductoMenu(AgregaMenu);
			this.Categorizar(Modificar, modificado);

		} else if (AgregaCombo != 100)
			try {
				pedidoEnCurso.agregarProducto(this.seleccionarCombo(AgregaCombo));
			} catch (RestriccionPrecioException e) {

//				System.out.println("Error " + e.getMessage());
				e.printStackTrace();
			}
		else if (AgregaMenu != 100)
			try {
				pedidoEnCurso.agregarProducto(this.seleccionarProductoMenu(AgregaMenu));
			} catch (RestriccionPrecioException e) {
//				System.out.println("Error " + e.getMessage());
				e.printStackTrace();
			}

	}

	public Combo seleccionarCombo(int opcion) {
		if (opcion >= 1 && opcion <= combos.size()) {
			return combos.get(opcion - 1);
		} else {
			return null;
		}
	}

	public void Categorizar(String Modificar, ProductoMenu Base) {
		String[] modificaciones = Modificar.split(" ");
		ArrayList<Ingrediente> IngredientesA = new ArrayList<Ingrediente>();
		ArrayList<Ingrediente> IngredientesE = new ArrayList<Ingrediente>();
		for (int i = 0; i < modificaciones.length; i++) {
			if (modificaciones[i].equals("con")) {

				String nombreIngredienteA = modificaciones[i + 1];
				if (i + 2 < modificaciones.length && !modificaciones[i + 2].equals("sin")
						&& !modificaciones[i + 2].equals("con") && !modificaciones[i + 2].equals("y")) {

					nombreIngredienteA = modificaciones[i + 1] + " " + modificaciones[i + 2];
					Ingrediente IngredienteA = buscarIngredientePorNombre(nombreIngredienteA);
					IngredientesA.add(IngredienteA);

				} else {
					Ingrediente IngredienteA = buscarIngredientePorNombre(nombreIngredienteA);
					IngredientesA.add(IngredienteA);
				}

			} else if (modificaciones[i].equals("sin")) {

				String nombreIngredienteE = modificaciones[i + 1];
				if (i + 2 < modificaciones.length && !modificaciones[i + 2].equals("sin")
						&& !modificaciones[i + 2].equals("sin") && !modificaciones[i + 2].equals("y")) {
					nombreIngredienteE = modificaciones[i + 1] + " " + modificaciones[i + 2];
					Ingrediente IngredienteE = buscarIngredientePorNombre(nombreIngredienteE);
					IngredientesE.add(IngredienteE);

				} else {
					Ingrediente IngredienteE = buscarIngredientePorNombre(nombreIngredienteE);
					IngredientesE.add(IngredienteE);
				}

			}
		}
		pedidoEnCurso.agregarModificado(Base, IngredientesA, IngredientesE);
	}

	public Ingrediente buscarIngredientePorNombre(String nombre) {
		for (Map.Entry<Integer, Ingrediente> entry : ingr.entrySet()) {
			Ingrediente ingrediente = entry.getValue();
			if (ingrediente.getNombre().equals(nombre)) {
				return ingrediente;
			}
		}
		return null;
	}

	public Integer buscarIngredientePorNombreInt(String nombre) {
		for (Map.Entry<Integer, Ingrediente> entry : ingr.entrySet()) {
			Ingrediente ingrediente = entry.getValue();
			if (ingrediente.getNombre().equals(nombre)) {
				return entry.getKey();
			}
		}
		return null;
	}

	private void cargarCombos() {

		try (BufferedReader cr = new BufferedReader(new FileReader("data/combos.txt"))) {
			String linea;
			while ((linea = cr.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				double descuento = Double.parseDouble(partes[1].replace("%", "")) / 100;

				Combo combo = new Combo(nombre, descuento);
				ProductoMenu PartedeCombo;
				for (int i = 2; i < partes.length; i++) {
					// Buscar producto menu con el nombre para poder sacar el precio
					PartedeCombo = this.buscarPorNombre(partes[i]);
					combo.agregarItemACombo(PartedeCombo);

				}

				combos.add(combo);
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

}

package procesamiento;

//import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Exception.RestriccionPrecioException;

public class Pedido {
	private int idPedido;
	private static int numeroPedidos = 0;
	private String nombreCliente;
	private String direccionCliente;
	private List<Producto> itemsPedido;

	public Pedido(String nombreCliente, String direccionCliente) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.idPedido = 1;
		this.itemsPedido = new ArrayList<>();
	}

	public List<Producto> getItemsPedido() {

		return itemsPedido;
	}

	public int getidPedido0() {
		return idPedido;
	}

	public void agregarProducto(Producto nuevoItem) throws RestriccionPrecioException {
		itemsPedido.add(nuevoItem);
		long PrecioTotal = this.getPrecioTotalPedido0();

		if (PrecioTotal > 150000) {
			int ultimoIndice = itemsPedido.size() - 1;
			itemsPedido.remove(ultimoIndice);
			throw new RestriccionPrecioException(nuevoItem);

		}

	}

	public void agregarModificado(ProductoMenu nuevoItem, ArrayList<Ingrediente> IngredienteA,
			ArrayList<Ingrediente> IngredienteE) {
		ProductoAjustado ItemAjustado = new ProductoAjustado(nuevoItem);
		ItemAjustado.agregarProductos(IngredienteA, IngredienteE);
		itemsPedido.add(ItemAjustado);
	}

	private int generarID() {
		numeroPedidos++;
		return numeroPedidos;
	}

	public int getPrecioNetoPedido0() {
		int PrecioTotal = 0;
		for (Producto item : itemsPedido) {

			PrecioTotal += item.getPrecio();
		}
		return PrecioTotal;

	}

	private int getPrecioTotalPedido0() {
		int PrecioN = getPrecioNetoPedido0();
		int IVA = (int) getPrecioIVAPedido0();
		int PrecioTotal = PrecioN + IVA;

		return PrecioTotal;
	}

	private double getPrecioIVAPedido0() { // se cambio a double ya que es un valor no entero
		int PrecioN = getPrecioNetoPedido0();
		double IVA = PrecioN * 0.19;
		return IVA;
	}

	public String generarTextoFactura() {
		int numeroFactura = getidPedido0();
		String encabezado = "ID No. " + numeroFactura;
		String informacion = "\nNombre Cliente: " + this.nombreCliente + "\nDireccion Cliente: "
				+ this.direccionCliente;
		String listaProductos = "\nProductos:\n";
		for (Producto item : itemsPedido) {
			listaProductos += item.generarTextoFactura();
		}

		int precioNeto = getPrecioNetoPedido0();
		double ivaNeto = getPrecioIVAPedido0();
		int precioTotal = getPrecioTotalPedido0();

		String precios = String.format("Precio neto: $%d\nIVA: $%.2f\nTotal: $%d", precioNeto, ivaNeto, precioTotal);

		String factura = encabezado + informacion + listaProductos + precios;

		return factura;
	}

	public void guardarFactura() {

		String factura = generarTextoFactura()+";\n";
		PrintWriter writer;

		try {
			writer = new PrintWriter(new FileWriter("data/facturas.txt", true));
			writer.print(factura);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(factura);
	}

}

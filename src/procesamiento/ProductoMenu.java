package procesamiento;

public class ProductoMenu implements Producto {
	private String nombre;
	private long precioBase;

	public ProductoMenu(String nombre, int PrecioBase) {
		this.nombre = nombre;
		this.precioBase = PrecioBase;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public long getPrecio() {
		return precioBase;

	}

	@Override
	public String generarTextoFactura() {
		return String.format("%s - $%d\n", this.getNombre(), this.getPrecio());

	}

}

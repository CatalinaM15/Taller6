package Exception;

import procesamiento.Producto;

public class ProductoRepetidoException extends HamburguesaException {

	private static final long serialVersionUID = 1L;
	private Producto producto;

	public ProductoRepetidoException(Producto prod) {
		this.producto = prod;
	}

	@Override
	public String getMessage() {

		return "Habia un Producto con el nombre " + producto.getNombre()
				+ " repetido en la lista se elimino, por favor correr de nuevo la aplicacion";

	}

	public Producto getProducto() {
		return producto;
	}
}

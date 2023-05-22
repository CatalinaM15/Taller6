package Exception;


import procesamiento.Producto;

public class RestriccionPrecioException  extends Exception{

	private static final long serialVersionUID = 1L;
	private Producto newProducto;

	public RestriccionPrecioException(Producto newProducto) {
		this.newProducto = newProducto;
	}

	@Override
	public String getMessage() {

		return "El Producto " +newProducto.getNombre()+ " no ha sido posible de agregar al pedido ya que superan los 150.000";

	}

	public Producto getProducto() {
		return newProducto;
	}
}

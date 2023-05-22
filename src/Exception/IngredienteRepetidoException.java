package Exception;

import procesamiento.Ingrediente;

public class IngredienteRepetidoException extends HamburguesaException {
	private static final long serialVersionUID = 1L;
	private Ingrediente item;

	public IngredienteRepetidoException(Ingrediente item) {
		this.item = item;
	}

	@Override
	public String getMessage() {

		return "Habia un ingrediente con el nombre " + item.getNombre()
				+ " repetido en la lista se elimino, por favor correr de nuevo la aplicacion";

	}

	public Ingrediente getItem() {
		return item;
	}

}

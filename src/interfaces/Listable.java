package interfaces;

import java.sql.SQLException;
import java.util.LinkedList;

public interface Listable {

	/**
	 * M�todo que muestra la lista de todos los Pacientes y/o citas de estos pacientes
	 * @return LinkedList<Object>
	 * @throws SQLException
	 */
	public LinkedList<Object> listarTodos() throws SQLException;
	
	/**
	 * M�todo que vac�a cualquiera de las listas
	 * @throws SQLException
	 */
	public void borrarLista() throws SQLException;	
	
}

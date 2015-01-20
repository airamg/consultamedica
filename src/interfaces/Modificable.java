package interfaces;

import java.sql.SQLException;

public interface Modificable {

	/**
	 * M�todo que modifica varios datos del objecto que se pase como parametro
	 * @param o
	 * @throws SQLException
	 */
	public void modificarTodo(Object o) throws SQLException;	
	
}
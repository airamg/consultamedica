package gestoresBD;

import interfaces.Listable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import clasesBasicas.Administrador;


public class GestorAdministrador implements Listable 
{

	private Connection conn;
	private static GestorAdministrador instance = null;
	
	public static GestorAdministrador getInstance()
	{
		if (instance == null)
			instance = new GestorAdministrador();
		return instance;
	}

	public void connect() throws ClassNotFoundException, SQLException 
	{
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:files/consulta.sqlite");
	}

	public void disconnect() throws SQLException
	{
		conn.close();
	}
	
	/**	
	 * Método que devuelve la contraseña correspondiente al nombre del usuario introducido
	 * @param nombre
	 * @return String
	 * @throws SQLException 
	 */
	public String buscarUsuarioPass(String nombre) throws SQLException
	{
		String contras = "";
		PreparedStatement ps = conn.prepareStatement("Select * from Administrador where nombre=?");
		ps.setString(1, nombre);		
		ResultSet rs = ps.executeQuery();		
		if(rs.next()) 		
			contras = rs.getString("contraseña");
		rs.close();
		ps.close();	
		return contras;			
	}	
	
	/**
	 * Método que devuelve una lista con todos los usuarios y contraseñas que tiene el Administrador
	 * @return LinkedList<Administrador>
	 * @throws SQLException 
	 */
	public LinkedList<Object> listarTodos() throws SQLException 
	{
		LinkedList<Object> lA = new LinkedList<Object>();	
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("Select * from Administrador");	
		while (rs.next()) 
		{
			Administrador a = new Administrador(rs.getString("nombre"), rs.getString("contraseña"));
			lA.add(a);				
		}
		rs.close();
		s.close();
		return lA;	
	} 
	
	/**
	 * Método que borra la lista de todos los usuarios que haya en la base de datos
	 * @throws SQLException 
	 */
	public void borrarLista() throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("Delete * from Administrador");		
		stat.executeUpdate();
		stat.close();
	}
		
	public static void main(String[] args) 
	{
		GestorAdministrador a = new GestorAdministrador();
		try {
			a.connect();
		} catch (ClassNotFoundException e1) {} catch (SQLException e1) {}
		try {
			String nom = "maria";
			String pass = a.buscarUsuarioPass(nom);
			System.out.println("Contraseña del usuario " +nom+ ": " +pass);
			System.out.println();
			LinkedList<Object> l = a.listarTodos();		
			for (int i=0; i<l.size(); i++)
			{
				System.out.println(l.get(i));
			}
		} catch (SQLException e) {			
			System.out.println(e.getMessage());
		}
		try {
			a.disconnect();
		} catch (SQLException e) {}
	}

}

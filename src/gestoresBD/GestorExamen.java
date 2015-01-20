package gestoresBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import clasesBasicas.Examen;

import interfaces.Listable;

public class GestorExamen implements Listable
{
	
	private static GestorExamen instance = null;
	private Connection conn;

	public static GestorExamen getInstance()
	{
		if (instance == null)
			instance = new GestorExamen();
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
	 * Método que devuelve una lista con todos los tipos de exámenes que hay
	 * @return LinkedList<Object>
	 * @throws SQLException
	 */
	public LinkedList<Object> listarTodos() throws SQLException 
	{		
		LinkedList<Object> examen = new LinkedList<Object>();
		Statement stat = conn.createStatement();		
		ResultSet rs = stat.executeQuery("SELECT * from examen");
				
		while (rs.next()) 
		{			
			Examen e = new Examen(rs.getInt("codigo"), rs.getString("nombre"), rs.getString("descripcion"));
			System.out.println(e);
			examen.add(e);
		}	
		
		rs.close();		
		stat.close();		
		return examen;		
	}
	
	/**
	 * Devuelve la descripción del examen que metamos como parámetro
	 * @param idExam
	 * @return String
	 * @throws SQLException
	 */
	public String mostrarDescripcionExamen(int idExam) throws SQLException 
	{
		String descrip = "";
		Examen e = null;
		PreparedStatement ps = conn.prepareStatement("Select * from Examen where codigo=?");
		ps.setInt(1, idExam);		
		ResultSet rs = ps.executeQuery();		
		if(rs.next()) 
		{
			e = new Examen(rs.getInt("codigo"), rs.getString("nombre"), rs.getString("descripcion"));
			descrip = e.getDescripcion();
		}	
		rs.close();
		ps.close();
		return descrip;
	}

	/**
	 * Borra toda la lista de exámenes
	 * @throws SQLException 
	 */
	public void borrarLista() throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("Delete * from examen");		
		stat.executeUpdate();
		stat.close();		
	}
	
	public static void main(String[] args) 
	{
		GestorExamen e = new GestorExamen();		
		try {
			e.connect();
		} catch (ClassNotFoundException e1) {} catch (SQLException e1) {}
		try {
			LinkedList<Object> lE = e.listarTodos();
			System.out.println();
			System.out.println("*Tamaño lista: " +lE.size());			
			if(lE==null)
				System.out.println("Lista vacia");	
			String s = e.mostrarDescripcionExamen(1);
			System.out.println(s);
		} catch (SQLException esql) {			
			System.out.println(esql.getMessage());
		}
		try {
			e.disconnect();
		} catch (SQLException eqld1) {}
	}	
	
}

package gestoresBD;

import interfaces.Listable;
import interfaces.Modificable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import clasesBasicas.Hace;

public class GestorHace implements Listable, Modificable
{
	private Connection conn;
	private static GestorHace instance = null;
	
	public static GestorHace getInstance()
	{
		if (instance == null)
			instance = new GestorHace();
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
	 * Método que muestra solo la lista todas las pruebas realizadas de un paciente
	 * Se mirará si el número de intentos de cada prueba es mayor que cero
	 * @param nss_paciente
	 * @return LinkedList<Hace>
	 * @throws SQLException
	 */
	public LinkedList<Hace> listaExamenesRealizados(long nss_paciente) throws SQLException
	{
		LinkedList<Hace> lEP = new LinkedList<Hace>();
		
		PreparedStatement ps = conn.prepareStatement("Select * from hace where nss_paciente=?");
		ps.setLong(1, nss_paciente);		
		ResultSet rs = ps.executeQuery();		
		if(rs.next())	
		{
			if(rs.getInt("intentos")>0){
			Hace c = new Hace(rs.getLong("nss_paciente"), rs.getInt("codigo_examen"), rs.getString("fecha_cita"), rs.getString("hora_cita"), rs.getInt("intentos"), rs.getInt("fallos"), rs.getInt("aciertos"));
			lEP.add(c);
			System.out.println(c);
			}
		}			
		rs.close();
		ps.close();
		
		if(lEP.size()==0)
			return null;
		return lEP;
	}	
	
	/**
	 * Método que devuelve una lista de las citas que tiene un paciente para realizar un tipo de prueba
	 * @return LinkedList<Hace>
	 * @throws SQLException 
	 */
	public LinkedList<Object> listarTodos() throws SQLException 
	{
		LinkedList<Object> lCP = new LinkedList<Object>();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("Select * from hace");
		System.out.println("LISTA CITAS");
		System.out.println();
		while (rs.next()) 
		{
			Hace hace = new Hace(rs.getLong("nss_paciente"), rs.getInt("codigo_examen"), rs.getString("fecha_cita"), rs.getString("hora_cita"), rs.getInt("intentos"), rs.getInt("fallos"), rs.getInt("aciertos"));
			lCP.add(hace);	
			System.out.println(hace);
		}
		rs.close();
		s.close();
		return lCP;		
	}
	
	/**
	 * Método que añade una nueva cita a la BD del paciente buscado
	 * @param h
	 * @throws SQLException
	 */
	public void nuevaCita(Hace h) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement("Insert into Hace values(" +h.getNss_paciente()+ "," +h.getCodigo_examen()+ ",'" +h.getFecha()+ "','" +h.getHoraCita()+ "'," +h.getIntentos()+ "," +h.getFallos()+ "," +h.getAciertos()+ ");");
		stat.executeUpdate();
		stat.close();		
	}	
		
	//MODIFICAR DATOS DE LA CITA DEL PACIENTE CON ESE NSS Y CODIGO EXAMEN
	public void modificar(long nss, int codigo) throws SQLException
	{	
		PreparedStatement stat = conn.prepareStatement("update paciente set horaCita= ? fecha=? where nss=?");
		stat.setLong(3, nss);
		stat.executeUpdate();		
		stat.close();
	}	
	
	/**
	 * Método que borra el objecto Hace que metemos como parametro e introduce uno nuevo con los nuevos valores
	 * @param o
	 * @throws SQLException
	 */
	public void modificarTodo(Object o) throws SQLException 
	{
		borrarCita((Hace) o);
		Hace h = new Hace(((Hace) o).getNss_paciente(), ((Hace) o).getCodigo_examen(), ((Hace) o).getFecha(), ((Hace) o).getHoraCita(), ((Hace) o).getIntentos(), ((Hace) o).getFallos(), ((Hace) o).getAciertos());
		nuevaCita(h);		
	}
	
	/**
	 * Método que borra una cita h de la lista de citas
	 * @param h
	 * @throws SQLException 
	 */
	public void borrarCita(Hace h) throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("Delete from Hace where nss_paciente = ?");
		stat.setLong(1, h.getNss_paciente());		
		stat.executeUpdate();
		stat.close();
	}	
	
	/**
	 * Método que borra la lista de todas las citas que haya en la base de datos
	 * @throws SQLException 
	 */
	public void borrarLista() throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("Delete * from Hace");		
		stat.executeUpdate();
		stat.close();
	}
	
	/**
	 * Main de prueba de los métodos de algunos métodos esta clase
	 * @param args sin uso
	 */
	public static void main(String[] args) 
	{
		GestorHace h = new GestorHace();
		//Hace ha = new Hace(23143432, 2, "13/06/13", "10:00", 0, 0, 0);		
		try {
			h.connect();
		} catch (ClassNotFoundException e1) {} catch (SQLException e1) {}
		try {		
			System.out.println();
			LinkedList<Object> lH = h.listarTodos();
			System.out.println();
			System.out.println("*Tamaño lista: " +lH.size());	
			LinkedList<Hace> lE = h.listaExamenesRealizados(23143432);
			if(lE==null)
				System.out.println("Lista vacia");			
		} catch (SQLException e) {			
			System.out.println(e.getMessage());
		}
		try {
			h.disconnect();
		} catch (SQLException e) {}
	}

}

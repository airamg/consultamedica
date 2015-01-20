package gestoresBD;

import interfaces.Listable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import clasesBasicas.Pregunta;

public class GestorPregunta implements Listable
{
	
	private static GestorPregunta instance = null;
	private Connection conn;
	
	public static GestorPregunta getInstance(){
		if (instance == null)
			instance = new GestorPregunta();
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
	 * Método que devuelve una lista con todas las preguntas que hay sobre el tema introducido
	 * @param tema
	 * @return LinkedList<Pregunta>
	 * @throws SQLException
	 */
	public LinkedList<Pregunta> listarPreguntasTema(String tema) throws SQLException
	{
		LinkedList<Pregunta> lPT = new LinkedList<Pregunta>();		
		PreparedStatement stat = conn.prepareStatement("SELECT * from Pregunta where tema=?");
		stat.setString(1, tema);		
		ResultSet rs = stat.executeQuery();
		while (rs.next())
		{
			Pregunta p = new Pregunta (rs.getInt("id"),rs.getInt("codigo_examen"),rs.getString("pregunta"), rs.getString("respuesta_correcta"), rs.getString("respuesta_incorrecta1"),	rs.getString("respuesta_incorrecta2"),rs.getString("tema"));
			lPT.add(p);
			System.out.println(p);
		}	
		rs.close();
		stat.close();
		return lPT;
	}
	
	/**
	 * Método que devuelve una lista con todas las preguntas que hay según el código de examen introducido
	 * @param codigo_examen
	 * @return LinkedList<Pregunta>
	 * @throws SQLException
	 */
	public LinkedList<Pregunta> listarPreguntasPrueba(int codigo_examen) throws SQLException
	{
		LinkedList<Pregunta> lPP = new LinkedList<Pregunta>();		
		PreparedStatement stat = conn.prepareStatement("SELECT * from pregunta where codigo_examen = ?");
		stat.setInt(1, codigo_examen);		
		ResultSet rs = stat.executeQuery();		
		Pregunta p = null;		
		while (rs.next())
		{
			p = new Pregunta (rs.getInt("id"),rs.getInt("codigo_examen"),rs.getString("pregunta"), rs.getString("respuesta_correcta"), rs.getString("respuesta_incorrecta1"), rs.getString("respuesta_incorrecta2"),rs.getString("tema"));
			lPP.add(p);
			System.out.println(p);
		}	
		rs.close();
		stat.close();
		return lPP;		
	}
	
	/**
	 * Devuelve una lista con todas las preguntas y respuestas 
	 * @return LinkedList<Object>
	 * @throws SQLException 
	 */
	public LinkedList<Object> listarTodos() throws SQLException 
	{
		LinkedList<Object> pregunta = new LinkedList<Object>();
		Statement stat = conn.createStatement();		
		ResultSet rs = stat.executeQuery("SELECT * from Pregunta");
		System.out.println("Lista Preguntas");		
		while (rs.next()) 
		{
			Pregunta p = new Pregunta(rs.getInt("id"),rs.getInt("codigo_examen"),rs.getString("pregunta"), rs.getString("respuesta_correcta"), rs.getString("respuesta_incorrecta1"), rs.getString("respuesta_incorrecta2"),rs.getString("tema"));
			pregunta.add(p);
		}		
		rs.close();		
		stat.close();		
		return pregunta;
	}	
		
	/**
	 * Borra toda la lista de preguntas de la base de datos
	 * @throws SQLException
	 */
	public void borrarLista() throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("delete * from pregunta");		
		stat.executeUpdate();
		stat.close();
	}	
		
	public static void main(String[] args) 
	{
		GestorPregunta p = new GestorPregunta();
		try {
			p.connect();
		} catch (ClassNotFoundException e1) {} catch (SQLException e1) {}
		
		try {
			LinkedList<Pregunta> lH = p.listarPreguntasTema("Deportes");
			System.out.println("*Tamaño lista: " +lH.size());	
			System.out.println();
			LinkedList<Pregunta> lP2 = p.listarPreguntasPrueba(3);
			System.out.println("*Tamaño lista: " +lP2.size());					
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		try {
			p.disconnect();
		} catch (SQLException e) {}
	}
	
}

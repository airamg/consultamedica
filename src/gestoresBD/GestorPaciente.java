package gestoresBD;


import clasesBasicas.Paciente;

import interfaces.Listable;
import interfaces.Modificable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class GestorPaciente implements Listable, Modificable
{

	private static GestorPaciente instance = null;
	private Connection conn;

	public static GestorPaciente getInstance()
	{
		if (instance == null)
			instance = new GestorPaciente();
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
	 * Método que devuelve una lista de todos los pacientes que hay en la consulta
	 * @return LinkedList<Paciente>
	 */
	public LinkedList<Object> listarTodos() throws SQLException 
	{
		LinkedList<Object> paciente = new LinkedList<Object>();
		Statement stat = conn.createStatement();		
		ResultSet rs = stat.executeQuery("SELECT * from Paciente");
		System.out.println("Lista Pacientes");		
		while (rs.next()) 
		{
			//añadir sus atributos
			Paciente p = new Paciente(rs.getString("nombre"),rs.getInt("nss"), rs.getString("apellido"), rs.getString("historial"),rs.getInt("año_nacimento"),rs.getString("trabajo"),rs.getString("medicacion"),rs.getInt("resultado_examen"));
			paciente.add(p);
		}		
		rs.close();		
		stat.close();		
		return paciente;
	}
	
	/**
	 * Método que añade un nuevo paciente a la base de datos
	 * Comprueba que la ruta de la imagen elegida sea correcta 
	 * @param p
	 * @param ruta
	 * @throws SQLException
	 * @throws IOException
	 */
	public void añadirPaciente(Paciente p, String ruta) throws SQLException, IOException 
	{	
		try {	
			File f = null;
			byte[] image = null;
			if(ruta!="")
			{
				f = new File(ruta);				
				FileInputStream ffile = new FileInputStream(f);
				BufferedInputStream reader = new BufferedInputStream(ffile);
				int tam = reader.available();
				image = new byte[tam];			
				reader.read(image, 0, tam);
				reader.close();	
			}			
			PreparedStatement stat = conn.prepareStatement("insert into Paciente values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stat.setLong(1, p.getNss());
			stat.setString(2, p.getNombre());
			stat.setString(3, p.getApellido());
			stat.setBytes(4, image);
			stat.setString(5, p.getHistorialMedico());
			stat.setInt(6, p.getAñoNacimiento());
			stat.setString(7, p.getTrabajo());
			stat.setString(8, p.getMedicacion());
			stat.setInt(9, p.getResultadoUltimaPrueba());
			stat.executeUpdate();
			stat.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	/**
	 * Método buscar que devuelve un paciente con todos sus atributos
	 * Devuelve null si el paciente no lo encuentra
	 * Devuelve el paciente correspondiente al nss si lo encuentra
	 * @param nss
	 * @return Paciente
	 * @throws SQLException 
	 */
	public Paciente buscarPaciente(long nss) throws SQLException
	{
		Paciente p = null;
		PreparedStatement ps = conn.prepareStatement("Select * from Paciente where nss=?");
		ps.setLong(1, nss);		
		ResultSet rs = ps.executeQuery();		
		if(rs.next()) //si lo encuentra	
		{
			p = new Paciente(rs.getString("nombre"), rs.getInt("nss"), rs.getString("apellido"), rs.getString("historial"), rs.getInt("año_nacimiento"), rs.getString("trabajo"), rs.getString("medicacion"), rs.getInt("resultado_examen"));
		}			
		rs.close();
		ps.close();
		return p;
	}	
	
	/**
	 * Método que permite modificar los datos del paciente
	 * @throws SQLException 	
	 */
	public void modificarTodo(Object o) throws SQLException
	{
		borrarPaciente((Paciente) o);
		Paciente p = new Paciente(((Paciente) o).getNombre(), ((Paciente) o).getNss(), ((Paciente) o).getApellido(), ((Paciente) o).getHistorialMedico(), ((Paciente) o).getAñoNacimiento(), ((Paciente) o).getTrabajo(), ((Paciente) o).getMedicacion(), ((Paciente) o).getResultadoUltimaPrueba());
		try {
			añadirPaciente(p, "");
		} catch (IOException e) {}		
	}	
	
	/**
	 * Modifica el resultado de la ultima prueba realizada por el paciente
	 * @param resultado
	 * @param nss
	 * @throws SQLException
	 */
	public void modificarResultadoPrueba(int resultado, long nss) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement("UPDATE Paciente SET resultado_examen = ? WHERE nss = ?");
		stat.setInt(1, resultado);
		stat.setLong(2, nss);
		stat.executeUpdate();
		stat.close();
	}
			
	/**
	 * Método que borra un paciente p de la lista de pacientes
	 * @param p
	 * @throws SQLException 
	 */
	public void borrarPaciente(Paciente p) throws SQLException 
	{
		PreparedStatement stat = conn.prepareStatement("Delete from Paciente where nss = ?");
		stat.setLong(1, p.getNss());		
		stat.executeUpdate();
		stat.close();
	}
	
	/**
	 * Método que cambia la foto anterior por una nueva del paciente que metamos como parámetro
	 * @param ruta
	 * @param nss
	 * @throws SQLException
	 * @throws IOException
	 */
	public void modificarFoto(String ruta, long nss) throws SQLException, IOException
	{
		File f = new File(ruta);
		byte[] image = null;
		FileInputStream ffile = new FileInputStream(f);
		BufferedInputStream reader = new BufferedInputStream(ffile);
		int tam = reader.available();
		image = new byte[tam];			
		reader.read(image, 0, tam);
		reader.close();
		PreparedStatement stat2 = conn.prepareStatement("UPDATE Paciente SET foto = ? WHERE nss = ?");
		stat2.setBytes(1, image);
		stat2.setLong(2, nss);
		stat2.executeUpdate();
		stat2.close();
	}
	
	/**
	 * Método que devuelve la foto de tipo Blob de la base de datos
	 * La pasa a una JLabel para poderla mostrar en la ventana	
	 * @param imagen
	 * @param nss
	 * @throws SQLException
	 */
	public void sacarFoto(JLabel imagen, long nss) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String lSql = "SELECT foto FROM Paciente WHERE nss = ?";		
		ps = conn.prepareStatement(lSql);
		ps.setLong(1, nss);
		rs = ps.executeQuery();		
		if (rs.next())
		{
			byte[] a = rs.getBytes(1);
			ImageIcon ii = new ImageIcon(a);
			imagen.setIcon(ii);			
		}	
		rs.close();
		ps.close();
	}	
	
	/**
	 * Método que borra la lista de todos los pacientes que haya en la base de datos
	 * @throws SQLException 
	 */ 
	public void borrarLista() throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement("Delete * from Paciente");		
		stat.executeUpdate();
		stat.close();
	}	

	public static void main(String[] args) 
	{
		GestorPaciente gp = new GestorPaciente();
		try {
			gp.connect();
		} catch (ClassNotFoundException e1) {} catch (SQLException e1) {}
		Paciente p = new Paciente();
		//Paciente p2 = new Paciente("Pepe", 34561234, "Lu", null, "Historial en proceso...", 1990, "Gimnasta", "Ninguna", 0);
		try {
			p = gp.buscarPaciente(46372817);
			if (p!=null)							
				System.out.println(p);	
			//gp.modificarResultadoPrueba(1, 34561234);
			Paciente a = gp.buscarPaciente(34561234);
			if (a!=null)							
				System.out.println(a);
		} catch (SQLException e) { e.printStackTrace(); }
		try {
			gp.disconnect();
		} catch (SQLException e) {}
	}
	
}

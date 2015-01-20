package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import clasesBasicas.Hace;


public class VHistorial extends JFrame implements ActionListener, Serializable
{
		
	private static final long serialVersionUID = 1L;
	private JButton btnVer = new JButton("Ver paciente");
	private JButton btnAtras = new JButton("Atrás");
	private DefaultTableModel modelo;
	private JTable tabla;
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";	
	
	public VHistorial()
	{
		super("Historial médico de las pruebas");
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				
		tabla = new JTable();
		modelo = new DefaultTableModel(0, 5);		
		String[] s = {"Nombre", "Apellido", "Cita", "Prueba", "Resultado"};
		modelo.setColumnIdentifiers(s);
		
		Container lista = new JPanel();			
		lista.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane();		
		scroll.add(tabla);		
		lista.add(scroll, "Center");		
		scroll.setViewportView(tabla);	
		
		this.pack();
		this.setSize(800, 480);
		this.setLocationRelativeTo(null); 
				
		LinkedList<Object> ll = new LinkedList<Object>();
		
		//conexion a base de datos
		try {
			GestorHace gestor = GestorHace.getInstance();
			GestorPaciente gestor2 = GestorPaciente.getInstance();
			gestor.connect();
			gestor2.connect();
			ll = gestor.listarTodos();			
			
			for (Object ha : ll)
			{    
				if(ha instanceof Hace)
				{
					String nom = gestor2.buscarPaciente(((Hace) ha).getNss_paciente()).getNombre();
					String apell = gestor2.buscarPaciente(((Hace) ha).getNss_paciente()).getApellido();
					String cita = ((Hace) ha).getFecha()+ " - " +((Hace) ha).getHoraCita();
					int codigo = ((Hace) ha).getCodigo_examen();
					String prueba = "";
					String resultado = "0";					
					switch(codigo)
					{
						case 1: 
							prueba = "vista"; 
							resultado = Integer.toString(((Hace) ha).getAciertos());
							break;							
						case 2: 
							prueba = "memoria"; 
							resultado = Integer.toString(((Hace) ha).getAciertos());
							break;	
						case 3:	
							prueba = "ingenio"; 
							resultado = Integer.toString(((Hace) ha).getAciertos());
							break;	
					}
					modelo.addRow(new String[] {nom, apell, cita, prueba, resultado});				
				}
			}			
			tabla.setModel(modelo);	
			
			gestor.disconnect();
			gestor2.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}
				
		//panel para botones: sur
		Container botones = new JPanel();
		botones.setLayout(new FlowLayout());
		botones.add(btnAtras);			
		botones.add(btnVer);		

		//añadir todos los paneles de antes a la ventana
		getContentPane().setLayout(new BorderLayout());		
		getContentPane().add(lista, "Center");	
		getContentPane().add(botones, "South");		
						
		//eventos
		btnAtras.addActionListener(this);		
		btnVer.addActionListener(this);		
	}	

	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getSource().equals(btnAtras))
		{
			dispose();
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}
		
		if (arg0.getSource().equals(btnVer))
		{	
			int indexFila = tabla.getSelectedRow();	
			Hace h = null;
			try {
				GestorHace gestor = GestorHace.getInstance();		
				gestor.connect();		
				LinkedList<Object> ll = gestor.listarTodos();			
				h = (Hace) ll.get(indexFila);
				gestor.disconnect();			
			} catch (ClassNotFoundException e) {} catch (SQLException e) {}
			
			VVerPaciente v = new VVerPaciente(h.getNss_paciente()); 
			v.setVisible(true);
		}		
	}	
	
	public static void main(String[] args)
	{
		VHistorial v = new VHistorial();
		v.setVisible(true);	
	}
	
}

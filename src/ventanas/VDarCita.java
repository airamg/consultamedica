package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JComboBox;

import clasesBasicas.Hace;
import clasesBasicas.Paciente;


public class VDarCita extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JLabel lbNSS = new JLabel("NSS del paciente (8 dígitos): ");
	private JTextField tFNSS = new JTextField(20);
	private JButton btnBuscar = new JButton("Buscar");
	private JLabel lbFecha = new JLabel("Fecha (dd/mm/aa): ");	
	private JTextField tFFecha = new JTextField(8);	
	private String[] sHora = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30"};
	private JComboBox<String> cBHora = new JComboBox<String>(sHora);
	private JLabel lbPruebas = new JLabel("   Tipo de prueba: ");
	private JComboBox pruebas = new JComboBox(Pruebas.values()); 
	private JButton btnAceptar = new JButton("Aceptar");
	private JButton btnCancelar = new JButton("Cancelar");
	
	//atributos del panel de busqueda del centro
	private JLabel foto = new JLabel("");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	private JList lDatos;
	private ScrollPane scroll;	
	private DefaultListModel<String> modeloDatos;
	private String nombre = "  Nombre: ";	
	private String apellido = "  Apellido: ";	
	private String añoNacimiento = "  Año de nacimiento: ";
	private String historial = "  Historial médico: ";
	private String medicacion = "  Medicación: ";
	private String resultadoPrueba = "  Resultado última prueba: ";	
	
	public VDarCita()
	{
		//DISEÑO VENTANA
		super("Dar cita");		
		this.setMinimumSize(new Dimension(560, 300));	
		this.setLocationRelativeTo(null);	
		this.setBackground(Color.GRAY);
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					
		//DISEÑO PANELES
		Container c = getContentPane();
		c.setLayout(new BorderLayout());		
		
		JPanel pBusqueda = new JPanel(); //panel norte		
		pBusqueda.setLayout(new FlowLayout(FlowLayout.CENTER));
		pBusqueda.add(lbNSS);
		pBusqueda.add(tFNSS);
		pBusqueda.add(btnBuscar);
		pBusqueda.setBackground(new Color(211,211,211));
		
		JPanel pDatosPaciente = new JPanel(); //panel central
		pDatosPaciente.setLayout(new BorderLayout());
		lDatos = new JList();
		modeloDatos = new DefaultListModel<String>();	
		lDatos.setModel(modeloDatos);	
		scroll = new ScrollPane();		
		scroll.add(lDatos);			
		pDatosPaciente.add(foto, "West");
		pDatosPaciente.add(scroll, "Center");
		pDatosPaciente.setBackground(new Color(255,255,255)); 
		scroll.setVisible(false);
		foto.setVisible(false);
		lDatos.setVisible(false);		
		
		JPanel pCitaBotones = new JPanel();
		pCitaBotones.setLayout(new BorderLayout());
		pCitaBotones.setBackground(new Color(211,211,211));
		JPanel cita = new JPanel();
		cita.setLayout(new FlowLayout(FlowLayout.CENTER));
		cita.add(lbFecha);
		cita.add(tFFecha);
		cita.add(cBHora);
		cita.add(lbPruebas);
		cita.add(pruebas);
		cita.setBackground(new Color(211,211,211));
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout(FlowLayout.CENTER));
		botones.add(btnAceptar);
		botones.add(btnCancelar);	
		botones.setBackground(new Color(211,211,211));
		pCitaBotones.add(cita, "Center");
		pCitaBotones.add(botones, "South");
		pCitaBotones.setBackground(new Color(211,211,211));	
			
		c.add(pBusqueda, "North");
		c.add(pDatosPaciente, "Center");
		c.add(pCitaBotones, "South");
		
		//EVENTOS
		btnBuscar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);		
	}
	
	enum Pruebas {
		Ingenio, Vista, Memoria 
	}
	
	public void llenarPanel(Paciente p)
	{
		//meto los atributos de paciente en el modelo de lista
		String s1 = p.getNombre(); 
		String s2 = p.getApellido();
		String s3 = Integer.toString(p.getAñoNacimiento()); 
		String s4 = Integer.toString(p.getResultadoUltimaPrueba());
		try {
			GestorPaciente gestor = GestorPaciente.getInstance();
			gestor.connect();
			gestor.sacarFoto(foto, p.getNss());
			gestor.disconnect();				
		} catch (Exception e) {	
			//si el paciente no tiene foto, saca una por defecto
			foto.setIcon(new ImageIcon(getClass().getResource("/imagenes/paciente.jpg")));
		} 	
		if (p.getResultadoUltimaPrueba()==0)
			s4 = "-";
		String s5 = p.getMedicacion();
		if(p.getMedicacion()==null)
			s5 = "no tiene";
		String s6 = p.getHistorialMedico(); 
		if(p.getHistorialMedico()==null)
			s6 = "en construcción...";
		try {			
			modeloDatos.addElement(" ");
			modeloDatos.addElement(nombre+s1);
			modeloDatos.addElement(apellido+s2);
			modeloDatos.addElement(añoNacimiento+s3);
			modeloDatos.addElement(resultadoPrueba+s4);	
			modeloDatos.addElement(medicacion+s5);
			modeloDatos.addElement(historial+s6);			
		} catch(Exception e) {}

		scroll.setVisible(true);
		foto.setVisible(true);
		lDatos.setVisible(true);		
	}	

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(btnBuscar))
		{
			if (tFNSS.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null, "Campo vacío.", "Error", JOptionPane.ERROR_MESSAGE);
			else
			{
				//conexion a base de datos
				try {
					GestorPaciente gestor = GestorPaciente.getInstance();				
					gestor.connect();				

					//busco paciente				
					Paciente p = gestor.buscarPaciente((long) Integer.parseInt(tFNSS.getText()));
					if (p==null) 
					{
						JOptionPane.showMessageDialog(this, "Error en la búsqueda", "Aviso", JOptionPane.WARNING_MESSAGE);
						tFNSS.setText("");
					}
					else
						llenarPanel(p);	
					
					gestor.disconnect();			
				} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
			}
		}

		if(arg0.getSource().equals(btnAceptar))
		{	
			if (tFFecha.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null, "Campo vacío.", "Error", JOptionPane.ERROR_MESSAGE);			
			else
			{			
				//conexion a base de datos
				try {
					GestorHace gestor2 = GestorHace.getInstance();				
					gestor2.connect();					

					int codigoExamen = 0;
					int codigo = pruebas.getSelectedIndex();
					System.out.println(codigo);
					if (codigo == 0) //ingenio
						codigoExamen = 3;
					else if (codigo == 1) //vista
						codigoExamen = 1;
					else if (codigo == 2) //memoria
						codigoExamen = 2;
					String hora = (String) cBHora.getSelectedItem();
					Hace h = new Hace((long) Integer.parseInt(tFNSS.getText()), codigoExamen, tFFecha.getText(), hora, 0, 0, 0);
					gestor2.nuevaCita(h);
					JOptionPane.showMessageDialog(this, "Cita añadida. El paciente " +h.getNss_paciente()+ " hará el examen el día " +h.getFecha()+ " a las " +h.getHoraCita(), "Aviso", JOptionPane.INFORMATION_MESSAGE);	
					dispose();
					setVisible(false);					

					gestor2.disconnect();
				} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
			}
		}

		if(arg0.getSource().equals(btnCancelar))			
			dispose();				
	}
	
	public static void main(String[] args)
	{
		VDarCita v = new VDarCita();
		v.setVisible(true);
	}
	
}

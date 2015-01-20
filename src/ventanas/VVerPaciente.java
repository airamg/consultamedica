package ventanas;

import gestoresBD.GestorPaciente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


import javax.swing.*;

import clasesBasicas.Paciente;


public class VVerPaciente extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JButton btnCancelar;	
	private JLabel lblpaciente; 
	private JTextField lblDNombre = new JTextField();
	private JTextField lblDApellidos = new JTextField();
	private JTextField lblDOcupacion = new JTextField();
	private JTextField lblDFecha = new JTextField();
	private JTextField lblDMedicacion = new JTextField();
	private JTextField lblDObs = new JTextField();	
	private JButton btnModificar = new JButton("Modificar");
	private JButton btnCambiarFoto = new JButton("Cambiar foto");
	private File foto;
	JTextField txFoto = new JTextField();
	private long nss;
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	
	public VVerPaciente(long nss) 
	{
		super();
		this.nss = nss;
		this.setTitle("Ver paciente");
		this.setSize(450, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);	
		this.setUndecorated(true); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBackground(Color.GRAY);
		getContentPane().add(panelTitulo, BorderLayout.NORTH);
		
		//buscamos el paciente con el nss que pasamos como paramentro
		Paciente p = null;
		try {
			GestorPaciente gestor = GestorPaciente.getInstance();
			gestor.connect();			
			p = gestor.buscarPaciente(nss);
			lblpaciente = new JLabel("Paciente: " +p.getNss());
			
			panelTitulo.add(lblpaciente);	
			JPanel panelBotones = new JPanel();
			panelBotones.setBackground(Color.GRAY);
			getContentPane().add(panelBotones, BorderLayout.SOUTH);

			btnCancelar = new JButton("Atrás");
			panelBotones.add(btnModificar);
			panelBotones.add(btnCancelar);				

			JPanel panelDatos = new JPanel();
			panelDatos.setBackground(Color.GRAY);
			getContentPane().add(panelDatos, BorderLayout.CENTER);
			panelDatos.setLayout(null);			

			JLabel lblNombre = new JLabel("Nombre: ");
			lblNombre.setBounds(10, 11, 79, 14);
			panelDatos.add(lblNombre);		

			JLabel lblApellidos = new JLabel("Apellidos: ");
			lblApellidos.setBounds(10, 36, 79, 14);
			panelDatos.add(lblApellidos);		

			JLabel lblOcupacion = new JLabel("Ocupacion: ");
			lblOcupacion.setBounds(10, 61, 79, 14);
			panelDatos.add(lblOcupacion);	

			JLabel lblFechan = new JLabel("Fecha nacimiento: ");
			lblFechan.setBounds(10, 86, 120, 14);
			panelDatos.add(lblFechan);	

			JLabel lblMedicacion = new JLabel("Medicacion: ");
			lblMedicacion.setBounds(10, 111, 79, 14);
			panelDatos.add(lblMedicacion);	

			JLabel lblObservaciones = new JLabel("Observaciones: ");
			lblObservaciones.setBounds(10, 136, 101, 14);
			panelDatos.add(lblObservaciones);	

			//PARA INTRODUCIR LOS DATOS CORRESPONDIENTES			
			lblDNombre.setBounds(121, 10, 300, 18); 
			lblDNombre.setText(p.getNombre());
			panelDatos.add(lblDNombre);				

			lblDApellidos.setBounds(121, 35, 300, 18);
			lblDApellidos.setText(p.getApellido());
			panelDatos.add(lblDApellidos);

			lblDOcupacion.setBounds(121, 60, 300, 18);
			lblDOcupacion.setText(p.getTrabajo());
			panelDatos.add(lblDOcupacion);

			lblDFecha.setBounds(121, 85, 300, 18);
			lblDFecha.setText(Integer.toString(p.getAñoNacimiento()));
			panelDatos.add(lblDFecha);

			lblDMedicacion.setBounds(121, 110, 300, 18);
			lblDMedicacion.setText(p.getMedicacion());
			panelDatos.add(lblDMedicacion);

			lblDObs.setBounds(121, 135, 300, 18);
			lblDObs.setText(p.getHistorialMedico());
			panelDatos.add(lblDObs);
			
			txFoto.setBounds(10, 168, 260, 18);			
			panelDatos.add(txFoto);
				
			btnCambiarFoto.setBounds(280, 168, 133, 20);
			panelDatos.add(btnCambiarFoto);			

		gestor.disconnect();		
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}
		
		//EVENTO
		btnCancelar.addActionListener(this);
		btnModificar.addActionListener(this);
		btnCambiarFoto.addActionListener(this);
	}	

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(btnCancelar))
			dispose();	
		
		if (arg0.getSource().equals(btnModificar))
		{
			try {
				GestorPaciente gestor = GestorPaciente.getInstance();
				gestor.connect();
				Paciente pac = gestor.buscarPaciente(nss);		
				Paciente pacient = new Paciente(lblDNombre.getText(), nss, lblDApellidos.getText(), null, lblDObs.getText(), Integer.parseInt(lblDFecha.getText()), lblDOcupacion.getText(), lblDMedicacion.getText(), pac.getResultadoUltimaPrueba());
				gestor.modificarTodo(pacient);
				if(txFoto.getText()!="") //si ha seleccionado alguna foto
				{
					try {
						gestor.modificarFoto(txFoto.getText(), nss);
					} catch (IOException e) {						
						e.printStackTrace();
					}
				}
				gestor.disconnect();
				JOptionPane.showMessageDialog(this, "Datos modificados.");
			} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
			dispose();
		}
		
		if (arg0.getSource().equals(btnCambiarFoto))
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File ("C:\\"));
			int resp = fc.showOpenDialog(this);
			if(resp == fc.APPROVE_OPTION)
			{
				foto = fc.getSelectedFile();	
				txFoto.setText(foto.getAbsolutePath());
			}
		}
	}	
}

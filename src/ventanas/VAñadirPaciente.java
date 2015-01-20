package ventanas;

import gestoresBD.GestorPaciente;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;

import clasesBasicas.Paciente;


public class VA�adirPaciente extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private JButton bfoto = new JButton("Elegir foto");		
	private File foto;
	private boolean fotoSeleccionada = false;
	private JLabel lbNSS = new JLabel("NSS (8 d�gitos): ");
	private JTextField tFNSS = new JTextField(12);
	private JLabel lbnombre = new JLabel("Nombre: ");
	private JTextField txnombre = new JTextField(12);
	private JLabel lbapellido = new JLabel("  Apellido: ");	
	private JTextField txapellido = new JTextField(12);
	private JLabel lba�oNacimiento = new JLabel("A�o de nacimiento: ");
	private JTextField txa�oNac = new JTextField(5);
	private JLabel lbMedicacion = new JLabel("Medicaci�n: ");
	private JTextField tFMedicacion = new JTextField(29);
	private JLabel lbOcupacion = new JLabel("  Ocupaci�n: ");
	private JTextField tFOcupacion = new JTextField(12);
	private JLabel lbhistorial = new JLabel("Historial m�dico: ");
	private JTextField txhistorial = new JTextField(27);
	private JLabel lbresultadoPrueba = new JLabel("Resultado �ltima prueba: ");	
	private JTextField tfResultadoPrueba = new JTextField(22);
	private JButton baceptar = new JButton("Aceptar");
	private JButton bcancelar = new JButton("Cancelar");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";	
	
	public VA�adirPaciente() 
	{
		//DISE�O VENTANA
		super("A�adir paciente");		
		this.setSize(438, 290);	
		this.setLocationRelativeTo(null);		
		this.setResizable(false);
		this.setUndecorated(true);
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					
		//DISE�O PANELES
		Container c = getContentPane();
		c.setLayout(new BorderLayout());		
		
		JPanel pNorte = new JPanel(); //panel norte
		pNorte.setLayout(new FlowLayout());			
		pNorte.add(new JLabel(" -- A�adir un nuevo paciente --"));	
		pNorte.setBackground(Color.PINK);
		
		JPanel pCentro = new JPanel(); //panel centro
		pCentro.setLayout(new BorderLayout());
		pCentro.setBackground(Color.PINK);
		JPanel arriba = new JPanel();
		arriba.setBackground(Color.PINK);
		arriba.setLayout(new FlowLayout(FlowLayout.LEFT));
		arriba.add(lbNSS);
		arriba.add(tFNSS);		
		pCentro.add(arriba, "North");
		JPanel centro = new JPanel();
		centro.setBackground(Color.PINK);
		centro.setLayout(new FlowLayout(FlowLayout.LEFT));
		centro.add(lbnombre);
		centro.add(txnombre);
		centro.add(lbapellido);
		centro.add(txapellido);	
		centro.add(lbresultadoPrueba);
		centro.add(tfResultadoPrueba);
		centro.add(lba�oNacimiento);
		centro.add(txa�oNac);
		centro.add(lbOcupacion);
		centro.add(tFOcupacion);
		centro.add(lbMedicacion);
		centro.add(tFMedicacion);		
		centro.add(lbhistorial);
		centro.add(txhistorial);
		pCentro.add(centro, "Center");		
		
		JPanel pSur = new JPanel(); //panel sur
		pSur.setLayout(new BorderLayout());
		pSur.setBackground(Color.PINK);
		JPanel sur1 = new JPanel();
		sur1.setLayout(new FlowLayout());
		sur1.setBackground(Color.PINK);
		sur1.add(bfoto);
		JPanel sur2 = new JPanel();
		sur2.setLayout(new FlowLayout());
		sur2.setBackground(Color.PINK);
		sur2.add(baceptar);
		sur2.add(bcancelar);
		pSur.add(sur1, "North");
		pSur.add(sur2, "South");		
		
		c.add(pNorte, "North");
		c.add(pCentro, "Center");
		c.add(pSur, "South");
		
		//EVENTOS
		bfoto.addActionListener(this);
		baceptar.addActionListener(this);
		bcancelar.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(bfoto)) 
		{		
			//comprobar que antes de meter foto, ha rellenado los campos de datos
			if(tFNSS.getText().equals("") || txnombre.getText().equals("") || txapellido.getText().equals("") || txa�oNac.getText().equals("") || tFMedicacion.getText().equals("") || tFOcupacion.getText().equals("") || txhistorial.getText().equals("") || tfResultadoPrueba.getText().equals(""))
				JOptionPane.showMessageDialog(this, "No has rellenado todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
			else
			{
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File ("C:\\"));
				int resp = fc.showOpenDialog(this);
				if(resp == fc.APPROVE_OPTION)
				{
					foto = fc.getSelectedFile();					
					fotoSeleccionada = true;
				}
			}
		} 	
		
		//comprueba que se ha seleccionado foto o no
		//si no se selecciona guardar� vacio el campo y se mostrar� una por defecto
		if(arg0.getSource().equals(baceptar))	
		{
			if(fotoSeleccionada==true)
			{
				try {				
					GestorPaciente gestor = GestorPaciente.getInstance();
					gestor.connect();			
					Paciente pp = new Paciente(txnombre.getText(), (long) Integer.parseInt(tFNSS.getText()), txapellido.getText(), null, txhistorial.getText(), Integer.parseInt(txa�oNac.getText()), tFOcupacion.getText(), tFMedicacion.getText(), Integer.parseInt(tfResultadoPrueba.getText()));
					try {
						gestor.a�adirPaciente(pp, foto.getAbsolutePath());
						JOptionPane.showMessageDialog(this, "Paciente guardado.", "OK", JOptionPane.OK_OPTION);
												
					} catch (Exception e) {					
						JOptionPane.showMessageDialog(this, "El paciente " +(long) Integer.parseInt(tFNSS.getText())+ " ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
					}			
					gestor.disconnect();
					
				} catch (ClassNotFoundException e) {} catch (SQLException e) {}
				
				dispose();
			}
			else
			{
				try {				
					GestorPaciente gestor = GestorPaciente.getInstance();
					gestor.connect();			
					Paciente pp = new Paciente(txnombre.getText(), (long) Integer.parseInt(tFNSS.getText()), txapellido.getText(), null, txhistorial.getText(), Integer.parseInt(txa�oNac.getText()), tFOcupacion.getText(), tFMedicacion.getText(), Integer.parseInt(tfResultadoPrueba.getText()));
					try {
						gestor.a�adirPaciente(pp, "");	
						JOptionPane.showMessageDialog(this, "Paciente guardado.", "OK", JOptionPane.OK_OPTION);
					} catch (SQLException ex) {					
						JOptionPane.showMessageDialog(this, "El paciente " +(long) Integer.parseInt(tFNSS.getText())+ " ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
					} catch (IOException e) {}		
					
					gestor.disconnect();
				} catch (ClassNotFoundException e) {} catch (SQLException e) { }
				
				dispose();			
			}						
		}
		
		if(arg0.getSource().equals(bcancelar))			
			dispose();		
	}	
	
	public static void main(String[] args) 
	{
		VA�adirPaciente v = new VA�adirPaciente();
		v.setVisible(true);
	}
}

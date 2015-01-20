package ventanas;

import gestoresBD.GestorExamen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VVista extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private JLabel texto;
	private JButton btnAtras = new JButton("Atras");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	
	public VVista()
	{
		super();
		
		//diseño ventana
		this.setSize(280, 110);		
		this.setResizable(false); 
		this.setLocationRelativeTo(null); 
		this.setUndecorated(true); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//paneles		
		Container c = getContentPane();		
		c.setLayout(new BorderLayout());
		c.setBackground(new Color(230,160,11)); 
		try {
			GestorExamen gestor = GestorExamen.getInstance();				
			gestor.connect();		
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());		
			String descrip = gestor.mostrarDescripcionExamen(1);
			texto = new JLabel("  " +descrip);
			panel.add(texto, "North");
			panel.add(new JLabel("   - Hay 10 niveles."), "Center");
			panel.add(new JLabel("   - Tienes 3 intentos por nivel."), "South");			
			panel.setBackground(new Color(230,160,11)); 

			JPanel panel2 = new JPanel();
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER));		
			panel2.add(btnAtras);
			panel2.setBackground(new Color(220,211,111)); 

			c.add(panel2, "South");
			c.add(panel, "Center");				
			
			gestor.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
		
		//eventos
		btnAtras.addActionListener(this);		
	}
	

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(btnAtras))				
			dispose();		
	}
	

	public static void main(String[] args) 
	{
		VVista v = new VVista();
		v.setVisible(true);		
	}

}

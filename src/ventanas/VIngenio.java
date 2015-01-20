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

public class VIngenio extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JLabel texto;
	private JButton btnAtras = new JButton("Atras");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
		
	public VIngenio()
	{
		super();
		
		//diseño ventana
		this.setSize(360, 110);		
		this.setResizable(false); 
		this.setLocationRelativeTo(null); 
		this.setUndecorated(true); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//paneles		
		Container c = getContentPane();		
		c.setLayout(new BorderLayout());
		c.setBackground(new Color(40,160,21)); 
		
		try {
			GestorExamen gestor = GestorExamen.getInstance();				
			gestor.connect();		
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());		
			String descrip = gestor.mostrarDescripcionExamen(3);
			texto = new JLabel("  " +descrip);
			panel.add(texto, "North");
			panel.add(new JLabel("   - Test: selecciona y comprueba cada respuesta."), "Center");
			panel.add(new JLabel("   - Juego: pulsa todas las casillas en el menor tiempo posible."), "South");			
			panel.setBackground(new Color(40,160,21)); 

			JPanel panel2 = new JPanel();
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER));		
			panel2.add(btnAtras);
			panel2.setBackground(new Color(20,211,111)); 

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
		VIngenio v = new VIngenio();
		v.setVisible(true);
	}
}

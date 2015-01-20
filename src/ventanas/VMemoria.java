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

public class VMemoria extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JLabel texto;
	private JButton btnAtras = new JButton("Atras");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	
	public VMemoria()
	{
		super();
		
		//diseño ventana
		this.setSize(290, 110);		
		this.setResizable(false); 
		this.setLocationRelativeTo(null); 
		this.setUndecorated(true); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//paneles		
		Container c = getContentPane();		
		c.setLayout(new BorderLayout());
		c.setBackground(new Color(180,55,111)); 
		
		try {
			GestorExamen gestor = GestorExamen.getInstance();				
			gestor.connect();		
			
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());		
			String descrip = gestor.mostrarDescripcionExamen(2);
			texto = new JLabel("  " +descrip);
			panel.add(texto, "North");
			panel.add(new JLabel("   - Intenta memorizar todas las fotos que veas."), "Center");
			panel.add(new JLabel("   - Adivina cual es la que no has visto."), "South");			
			panel.setBackground(new Color(180,55,111)); 

			JPanel panel2 = new JPanel();
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER));		
			panel2.add(btnAtras);
			panel2.setBackground(new Color(220,111,111)); 

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
		VMemoria v = new VMemoria();
		v.setVisible(true);
	}

}

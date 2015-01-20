package ventanas;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import clasesBasicas.Hace;

public class VMenuIngenio extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;

	private JButton btnCancelar = new JButton("Cancelar");	
	private JButton btnAceptar = new JButton("Aceptar");
	private JRadioButton rbIngenio1 = new JRadioButton("Test", false);
	private JRadioButton rbIngenio2 = new JRadioButton("Juego", false);
    private ButtonGroup bg; 	
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	private Hace h;
	
	public VMenuIngenio(Hace h) 
	{
		super();
		this.h = h;
		Container c = getContentPane();
		c.setLayout(null);	
		this.setTitle("Menu de la prueba Ingenio - Elige una");
		this.setSize(390, 290);		
		this.setResizable(false); 
		this.setLocationRelativeTo(null); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//DISEÑO PANELES
		JLabel lblNewLabel = new JLabel("");		
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/imagenes/Bombilla.jpg")));
		lblNewLabel.setBounds(0, 0, 434, 262);
		
		//coordenadas: izquierda, arriba, derecha, abajo
		JPanel botonesOpcion = new JPanel();
		botonesOpcion.setLayout(new FlowLayout());		
		botonesOpcion.setBounds(10, 30, 70, 200);
		rbIngenio1.setOpaque(false);
		rbIngenio2.setOpaque(false);
		botonesOpcion.add(rbIngenio1);
		botonesOpcion.add(rbIngenio2);
		bg = new ButtonGroup(); 
		bg.add(rbIngenio1);
		bg.add(rbIngenio2);		
		
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());		
		botones.setBounds(185, 220, 200, 220);
		
		botones.add(btnAceptar);
		botones.add(btnCancelar);
		
		botonesOpcion.setOpaque(false);
		botones.setOpaque(false);		
		
		c.add(botonesOpcion);
		c.add(botones);	
		c.add(lblNewLabel);
			
		//EVENTOS
		btnAceptar.addActionListener(this);
		rbIngenio1.addActionListener(this);
		rbIngenio2.addActionListener(this);
		btnCancelar.addActionListener(this);		
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(btnAceptar))				
		{
			if(rbIngenio1.isSelected() && !rbIngenio2.isSelected())
			{	
				dispose();    			
				VPruebaIngenio1 vpi1 = new VPruebaIngenio1(h); //prueba test
				vpi1.setVisible(true);    	
			}
			else if(!rbIngenio1.isSelected() && rbIngenio2.isSelected())
			{
				dispose();    			
				VPruebaIngenio2 vpi2 = new VPruebaIngenio2(h); //prueba juego
				vpi2.setVisible(true); 
			}
			else     						
				JOptionPane.showMessageDialog(this, "Debe seleccionar algúna opción");	
		}		

		if(arg0.getSource().equals(btnCancelar))	
		{
			dispose();
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}		
	}

}

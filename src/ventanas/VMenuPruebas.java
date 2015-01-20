package ventanas;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class VMenuPruebas extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private JButton btnCancelar;	
	private JButton btnPruebaVista;
	private JButton btnPruebaMemoria;
	private JButton btnPruebaIngenio;
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	
	public VMenuPruebas() 
	{
		Container c = getContentPane();
		c.setLayout(null);	
		this.setTitle("Menu de pruebas - Elige una");
		this.setSize(440, 300);		
		this.setResizable(false); 
		this.setLocationRelativeTo(null); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//DISEÑO PANELES
		JLabel lblNewLabel = new JLabel("");		
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/imagenes/lapiz.png")));
		lblNewLabel.setBounds(0, 0, 434, 262);
		
		JPanel botonesOpcion1 = new JPanel();
		botonesOpcion1.setLayout(new FlowLayout());		
		botonesOpcion1.setBounds(160, 126, 90, 50);
		btnPruebaVista = new JButton("");		
		botonesOpcion1.add(new JLabel("Vista     "));
		botonesOpcion1.add(btnPruebaVista);
		
		JPanel botonesOpcion2 = new JPanel();
		botonesOpcion2.setLayout(new FlowLayout());	
		botonesOpcion2.setBounds(180, 171, 110, 50);
		btnPruebaMemoria = new JButton("");	
		botonesOpcion2.add(new JLabel("Memoria     "));
		botonesOpcion2.add(btnPruebaMemoria);
		
		JPanel botonesOpcion3 = new JPanel();
		botonesOpcion3.setLayout(new FlowLayout());	
		botonesOpcion3.setBounds(242, 220, 100, 50);
		btnPruebaIngenio = new JButton("");		
		botonesOpcion3.add(new JLabel("Ingenio     "));
		botonesOpcion3.add(btnPruebaIngenio);
				
		JPanel botonCancelar = new JPanel();
		botonCancelar.setLayout(new FlowLayout());		
		btnCancelar = new JButton("Cancelar");		
		botonCancelar.setBounds(10, 228, 89, 40);
		botonCancelar.add(btnCancelar);	
		
		botonesOpcion1.setOpaque(false);	
		botonesOpcion2.setOpaque(false); 	
		botonesOpcion3.setOpaque(false); 		
		botonCancelar.setOpaque(false); 		
		
		c.add(botonesOpcion1);
		c.add(botonesOpcion2);
		c.add(botonesOpcion3);
		c.add(botonCancelar);
		c.add(lblNewLabel);	
			
		//EVENTOS
		btnPruebaVista.addActionListener(this);
		btnPruebaMemoria.addActionListener(this);
		btnPruebaIngenio.addActionListener(this);
		btnCancelar.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(btnPruebaVista))				
		{
			VVista v = new VVista();
			v.setVisible(true);
		}
		
		if(arg0.getSource().equals(btnPruebaMemoria))				
		{
			VMemoria v = new VMemoria();
			v.setVisible(true);
		}
		
		if(arg0.getSource().equals(btnPruebaIngenio))				
		{
			VIngenio v = new VIngenio();
			v.setVisible(true);
		}
		
		if(arg0.getSource().equals(btnCancelar))	
		{
			dispose();
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}		
	}
	

	public static void main(String[] args)
	{
		VMenuPruebas vmp = new VMenuPruebas();
		vmp.setVisible(true);
	}

}

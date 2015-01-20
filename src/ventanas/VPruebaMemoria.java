package ventanas;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.Timer;

import clasesBasicas.Hace;


public class VPruebaMemoria extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;

	private JLabel lblImagen = new JLabel ("");	
	private JLabel lblFondo = new JLabel ("");	
		
	private Timer tiempoVisible;	
	private int selectorImg;
	private Hace h;
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	
	public VPruebaMemoria(Hace h)
	{		
		//DISEÑO VENTANA
		super("Examen de memoria");
		this.h = h;		
		this.setSize(new Dimension(500, 420));
		this.setLocationRelativeTo(null);
		this.setResizable(false);	
		this.setUndecorated(true); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		
		//DISEÑO PANELES	
		Container c = getContentPane();
		c.setLayout(null);	
		
		//FONDO		
		lblFondo.add(lblImagen);
		lblFondo.setBounds(0, 0, getWidth(), getHeight());			
		cambiarImagen();
		c.add(lblFondo);
		
		//TEMPORIZADOR
		tiempoVisible = new Timer(2000,this); //Cada dos segundos llama al action performed 		
		tiempoVisible.start();
		selectorImg = 0;		

		//EVENTOS
		lblImagen.addFocusListener(null);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(tiempoVisible));
		{
			int numImag = cambiarImagen();
			if(numImag==1)
			{
				dispose();
				VPruebaMemoria2 vpm2 = new VPruebaMemoria2(h);
				vpm2.setVisible(true);
			}
		}
		
	}

	/**
	 * Método que devuelve la imagen actual y va incrementando para cambiarlas cada 2 segundos
	 * @return int
	 */
	private int cambiarImagen()
	{
		int numImag = 0;
		switch(selectorImg)
		{
			case 0: lblImagen.setIcon(new ImageIcon (getClass().getResource("/imagenes/casa1.gif")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());	
					break;
			case 1: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/mariposa.jpg")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					break;
			case 2: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/seis.png")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					break;
			case 3: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa2.jpg")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					break;
			case 4: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/paloma1.jpg")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					break;
			case 5: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/nueve.jpg")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					break;
			case 6: lblImagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/paloma2.jpg")));
					lblImagen.setBounds(0, 0, getWidth(), getHeight());
					numImag = 1;
					break;
		}
		selectorImg++;
		if(selectorImg == 7)
		{
			tiempoVisible.stop();//lo para en la ultima imagen
		}
		return numImag;
	}

}

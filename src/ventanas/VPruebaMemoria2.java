package ventanas;


import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container; 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import clasesBasicas.Hace;


public class VPruebaMemoria2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private int acierto=0;
	private int fallos=0;
	private JLabel lblImagen1 = new JLabel("");
	private JLabel lblImagen2 = new JLabel();
	private JLabel lblImagen3 = new JLabel();
	private JLabel lblImagen4 = new JLabel();
	private JLabel lblImagen5 = new JLabel();
	private JLabel lblImagen6 = new JLabel();
	private JLabel lblImagen7 = new JLabel();
	private JLabel lblImagen8 = new JLabel();
	
	private JLabel lblRespuesta= new JLabel ("Elige la imagen que antes no ha salido:");
	private JTextField txRespuesta= new JTextField(1);	
	private JButton bAbandonar = new JButton("Abandonar");
	private JButton bAceptar= new JButton ("Aceptar");

	private Hace hace;
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";

	public VPruebaMemoria2(Hace hace){

		//VENTANA
		super("Examen de Memoria");	
		this.hace=hace;
		this.setSize(new Dimension(550,300));
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		icon=new ImageIcon (getClass().getResource(rutaImagenes+"logo.jpg"));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		//IMAGENES VISUALIZAR, PANEL NORTE 
		Container c=getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel panelImagenesSuperior = new JPanel();//panel arriba
		panelImagenesSuperior.setLayout(new FlowLayout());
		lblImagen1.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa22.jpg")));
		lblImagen1.setToolTipText("1");
		lblImagen2.setIcon(new ImageIcon(getClass().getResource("/imagenes/mariposa22.gif")));
		lblImagen2.setToolTipText("2");
		lblImagen3.setIcon(new ImageIcon (getClass().getResource("/imagenes/nueve1.jpg")));
		lblImagen3.setToolTipText("3");
		lblImagen4.setIcon(new ImageIcon(getClass().getResource("/imagenes/paloma22.jpg")));
		lblImagen4.setToolTipText("4");
		panelImagenesSuperior.add(lblImagen1);
		panelImagenesSuperior.add(lblImagen2);
		panelImagenesSuperior.add(lblImagen3);
		panelImagenesSuperior.add(lblImagen4);
		panelImagenesSuperior.setBackground(new Color(255,255,255)); 
		
		JPanel panelImagenesInferior = new JPanel();// panel abajo
		panelImagenesInferior.setLayout(new FlowLayout());
		lblImagen5.setIcon(new ImageIcon(getClass().getResource("/imagenes/paloma11.jpg")));
		lblImagen5.setToolTipText("5");
		lblImagen6.setIcon(new ImageIcon(getClass().getResource("/imagenes/seis1.png")));
		lblImagen6.setToolTipText("6");
		lblImagen7.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa11.gif")));
		lblImagen7.setToolTipText("7");
		lblImagen8.setIcon(new ImageIcon(getClass().getResource("/imagenes/mariposa1.jpg")));
		lblImagen8.setToolTipText("8");
		panelImagenesInferior.add(lblImagen5);
		panelImagenesInferior.add(lblImagen6);
		panelImagenesInferior.add(lblImagen7);
		panelImagenesInferior.add(lblImagen8);
		panelImagenesInferior.setBackground(new Color(255,255,255)); 
		
		//RESTO PANELES 		
		JPanel panelRespuesta = new JPanel(); //panel centro
		panelRespuesta.setBounds(163, 222, 268, 67);
		panelRespuesta.setLayout(new BorderLayout());
		JPanel panelRespuesta1 = new JPanel();
		panelRespuesta1.setLayout(new FlowLayout());
		panelRespuesta1.add(lblRespuesta);
		panelRespuesta1.add(txRespuesta);
		txRespuesta.setToolTipText("Pasa el cursor por cada imagen para saber el numero");
		JPanel panelRespuesta2 = new JPanel();
		panelRespuesta2.setLayout(new FlowLayout());		
		panelRespuesta2.add(bAceptar);
		panelRespuesta2.add(bAbandonar);
		panelRespuesta.add(panelRespuesta1, "North");
		panelRespuesta.add(panelRespuesta2, "Center");		
		panelRespuesta.setBackground(new Color(255,255,255)); 
		
		c.add(panelImagenesSuperior, "North");
		c.add(panelImagenesInferior, "South");
		c.add(panelRespuesta, "Center");	

		//EVENTOS
		bAceptar.addActionListener(this);
		bAbandonar.addActionListener(this);
		txRespuesta.addActionListener(this);
	}

	/**
	 * Guarda los resultados obtenidos comprobando antes los intentos restantes
	 */
	public void aceptar()
	{
		String s = txRespuesta.getText();
		
		try {
			GestorHace gestor = GestorHace.getInstance();			
			GestorPaciente gestor2 = GestorPaciente.getInstance();
			gestor.connect();
			gestor2.connect();

			if(s.equals("2")) //respuesta correcta
			{
				JOptionPane.showMessageDialog(this, "Has acertado. Se han guardado tus resultados.");
				acierto++;
				Hace h = new Hace(hace.getNss_paciente(), hace.getCodigo_examen(), hace.getFecha(), hace.getHoraCita(), hace.getIntentos()+1, hace.getFallos()+fallos, hace.getAciertos()+acierto);
				gestor.modificarTodo(h);
				gestor2.modificarResultadoPrueba(acierto, hace.getNss_paciente());					
				dispose();
				VConsultaMedica v = new VConsultaMedica();
				v.setVisible(true);
			}	
			else
			{
				fallos++;

				if( fallos>=3)
				{
					JOptionPane.showMessageDialog(this, "LA PRUEBA HA FINALIZADO" );

					Hace h = new Hace(hace.getNss_paciente(), hace.getCodigo_examen(), hace.getFecha(), hace.getHoraCita(), hace.getIntentos()+1, hace.getFallos()+fallos, hace.getAciertos());
					gestor.modificarTodo(h);					
					dispose();
					VConsultaMedica v = new VConsultaMedica();
					v.setVisible(true);
				}	
				else
				{
					int i = 3 - fallos;
					JOptionPane.showMessageDialog(this, "Has fallado, tienes " +i+" oportunidades " );
				}
			}	
			
			gestor.disconnect();
			gestor2.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(bAceptar))
		{
			aceptar();			
		}
		
		if(arg0.getSource().equals(bAbandonar))
		{
			JOptionPane.showMessageDialog(this, "No has finalizado la prueba", "Abandono", JOptionPane.ERROR_MESSAGE);	
			
			dispose();	
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}
	}

}


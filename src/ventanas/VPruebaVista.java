package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class VPruebaVista extends JFrame implements ActionListener
{	
	private static final long serialVersionUID = 1L;
	
	private int aciertos = 0;
	private int fallos = 0;
	private int f = 0; //fallos por nivel
	private int tam = 30; //tamaño letras
	private JLabel lblFondo = new JLabel("");	
	private JLabel lblMensaje;
	private int nivel;
	private JLabel lblLetra1;
	private JLabel lblLetra2;
	private JLabel lblLetra3;
	private JLabel lblRespuesta = new JLabel("Escribe las letras que ves: ");
	private JTextField txRespuesta = new JTextField(25);
	private JButton bAceptar = new JButton("Aceptar");
	private JButton bPasar = new JButton("Pasar");
	private JButton bAbandonar = new JButton("Abandonar");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";	
	private Hace cita;	
	
	public VPruebaVista(Hace cita)
	{
		//DISEÑO VENTANA
		super("Examen de vista");
		this.cita = cita;
		this.setSize(new Dimension(500, 420));
		this.setLocationRelativeTo(null);
		this.setResizable(false);	
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//VISUALIZAR LETRAS ALEATORIAMENTE
		int valorletra1 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char l1 = (char) valorletra1; 
		int valorletra2 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char l2 = (char) valorletra2; 
		int valorletra3 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char l3 = (char) valorletra3; 		
		lblLetra1 = new JLabel("  " +l1+ "  ");
		lblLetra2 = new JLabel("  " +l2+ "  ");
		lblLetra3 = new JLabel("  " +l3+ "  ");		
				
		//DISEÑO PANELES	
		Container c = getContentPane();
		c.setLayout(null);
		
		JPanel panelLetras = new JPanel(); //panel norte 
		panelLetras.setBounds(0, 20, 500, 200);
		panelLetras.setLayout(new FlowLayout(FlowLayout.CENTER));		
		lblLetra1.setBackground(null);	
		panelLetras.add(lblLetra1);
		lblLetra1.setFont(new Font("Arial", Font.BOLD, tam));
		lblLetra1.setOpaque(true);
		panelLetras.add(lblLetra2);
		lblLetra2.setFont(new Font("Arial", Font.BOLD, tam));
		lblLetra2.setOpaque(true);
		panelLetras.add(lblLetra3);
		lblLetra3.setFont(new Font("Arial", Font.BOLD, tam));
		lblLetra3.setOpaque(true);
		panelLetras.setOpaque(false);
				
		JPanel panelMensaje = new JPanel(); //panel central
		panelMensaje.setBounds(0, 230, 390, 300);
		panelMensaje.setLayout(new FlowLayout(FlowLayout.RIGHT));
		nivel = 1;
		panelMensaje.add(lblMensaje = new JLabel("Nivel " +nivel)); //en el evento de aceptar se incluirá un contador de nivel
		lblMensaje.setFont(new Font("Arial", Font.BOLD, 20));
		panelMensaje.setOpaque(false);		
		
		JPanel panelRespuesta = new JPanel(); //panel sur
		panelRespuesta.setBounds(0, 315, 500, 420);
		panelRespuesta.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelRespuesta.add(lblRespuesta);
		panelRespuesta.add(txRespuesta);
		panelRespuesta.add(bAceptar);
		panelRespuesta.add(bPasar);
		panelRespuesta.add(bAbandonar);
		panelRespuesta.setOpaque(false);
	
		//FONDO
		lblFondo.setIcon(new ImageIcon(getClass().getResource("/imagenes/vista.gif")));
		lblFondo.setBounds(0, 0, getWidth(), getHeight());
		
		c.add(panelLetras);
		c.add(panelMensaje);
		c.add(panelRespuesta);
		c.add(lblFondo);
		
		//EVENTOS
		txRespuesta.addActionListener(this);
		txRespuesta.setToolTipText("escribe cada letra separada por un espacio");
		bAceptar.addActionListener(this);
		bPasar.addActionListener(this);
		bAbandonar.addActionListener(this);
		
	}
	
	/**
	 * Método aceptar para su evento de botón
	 * -Une en un solo String las letras de las labels que se muestran y lo compara con el que se mete por teclado
	 * -Aumenta los aciertos si es correcto
	 * -Reduce el tamaño de las letras para el siguiente nivel 
	 * -Se terminará cuando se superen los 10 niveles
	 * -Si en un mismo nivel tiene más de 3 fallos, finalizará la prueba
	 */
	public void aceptar()
	{	
		String s1 = lblLetra1.getText();
		String s2 = lblLetra2.getText();
		String s3 = lblLetra3.getText();		
		String ss = s1.substring(2, 4) + s2.substring(2, 4) + s3.substring(2, 3);
		String s = txRespuesta.getText();				
		if (s.equalsIgnoreCase(ss))
		{			
			if (nivel<10)
			{			
				if (f>0)
					f = 0;
				aciertos++;				
				JOptionPane.showMessageDialog(this, "Nivel " +nivel+ ": superado");
				nivel++;
				lblMensaje.setText("Nivel " +nivel); 
				txRespuesta.setText("");	
				tam = tam-3;
				//ponemos letras aleatorias
				int valor1 = (int) Math.floor(Math.random()*(65-90+1)+90);
				char letra1 = (char) valor1; 
				int valor2 = (int) Math.floor(Math.random()*(65-90+1)+90);
				char letra2 = (char) valor2; 
				int valor3 = (int) Math.floor(Math.random()*(65-90+1)+90);
				char letra3 = (char) valor3; 	
				lblLetra1.setText("  " +letra1+ "  ");
				lblLetra2.setText("  " +letra2+ "  ");
				lblLetra3.setText("  " +letra3+ "  ");			
				lblLetra1.setFont(new Font("Arial", Font.BOLD, tam));
				lblLetra2.setFont(new Font("Arial", Font.BOLD, tam));
				lblLetra3.setFont(new Font("Arial", Font.BOLD, tam));
			}
			else
			{
				aciertos++;				
				JOptionPane.showMessageDialog(this, "PRUEBA TERMINADA. Nivel: " +nivel+ " Aciertos: " +aciertos+ " Fallos: " +fallos);
				guardarCambiosBD(aciertos, fallos);				
				dispose();
				VConsultaMedica v = new VConsultaMedica();
				v.setVisible(true);
			}
		}
		else
		{
			if (f<2)
			{
				f++;
				fallos++;
				JOptionPane.showMessageDialog(this, "Respuesta incorrecta, vuelve a intentarlo");
				txRespuesta.setText("");
			}
			else
			{
				fallos++;				
				JOptionPane.showMessageDialog(this, "FIN PRUEBA. Nivel: " +nivel+ " Aciertos: " +aciertos+ " Fallos: " +fallos);
				f = 0;
				guardarCambiosBD(aciertos, fallos);	
				dispose();
				VConsultaMedica v = new VConsultaMedica();
				v.setVisible(true);
			}		
		}	
	}
	
	/**
	 * Método pasar para el evento del botón
	 * -El paciente puede pasar al siguiente nivel si no ve las letras del actual
	 * -Se le contará como fallo y se le sumará a los actuales
	 */
	public void pasar()
	{
		fallos++;
		JOptionPane.showMessageDialog(this, "Nivel " +nivel+ ": no superado");
		nivel++;
		lblMensaje.setText("Nivel " +nivel); 
		txRespuesta.setText("");	
		tam = tam-3;
		//letras aleatorias
		int valor1 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char letra1 = (char) valor1; 
		int valor2 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char letra2 = (char) valor2; 
		int valor3 = (int) Math.floor(Math.random()*(65-90+1)+90);
		char letra3 = (char) valor3; 	
		lblLetra1.setText("  " +letra1+ "  ");
		lblLetra2.setText("  " +letra2+ "  ");
		lblLetra3.setText("  " +letra3+ "  ");
		lblLetra1.setFont(new Font("Arial", Font.BOLD, tam));
		lblLetra2.setFont(new Font("Arial", Font.BOLD, tam));
		lblLetra3.setFont(new Font("Arial", Font.BOLD, tam));		
	}	
	
	/**
	 * Guarda en la base de datos los datos obtenidos de ese paciente
	 * @param aciertos
	 * @param fallos
	 */
	public void guardarCambiosBD(int aciertos, int fallos)
	{
		try {
			GestorHace gestor = GestorHace.getInstance();			
			GestorPaciente gestor2 = GestorPaciente.getInstance();
			gestor.connect();	
			gestor2.connect();
			
			Hace h = new Hace(cita.getNss_paciente(), cita.getCodigo_examen(), cita.getFecha(), cita.getHoraCita(), cita.getIntentos()+1, cita.getFallos()+fallos, cita.getAciertos()+aciertos);
			gestor.modificarTodo(h);
			gestor2.modificarResultadoPrueba(aciertos, cita.getNss_paciente());			
			
			gestor.disconnect();
			gestor2.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}			
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(bAceptar))
			aceptar();		
		
		if(arg0.getSource().equals(bPasar))
			pasar();
				
		if(arg0.getSource().equals(bAbandonar))
		{
			if (nivel==1)
				JOptionPane.showMessageDialog(this, "No has superado ningún nivel", "Abandono", JOptionPane.ERROR_MESSAGE);		
			else
				JOptionPane.showMessageDialog(this, "Has llegado al nivel " +(nivel-1), "Abandono", JOptionPane.ERROR_MESSAGE);		
			dispose();	
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}					
	}

}

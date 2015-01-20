package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;
import gestoresBD.GestorPregunta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import clasesBasicas.Hace;
import clasesBasicas.Pregunta;

public class VPruebaIngenio1 extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private Hace h;
	private LinkedList<Pregunta> lpreguntas;
	private JList<String> preguntas = new JList<String>();
	private DefaultListModel<String> modeloPreguntas; 
	private JButton finalizar = new JButton("Finalizar");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";	
	private JButton boton = new JButton();	
	private JLabel correcto = new JLabel("");	
	private JLabel incorrecto = new JLabel("");
	private JLabel puntuacion = new JLabel("0");	
	private int aciertos = 0;
	private int fallos = 0;
	private boolean contestada1 = false;
	private boolean contestada2 = false;
	private boolean contestada3 = false;
	private boolean contestada4 = false;
	private boolean contestada5 = false;
	private boolean contestada6 = false;
	private boolean contestada7 = false;
	private boolean contestada8 = false;
	private boolean contestada9 = false;
	private boolean contestada10 = false;	
	
	public VPruebaIngenio1(Hace h)
	{
		super("Prueba de Ingenio - Test sabiduria");
		this.h = h;
		this.setSize(700, 600);
		this.setLocationRelativeTo(null); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//guardamos las imagenes de las respuestas en las jlabel
		correcto.setIcon(new ImageIcon(getClass().getResource("/imagenes/correcto.jpg")));
		incorrecto.setIcon(new ImageIcon(getClass().getResource("/imagenes/incorrecto.jpg")));
						
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JPanel panelPreguntas = new JPanel();
		panelPreguntas.setLayout(new BorderLayout());
				
		lpreguntas = new LinkedList<Pregunta>();
		modeloPreguntas = new DefaultListModel<String>();	
		
		try {
			GestorPregunta gestor = GestorPregunta.getInstance();
			gestor.connect();
			lpreguntas = gestor.listarPreguntasPrueba(h.getCodigo_examen()); 
			int i = 0;
			try{
				for (Pregunta p : lpreguntas)
				{    
					String pregunta = p.getPregunta();
					modeloPreguntas.addElement(" " +(i+1)+ ": " +pregunta);
					modeloPreguntas.addElement(" Respuesta a: " +p.getRespuestaCorrecta());
					modeloPreguntas.addElement(" Respuesta b: " +p.getRespuestaIncorrecta1());
					modeloPreguntas.addElement(" Respuesta c: " +p.getRespuestaIncorrecta2());
					modeloPreguntas.addElement(" ");
					i++;					
				}					
			} catch(Exception e) {}
			
			gestor.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}
		
		preguntas.setModel(modeloPreguntas);
		ScrollPane scroll = new ScrollPane();		
		scroll.add(preguntas);	
		
		panelPreguntas.add(scroll, "Center");
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());
		botones.add(finalizar);
		
		JPanel panelOpciones = new JPanel();
		panelOpciones.setLayout(new BorderLayout());
		JPanel panelOpciones1 = new JPanel();
		panelOpciones1.setLayout(new FlowLayout());
		panelOpciones1.setBackground(new Color(255, 255, 255));
		panelOpciones1.add(boton);
		boton.setToolTipText("comprobar respuesta");
		JPanel panelOpciones2 = new JPanel();
		panelOpciones2.setLayout(new FlowLayout());
		panelOpciones2.setBackground(new Color(255, 255, 255));
		panelOpciones2.add(correcto);
		panelOpciones2.add(incorrecto);
		correcto.setVisible(false);
		incorrecto.setVisible(false);
		JPanel panelOpciones3 = new JPanel();
		panelOpciones3.setLayout(new FlowLayout());
		panelOpciones3.setBackground(new Color(255, 255, 255));
		puntuacion.setFont(new Font("Arial", Font.BOLD, 22));
		panelOpciones3.add(puntuacion);
		panelOpciones.add(panelOpciones1, "North");
		panelOpciones.add(panelOpciones2, "Center");
		panelOpciones.add(panelOpciones3, "South");						
		panelOpciones.setBackground(new Color(255, 255, 255));
		
		c.add(panelOpciones, "West");
		c.add(panelPreguntas, "Center");
		c.add(botones, "South");
		
		finalizar.addActionListener(this);
		boton.addActionListener(this);		
	}
	
	/**
	 * Comprueba si la pregunta que queremos comprobar ya ha sido respondida antes
	 * @param seleccionada
	 * @return boolean
	 */
	public boolean comprobarPreguntaRespondida(int seleccionada)
	{
		boolean si = true;			
		switch(seleccionada)
		{
			case 1: case 2: case 3:
				if(contestada1==false)
					contestada1 = true;
				else
					si = false;
				break;
			case 6: case 7: case 8:
				if(contestada2==false)
					contestada2 = true;
				else
					si = false;
				break;
			case 11: case 12: case 13:
				if(contestada3==false)
					contestada3 = true;
				else
					si = false;
				break;
			case 16: case 17: case 18:
				if(contestada4==false)
					contestada4 = true;
				else
					si = false;
				break;
			case 21: case 22: case 23:
				if(contestada5==false)
					contestada5 = true;
				else
					si = false;
				break;
			case 26: case 27: case 28:
				if(contestada6==false)
					contestada6 = true;
				else
					si = false;
				break;
			case 31: case 32: case 33:
				if(contestada7==false)
					contestada7 = true;
				else
					si = false;
				break;
			case 36: case 37: case 38:
				if(contestada8==false)
					contestada8 = true;
				else
					si = false;
				break;
			case 41: case 42: case 43:
				if(contestada9==false)
					contestada9 = true;
				else
					si = false;
				break;
			case 46: case 47: case 48:
				if(contestada10==false)
					contestada10 = true;
				else
					si = false;
				break;
		}		
		return si;
	}	
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(finalizar))				
		{
			if((contestada1==false) || (contestada2==false) || (contestada3==false) || (contestada4==false) || (contestada5==false) || (contestada6==false) || (contestada7==false) || (contestada8==false) || (contestada9==false) | (contestada10==false))
				JOptionPane.showMessageDialog(this, "No has terminado de responder todas las preguntas.");
			else
			{
				JOptionPane.showMessageDialog(this, "Ya acertado " +aciertos+ " preguntas y has fallado " +fallos+ ".");
				try {
					GestorHace gestor = GestorHace.getInstance();
					GestorPaciente gestor2 = GestorPaciente.getInstance();
					gestor.connect();	            				
					gestor2.connect();
					Hace ha = new Hace(h.getNss_paciente(), h.getCodigo_examen(), h.getFecha(), h.getHoraCita(), h.getIntentos()+1, h.getFallos()+fallos, h.getAciertos()+aciertos);
					gestor.modificarTodo(ha);
					gestor2.modificarResultadoPrueba(aciertos, h.getNss_paciente());							
					gestor.disconnect();
					gestor2.disconnect();		
				} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
				dispose();
				VConsultaMedica v = new VConsultaMedica();
				v.setVisible(true);	
			}				
		}
		
		if(arg0.getSource().equals(boton))	
		{
			//miro qué respuesta de la lista está seleccionada
			int preguntaSeleccionada = preguntas.getSelectedIndex();			
			preguntas.setSelectedIndex(preguntaSeleccionada);				
			if(preguntaSeleccionada>=0) //si ha seleccionado algo
			{
				switch(preguntaSeleccionada)
				{
					case 0: case 5: case 10: case 15: case 20: case 25: case 30: case 35: case 40: case 45: 
						JOptionPane.showMessageDialog(this, "Debes seleccionar alguna respuesta a esta pregunta");
						break;
					case 4: case 9: case 14: case 19: case 24: case 29: case 34: case 39: case 44: case 49: 
						JOptionPane.showMessageDialog(this, "No has seleccionado una respuesta válida.");
						break;
					case 1: case 6: case 11: case 16: case 21: case 26: case 31: case 36: case 41: case 46: 
						boolean seguir1 = comprobarPreguntaRespondida(preguntaSeleccionada);
						if(seguir1==true)
						{
							correcto.setVisible(true);
							aciertos++;
							puntuacion.setText(Integer.toString(aciertos));
							this.repaint();
							Thread hilo1 = new Thread()
							{
								public void run()
								{
									try {
										Thread.sleep(3000);		
										correcto.setVisible(false);
										VPruebaIngenio1.this.repaint();
									} catch (InterruptedException ie) { ie.printStackTrace(); }								
								}
							};
							hilo1.start();	
						}
						else
							JOptionPane.showMessageDialog(this, "Ya has respondido a esa pregunta.");
						break;
					default: 
						boolean seguir2 = comprobarPreguntaRespondida(preguntaSeleccionada);
						if(seguir2==true)
						{
							incorrecto.setVisible(true);
							fallos++;
							Thread hilo2 = new Thread()
							{
								public void run()
								{
									try {
										Thread.sleep(3000);		
										incorrecto.setVisible(false);
										VPruebaIngenio1.this.repaint();
									} catch (InterruptedException ie) { ie.printStackTrace(); }								
								}
							};
							hilo2.start();	
						}
						else
							JOptionPane.showMessageDialog(this, "Ya has respondido a esa pregunta.");
						break;
				}
			}
			else
				JOptionPane.showMessageDialog(this, "Para poder comprobar si es correcta, tienes que seleccionar alguna.");
		}
		
	}	
	
}
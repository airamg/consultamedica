package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import javax.swing.WindowConstants;

import utils.Tablero;

import clasesBasicas.Hace;

public class VPruebaIngenio2 extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	private Hace h;
	private Tablero tablero;
	private JButton empezar = new JButton("Empezar");	
	private JButton terminar = new JButton("Terminar");
	private JLabel contador1 = new JLabel("Puntos: 10");	
	private JLabel contador2 = new JLabel(" ");
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
	private JProgressBar pb = new JProgressBar(0, 0, 1000);
	private Thread hilo = null;
	int cont = 10; //empieza la prueba con 10 puntos
    static int counter = 1000;
    private int contador;
        
	public VPruebaIngenio2(Hace h)
	{
		super("Prueba de Ingenio - Juego destreza");		
		this.h = h;			
		this.setResizable(false); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         
        pb.setValue(1000);
        
        //DISEÑO PANELES
        Container c = getContentPane();
		c.setLayout(new GridLayout(1, 2));
			
		tablero = new Tablero(10, true, contador); 
		this.contador = tablero.getContador();
		tablero.setLayout(new GroupLayout(tablero));
		tablero.setVisible(false);
		
		       
		JPanel opciones = new JPanel();
		opciones.setLayout(new BorderLayout());
		
		JPanel opciones1 = new JPanel();
		opciones1.setLayout(new FlowLayout());
		opciones1.add(pb);
		JPanel opciones2 = new JPanel();
		opciones2.setLayout(new FlowLayout());
		opciones2.add(contador1);
		opciones2.add(contador2);
		contador2.setVisible(false);
		JPanel opciones3 = new JPanel();
		opciones3.setLayout(new FlowLayout());
		opciones3.add(empezar);
		opciones3.add(terminar);	
		terminar.setEnabled(false);
		opciones.add(opciones1, "North");
		opciones.add(opciones2, "Center");
		opciones.add(opciones3, "South");
			
        c.add(tablero);
        c.add(opciones);
        pack();
        
        //EVENTOS      
        empezar.addActionListener(this);
        terminar.addActionListener(this);
        pb.setStringPainted(true); 
        
        JOptionPane.showMessageDialog(null, "Al abrirse la ventana, pulsa \"empezar\" para hacer la prueba.");
    }

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(terminar)) //no se como hacer para que no pueda pulsarlos hasta que esten todas las casillas dadas la vuelta
		{
			hilo.interrupt(); 
			JOptionPane.showMessageDialog(null, "Has conseguido " +cont+ " puntos.");
			contador1.setVisible(false);
			contador2.setText("Fin de prueba. Puntos: " +Integer.toString(cont));
			contador2.setVisible(true);	
			
			//guardar cambios en DB			
			try {
				GestorHace gestor = GestorHace.getInstance();
				GestorPaciente gestor2 = GestorPaciente.getInstance();
				gestor.connect();
				gestor2.connect();
				int fallos = 10 - cont;
				Hace ha = new Hace(h.getNss_paciente(), h.getCodigo_examen(), h.getFecha(), h.getHoraCita(), h.getIntentos()+1, h.getFallos()+fallos, h.getAciertos()+cont);
				gestor.modificarTodo(ha);
				gestor2.modificarResultadoPrueba(cont, h.getNss_paciente());							
				gestor.disconnect();
				gestor2.disconnect();
			} catch (ClassNotFoundException e) {} catch (SQLException e) {}		
						
			dispose();
			VConsultaMedica v = new VConsultaMedica();
			v.setVisible(true);
		}
		
		if(arg0.getSource().equals(empezar)) 
		{
			tablero.setVisible(true);			
			hilo = new Thread()
		    {
		      public void run() {		    	
		    	  try {
						Thread.sleep(50);
						pb.setValue(counter-10);						
						counter--;
						VPruebaIngenio2.this.repaint();
						if(counter==800 || counter==600 || counter==500 || counter==400 || counter==300 || counter==200 || counter==100 || counter==50 || counter==30 || counter==15)
						{
							cont--;
							contador1.setText("Puntos: " +Integer.toString(cont));
							VPruebaIngenio2.this.repaint();
						}
						if(tablero.getContador()==100) //si ha pulsado todas las casillas se habilita el boton terminar
						{
							terminar.setEnabled(true);
							VPruebaIngenio2.this.repaint();
						}
						if (counter==10) //si se acaba el tiempo solo suma intentos, no guarda aciertos ni fallos
						{
	            			JOptionPane.showMessageDialog(null, "¡Se ha acabado el tiempo!");
	            			
	            			//guardar cambios en DB			
	            			try {
	            				GestorHace gestor = GestorHace.getInstance();
	            				gestor.connect();	            				
	            				Hace ha = new Hace(h.getNss_paciente(), h.getCodigo_examen(), h.getFecha(), h.getHoraCita(), h.getIntentos()+1, h.getFallos(), h.getAciertos());
	            				gestor.modificarTodo(ha);
	            				gestor.disconnect();	            		
	            			} catch (ClassNotFoundException e) {} catch (SQLException e) {}	
	            			
	            			VPruebaIngenio2.this.dispose();
	            			VConsultaMedica v = new VConsultaMedica();
	        				v.setVisible(true);	 
	        				hilo.stop();           		
						}					
						else
							hilo.run();	
						
					} catch (InterruptedException ie) {
						hilo.stop();	
					}  
		      }		    
		    };	
			hilo.start();		
		}
	}
	
}

package ventanas;

import gestoresBD.GestorHace;
import gestoresBD.GestorPaciente;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;


import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;


import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;



import javax.swing.JLabel;
import javax.swing.JList;

import clasesBasicas.Hace;

public class VConsultaMedica extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private JLabel lbFecha;
	private String fecha;
	private JList<Object> lListaCitas = new JList<Object>();
	private JButton btnDarCita = new JButton("Dar Cita");
	private JButton btnVerPruebas = new JButton("Ver Pruebas");
	private JButton btnAñadir = new JButton("Añadir paciente");
	private JButton btnConsultarHistorial = new JButton("Consultar historial");
	private JButton btnSalir = new JButton("Salir");	
	private JLabel lblNewLabel = new JLabel("");
	private JLabel lblEleccion = new JLabel("¿Qué desea hacer?");
	private JRadioButton rbCambiarCita = new JRadioButton("Cambiar cita", false);
	private JRadioButton rbHacerPrueba = new JRadioButton("Empezar prueba", false);
	private ButtonGroup bg; 
	private LinkedList<Object> ll;
	private DefaultListModel<Object> modeloCitas;
	private LinkedList<Hace> lCitas;
	private JButton btnAceptar = new JButton("Aceptar");
	
	//para panel de cambiar cita que solo se muestra cuando aceptar
	private JLabel lbCambio = new JLabel("CAMBIO DE CITA");
	private JLabel lbFechaa = new JLabel("Fecha (dd/mm/aa): ");	
	private JTextField tFFecha = new JTextField(6);	
	private String[] sHora = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30"};
	private JComboBox cBHora = new JComboBox(sHora);
	private JButton btnGuardar = new JButton("Guardar");
	private JButton bCancelar = new JButton("Cancelar");
	private static String rutaImagenes = "/imagenes/";
	private ImageIcon icon;
	private Timer refresco = new Timer(9000, this);
	
	
	public VConsultaMedica()
	{
		//DISEÑO VENTANA
		super();
		refresco.start();
		this.setTitle("Consulta médica - Inicio");
		this.setMinimumSize(new Dimension(650, 450));
		this.setLocationRelativeTo(null);
		this.setResizable(false);	
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
							
		//DISEÑO PANELES
		Container c = getContentPane();
		c.setLayout(null);
		
		JPanel panelFecha = new JPanel(); //panel norte
		panelFecha.setBounds(0, 0, 634, 34);
		panelFecha.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//poniendo la fecha actual en el panel
		long temp = System.currentTimeMillis();
		Date d= new Date(temp);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		DateFormat df = DateFormat.getInstance();				
		fecha = df.format(d);	
		panelFecha.add(lbFecha = new JLabel(fecha));
		lbFecha.setFont(new Font("Stencil", Font.BOLD, 23)); //cambio formato texto fecha
				
		JPanel panelCitas = new JPanel(); //panel central izquierda		
		panelCitas.setBounds(10, 50, getWidth()-190, getHeight()-270); 
		panelCitas.setLayout(new BorderLayout());		
		llenarPanelCitas();		
		ScrollPane scroll = new ScrollPane();		
		scroll.add(lListaCitas);		
		panelCitas.add(scroll);	
			
		JPanel panelCitas1 = new JPanel(); //panel central derecha (pregunta)		
		panelCitas1.setBounds(10, 50, getWidth()-30, getHeight()-350); 
		panelCitas1.setLayout(new FlowLayout(FlowLayout.RIGHT));		
		panelCitas1.add(lblEleccion);					
		JPanel panelCitas2 = new JPanel(); //panel central derecha (opcion 1)
		panelCitas2.setBounds(10, 75, getWidth()-30, getHeight()-250); 
		panelCitas2.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		panelCitas2.add(rbCambiarCita);
		JPanel panelCitas3 = new JPanel(); //panel central derecha (opcion 2)
		panelCitas3.setBounds(10, 97, getWidth()-30, getHeight()-200); 
		panelCitas3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCitas3.add(rbHacerPrueba);	
		bg = new ButtonGroup(); //para que solo se pueda pulsar un boton de las dos opciones		
		bg.add(rbCambiarCita);
		bg.add(rbHacerPrueba);
		rbCambiarCita.setFont(new Font("Arial", Font.PLAIN, 12));
		rbHacerPrueba.setFont(new Font("Arial", Font.PLAIN, 12));
		JPanel panelCitas5 = new JPanel(); //panel central derecha (boton)
		panelCitas5.setBounds(10, 135, getWidth()-30, getHeight()-250); 
		panelCitas5.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCitas5.add(btnAceptar);
		
		JPanel panelCambio1 = new JPanel(); //paneles para cambio de cita del evento aceptar
		panelCambio1.setBounds(10, 215, getWidth()-40, getHeight()-300); 
		panelCambio1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCambio1.add(lbCambio);		
		JPanel panelCambio2 = new JPanel(); 
		panelCambio2.setBounds(10, 240, getWidth()-40, getHeight()-360); 
		panelCambio2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCambio2.add(lbFechaa);	
		panelCambio2.add(tFFecha);			
		panelCambio2.add(cBHora);		
		JPanel panelCambio3 = new JPanel(); 
		panelCambio3.setBounds(10, 275, getWidth()-40, getHeight()-390); 	
		panelCambio3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelCambio3.add(bCancelar);
		panelCambio3.add(btnGuardar);
		
		lbCambio.setVisible(false);
		lbFechaa.setVisible(false);
		tFFecha.setVisible(false);
		cBHora.setVisible(false);
		btnGuardar.setVisible(false);
		bCancelar.setVisible(false);
		
		JPanel panelBotones = new JPanel(); //panel sur
		panelBotones.setBounds(0, 379, 634, 33);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBotones.add(btnDarCita);
		panelBotones.add(btnVerPruebas);
		panelBotones.add(btnAñadir);
		panelBotones.add(btnConsultarHistorial);
		panelBotones.add(btnSalir); 		
        
		panelFecha.setOpaque(false);
		panelCitas.setOpaque(false);
		panelCitas1.setOpaque(false);
		panelCitas2.setOpaque(false);		
		panelCitas3.setOpaque(false);
		rbCambiarCita.setOpaque(false);
		rbHacerPrueba.setOpaque(false);
		panelBotones.setOpaque(false);
		panelCitas5.setOpaque(false);
		panelCambio1.setOpaque(false);
		panelCambio2.setOpaque(false);
		panelCambio3.setOpaque(false);
		
		//FONDO
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/imagenes/consultaInicial.jpg")));
		lblNewLabel.setBounds(0, 0, getWidth(), getHeight());
		c.add(panelFecha);
		c.add(panelCitas);	
		c.add(panelCitas1);
		c.add(panelCitas2);
		c.add(panelCitas3);
		c.add(panelCitas5);
		c.add(panelCambio1);
		c.add(panelCambio2);
		c.add(panelCambio3);
		c.add(panelBotones);
		c.add(lblNewLabel);		
		
		//EVENTOS
		btnDarCita.addActionListener(this);
		btnVerPruebas.addActionListener(this);			
		btnConsultarHistorial.addActionListener(this);
		btnAñadir.addActionListener(this);
		btnAceptar.addActionListener(this);		
		btnSalir.addActionListener(this);	
		rbCambiarCita.addActionListener(this);
		rbHacerPrueba.addActionListener(this);
		btnGuardar.addActionListener(this);
		bCancelar.addActionListener(this);
		refresco.addActionListener(this);
	}
	
	/**
	 * Mete en la JList los elementos que recoge de la base de datos y los muestra
	 * Comprueba que solo se introduzcan las citas que se correspondan con la fecha actual
	 */
	public void llenarPanelCitas() 
	{
		ll = new LinkedList<Object>();
		modeloCitas = new DefaultListModel<Object>();	
		
		//conexion a base de datos
		try {
			GestorHace gestor = GestorHace.getInstance();
			GestorPaciente gestor2 = GestorPaciente.getInstance();
			gestor.connect();
			gestor2.connect();
			ll = gestor.listarTodos();
					
			//incluir elementos en la lista
			String mensaje = "";
			String estadoCita = "" ;	
			try{
				//comprobamos fecha de hoy con las citas de los pacientes de la lista
				lCitas = comprobarFechas(ll);			
				if (lCitas==null)
				{
					mensaje = " NO HAY CITAS PENDIENTES HOY";					
					modeloCitas.addElement(" ");
					modeloCitas.addElement(mensaje);
					modeloCitas.addElement(" ");
				}
				else
				{				
					mensaje = " CITAS PENDIENTES PARA HOY";
					modeloCitas.addElement(" ");
					modeloCitas.addElement(mensaje);
					modeloCitas.addElement(" ");
					//comprobar si la hora de la cita del paciente no es anterior a la actual y no ha realizado la prueba
									
					int i = 1; //contador pacientes
					
					for (Object ha : lCitas)
					{    
						if(ha instanceof Hace)
						{
							boolean b = comprobarHora(ha);
							if (b==true)
							{
								estadoCita = "[cita cancelada]";						
							}
							else 
								estadoCita = "";
							String nom = gestor2.buscarPaciente(((Hace) ha).getNss_paciente()).getNombre();
							String apell = gestor2.buscarPaciente(((Hace) ha).getNss_paciente()).getApellido();
							String nombreCompleto = nom+ " " +apell;
													
							modeloCitas.addElement(" Paciente " +i+ ": " +nombreCompleto+ " - tipo examen " +((Hace) ha).getCodigo_examen()+ " - " +((Hace) ha).getHoraCita()+ " " +estadoCita);
							i++;
						}
					}
				}				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			gestor.disconnect();
			gestor2.disconnect();
		} catch (ClassNotFoundException e) {} catch (SQLException e) {}
		
		lListaCitas.setModel(modeloCitas);	
	}	
	
	/**
	 * Añade a una nueva lista solo las citas que se correspondan con la fecha actual
	 * @param l
	 * @return LinkedList<Hace>
	 */
	public LinkedList<Hace> comprobarFechas(LinkedList<Object> l) 
	{
		LinkedList<Hace> h = new LinkedList<Hace>();
		String fechaActual = fecha.substring(0, 8);				
		for (Object o : l)
		{    
			if(o instanceof Hace)
			{
				String fechaCita = ((Hace) o).getFecha();				
				String diaA = null;				
				String mesA = null;					
				String añoA = null;	
				String diaC = null;				
				String mesC = null;					
				String añoC = null;				
				//comprobamos que la fecha mide lo mismo (por si se han introducido 0s al principio)
				if( ( fechaActual.charAt(7)!=' ' ) && ( fechaActual.length()==(fechaCita.length()) ) )
				{
					//separar en dia, mes, año - A:actual, C:cita
					diaA = fechaActual.substring(0, 2);						
					mesA = fechaActual.substring(3, 5);						
					añoA = fechaActual.substring(6, 8);					
					diaC = fechaCita.substring(0, 2);						
					mesC = fechaCita.substring(3, 5);						
					añoC = fechaCita.substring(6, 8);						
				}
				else
				{
					//si no son iguales de tamaño, añadir un 0 delante de la actual
					String fechaA = "0" +fechaActual;						
					//separar en dia, mes, año - A:actual, C:cita
					diaA = fechaA.substring(0, 2);						
					mesA = fechaA.substring(3, 5);						
					añoA = fechaA.substring(6, 8);							
					diaC = fechaCita.substring(0, 2);		
					mesC = fechaCita.substring(3, 5);					
					añoC = fechaCita.substring(6, 8);					
				}
				//buscar que sean iguales las fechas, si son iguales: añadir a la lista nueva
				 if(diaA.equals(diaC) && mesA.equals(mesC) && añoA.equals(añoC))			
					h.add((Hace) o);			 				
			}
		}
		if(h.size()<=0)
			return null;
		return h;
	}		
	
	/**
	 * Método que comprueba si la hora actual es inferior a la de cada cita 
	 * @param l
	 * @return boolean - true: hora actual inferior, false: hora actual superior
	 */
	public boolean comprobarHora(Object o)
	{
		boolean fcancelada = false;
		if(fecha.length()<14)
		{
			if(fecha.charAt(7)==' ') //si la fecha no tiene 0 delante del dia
			{
				fecha = "0" +fecha;					
			}
		}	
		String horaActual = fecha.substring(8, 12); //solo cojo hasta los dos puntos
		String horaA = "";
		String minutoA = "";	
		if(horaActual.charAt(2)==':')
		{	
			horaActual = fecha.substring(9, 13);
			String hA = " 0" +horaActual;		
			horaA = hA.substring(1, 3);				
			minutoA = hA.substring(4, 6);	
		}		
		else
		{		
			horaA = fecha.substring(9, 11);
			minutoA = fecha.substring(12, 14);		
		}		
		   
		if(o instanceof Hace)
		{
			String horaCita = ((Hace) o).getHoraCita();				
			String horaC = "";
			String minutoC = "";
			//comprobamos que las horas esten puestas en el mismo formato hh:mm
			if(horaCita.charAt(1)==':')
			{
				String hC = " 0" +horaCita;					
				horaC = hC.substring(1, 3);					
				minutoC = hC.substring(4, 6);				
			}
			else
			{	
				String hC = " " +horaCita;				
				horaC = hC.substring(1, 3);					
				minutoC = hC.substring(4, 6);			
			}						
			
			if(horaA.compareTo(horaC)>0) //si es mayor la hora actual a la de la cita
			{	
				fcancelada = true;
			}
			else
			{					
				int minuto1 = Integer.parseInt(minutoA); //minuto actual					
				int minuto2 = Integer.parseInt(minutoC); //minuto de la cita					
				if( ( (horaA.equals(horaC)) || (horaA.compareTo(horaC)>0) ) && (minuto1>minuto2) ) 
				{
					fcancelada = true;		
				}
			}				
		}			
		return fcancelada;		
	}	
	
	/**
	 * Visualiza el panel de cambiar cita para que el paciente puede escribir sus datos
	 * @param h
	 */
	public void cambiarCita(Hace h) 
	{
		lbCambio.setVisible(true);
		lbFechaa.setVisible(true);
		tFFecha.setVisible(true);
		cBHora.setVisible(true);
		btnGuardar.setVisible(true);
		bCancelar.setVisible(true);
	}
	
	/**
	 * Método que abre la ventana de la prueba correspondiente al código del examen del paciente
	 * @param h
	 */
	public void hacerPrueba(Hace h) 
	{
		int intentos = h.getIntentos();
		boolean cancel = comprobarHora(h);
		if(cancel==false) //si no se ha cancelado la cita
		{
			if(intentos == 0) //si ha realizado al menos una vez la prueba		
			{
				if(h.getCodigo_examen()==1)
				{
					VPruebaVista v = new VPruebaVista(h);
					v.setVisible(true);
				}
				else if(h.getCodigo_examen()==2)
				{
					VPruebaMemoria v = new VPruebaMemoria(h);
					v.setVisible(true);			
				}
				else if (h.getCodigo_examen()==3)
				{
					VMenuIngenio v = new VMenuIngenio(h);
					v.setVisible(true);		
				}		
				refresco.stop();
				this.dispose();								
				this.setVisible(false);		
			}
			else //si quiere volver a realizar la prueba
			{
				int res = JOptionPane.showConfirmDialog(null, "¿Desea volver a realizar la prueba?", "Hacer prueba", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(res==JOptionPane.YES_OPTION) 
				{
					if(h.getCodigo_examen()==1)
					{
						VPruebaVista v = new VPruebaVista(h);
						v.setVisible(true);
					}
					else if(h.getCodigo_examen()==2)
					{
						VPruebaMemoria v = new VPruebaMemoria(h);
						v.setVisible(true);			
					}
					else if (h.getCodigo_examen()==3)
					{
						VMenuIngenio v = new VMenuIngenio(h);
						v.setVisible(true);		
					}		
					refresco.stop();
					this.dispose();	
					this.setVisible(false);		
				}					
			}
		}
		else
			JOptionPane.showMessageDialog(this, "No puede realizar la prueba. Cita cancelada");
			
	}
		
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(refresco)) //para que refresque la ventana cada 7 segundos
		{
			llenarPanelCitas(); 
			//actualizar hora
			long temp = System.currentTimeMillis();
			Date d= new Date(temp);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			DateFormat df = DateFormat.getInstance();				
			fecha = df.format(d);
			lbFecha.setText(fecha);
			this.repaint();
		}
		
		if(arg0.getSource().equals(btnDarCita))
		{	
			VLoginPass vlp= new VLoginPass();
			vlp.setVisible(true);				
		}
		
		if(arg0.getSource().equals(btnVerPruebas))
		{	
			VMenuPruebas vmp = new VMenuPruebas();
			vmp.setVisible(true);
		}
		
		if(arg0.getSource().equals(btnAñadir))
		{	
			VLoginPass vlp = new VLoginPass();
			vlp.setVisible(true);
		}
		
		if(arg0.getSource().equals(btnConsultarHistorial))
		{
			VHistorial vh = new VHistorial();
			vh.setVisible(true);
		}		
		
		if(arg0.getSource().equals(btnAceptar))
		{				
			//conexion a base de datos
			try {
				GestorHace gestor = GestorHace.getInstance();	
				GestorPaciente gestor2 = GestorPaciente.getInstance();
				gestor.connect();	
				gestor2.connect();
			
				//miro qué paciente de la lista está seleccionado
				int pacienteSeleccionado = lListaCitas.getSelectedIndex();			
				lListaCitas.setSelectedIndex(pacienteSeleccionado);	
				Hace h = null;
				if (pacienteSeleccionado>=3)
				{
					h = (Hace) lCitas.get(pacienteSeleccionado-3);			
				
					//selecciono una opcion de aceptar
					if(rbCambiarCita.isSelected() && !rbHacerPrueba.isSelected())
					{
						if (pacienteSeleccionado>=3)
							cambiarCita(h); 
						else 
							JOptionPane.showMessageDialog(this, "No se pueden cambiar la cita.");				
					}
					else if(!rbCambiarCita.isSelected() && rbHacerPrueba.isSelected())
					{					
						if(pacienteSeleccionado>=3)
							hacerPrueba(h);								
						else
							JOptionPane.showMessageDialog(this, "No se puede realizar la prueba.");				
					}
					else
						JOptionPane.showMessageDialog(this, "Debe seleccionar algúna opción de la derecha.");				
				}
				else
					JOptionPane.showMessageDialog(this, "No ha seleccionado ningún paciente de la lista.");				
				
				gestor.disconnect();
				gestor2.disconnect();
			} catch (ClassNotFoundException e) {} catch (SQLException e) {}						
		}	
		
		if(arg0.getSource().equals(btnGuardar))
		{	
			System.out.println(tFFecha.getText().length());
			try {
				GestorHace gestor = GestorHace.getInstance();	
				gestor.connect();
				
				//miro que paciente esta seleccionado			
				int pacienteSeleccionado = lListaCitas.getSelectedIndex();			
				lListaCitas.setSelectedIndex(pacienteSeleccionado);	
				Hace h = null;
				if (pacienteSeleccionado>=3)
				{
					h = (Hace) lCitas.get(pacienteSeleccionado-3);					
					Hace ha = new Hace(h.getNss_paciente(), h.getCodigo_examen(), tFFecha.getText(), (String) cBHora.getSelectedItem(), h.getIntentos(), h.getFallos(), h.getAciertos()); 
					if(tFFecha.getText().equals(""))
						JOptionPane.showMessageDialog(this, "Debes introducir una fecha.");			
					else if(tFFecha.getText().length()!=8)
						JOptionPane.showMessageDialog(this, "El formato de la fecha no es el correcto.");			
					else
					{
						gestor.modificarTodo(ha);
						JOptionPane.showMessageDialog(this, "Cita modificada.");
						lbCambio.setVisible(false);
						lbFechaa.setVisible(false);
						tFFecha.setVisible(false);
						cBHora.setVisible(false);
						btnGuardar.setVisible(false);
						bCancelar.setVisible(false);
					}
				}
				else
					JOptionPane.showMessageDialog(this, "No ha seleccionado ningún paciente de la lista.");			
				
				gestor.disconnect();				
			} catch (ClassNotFoundException e) {} catch (SQLException e) {}						
						
		}		
		
		if(arg0.getSource().equals(bCancelar))
		{	
			lbCambio.setVisible(false);
			lbFechaa.setVisible(false);
			tFFecha.setVisible(false);
			cBHora.setVisible(false);
			btnGuardar.setVisible(false);
			bCancelar.setVisible(false);
		}
		
		if(arg0.getSource().equals(btnSalir))		
			System.exit(0);				
	}
	
	public static void main(String[] args)
	{
		VConsultaMedica v = new VConsultaMedica();
		v.setVisible(true);
	}
}


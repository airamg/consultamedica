package ventanas;

import gestoresBD.GestorAdministrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import clasesBasicas.Administrador;

public class VLoginPass extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JTextField usuario = new JTextField(17);
	private JPasswordField contraseña = new JPasswordField(15);	
	private JLabel user = new JLabel("Usuario: ");
	private JLabel pass = new JLabel("Contraseña: ");
	private JRadioButton rbTipoCita1 = new JRadioButton("Dar cita", false);
	private JRadioButton rbTipoCita2 = new JRadioButton("Añadir paciente", false);
    private ButtonGroup bg; 
	private JButton boton = new JButton("Entrar"); 
	private ImageIcon icon;
	private static String rutaImagenes = "/imagenes/";
    
	public VLoginPass()
    {
    	super();
    	setTitle("Acceso restringido - Identificate");
		setSize(320, 160);	
		setResizable(false); 
		this.setLocationRelativeTo(null); 
		icon = new ImageIcon(getClass().getResource((rutaImagenes+"logo.jpg")));
		this.setIconImage(icon.getImage());
		
    	Container panelContenidos = getContentPane();
		panelContenidos.setLayout(new BorderLayout());	
    	panelContenidos.setBackground(new Color(211,211,211)); //gris clarito
    	
    	JPanel tiposCitas = new JPanel();
    	tiposCitas.setLayout(new FlowLayout()); 
    	tiposCitas.setBackground(new Color(211,211,211)); //gris clarito
    	tiposCitas.add(rbTipoCita1);
    	tiposCitas.add(rbTipoCita2);
    	rbTipoCita1.setOpaque(false);
    	rbTipoCita2.setOpaque(false);   
    	bg = new ButtonGroup(); 
		bg.add(rbTipoCita1);
		bg.add(rbTipoCita2);	
    	
    	JPanel plogin = new JPanel();
    	plogin.setLayout(new FlowLayout(FlowLayout.CENTER));
    	plogin.setBackground(new Color(211,211,211)); //gris clarito
    	plogin.add(user);
    	plogin.add(usuario);
    	plogin.add(pass);
    	plogin.add(contraseña);
    	    
    	JPanel pboton = new JPanel();
    	pboton.setLayout(new FlowLayout(FlowLayout.CENTER));
    	pboton.setBackground(new Color(211,211,211)); //gris clarito
    	pboton.add(boton);

		panelContenidos.add(tiposCitas, "North");
    	panelContenidos.add(plogin, "Center"); 
		panelContenidos.add(pboton, "South");    
		
		rbTipoCita1.addActionListener(this);
		rbTipoCita2.addActionListener(this);
		boton.addActionListener(this);
    	
    	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }	
	
    /**
	 * Dirección de las acciones
	 * Si es el botón de 'entrar':
	 * -si coincide el usuario y contraseña con la del administrador, muestra la ventana del menu de administrador
	 * -si coincide el usuario y contraseña con la del socio, muestra la ventana del menu de socio
	 * -en caso de que nohaga nada de eso, muestra un mensaje de error en pantalla
	 */    
    public void actionPerformed(ActionEvent ae) 
    {
    	if(ae.getSource().equals(boton))
    	{
    		try {
    			GestorAdministrador gestor = GestorAdministrador.getInstance();    			
    			gestor.connect();      			
    			
    			String u = usuario.getText(); //nombre de usuario introducida
    			//comprobamos que existe el usuario
    			LinkedList<Object> lista = gestor.listarTodos();
    			
    			boolean enc = false;
    			int i = 0;
    			while (i<lista.size() && enc==false)
    			{
    				Administrador a = (Administrador) lista.get(i);    			
    				String no = a.getNombre();    			
    				if (a.getNombre().equals(u))
    					enc = true;
    				else
    					i++;
    			}
    			if(enc==true) //si existe...
    			{
    				String c = contraseña.getText(); 
    				//sacamos la contraseña que le corresponderia al usuario
    				String s = gestor.buscarUsuarioPass(u);
    				//comprobamos que se corresponden
    				if (c.equals(s))
        			{
    					//miramos que está seleccionado
    					if(rbTipoCita1.isSelected() && !rbTipoCita2.isSelected())
    		    		{	
    						dispose();    			
            				VDarCita vdc = new VDarCita();	
            				vdc.setVisible(true);    	
    		    		}
    					else if(!rbTipoCita1.isSelected() && rbTipoCita2.isSelected())
    					{
    						dispose();    			
            				VAñadirPaciente vap = new VAñadirPaciente();	
            				vap.setVisible(true); 
    					}
    					else     						
    						JOptionPane.showMessageDialog(this, "Debe seleccionar algúna opción");				
    				}        			
        			else
        			{
        				contraseña.setText("");
        				JOptionPane.showMessageDialog(this, "La combinación de usuario/contraseña es incorrecta");		
        			}     				
    			}
    			else
    			{
    				usuario.setText("");
    				contraseña.setText("");
    				JOptionPane.showMessageDialog(this, "No existe ese usuario, vuelve a intentarlo.");		
    			}
    			
    			gestor.disconnect();    			
    		} catch (ClassNotFoundException e) {} catch (SQLException e) {}    		
    	}    
    }	

	public static void main(String[] args)
	{
		VLoginPass v = new VLoginPass();
		v.setVisible(true);
	}

}

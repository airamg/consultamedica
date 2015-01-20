package clasesBasicas;

/**
 * Clase administrador que hereda de Usuario
 * @author equipo-05
 */
public class Administrador extends Usuario
{
	private String contraseña;
	
	public Administrador() {}	
	
	/**
	 * Contructor de administrador
	 * @param nombre
	 * @param contraseña
	 */
	public Administrador(String nombre, String contraseña)
	{
		super(nombre);
		this.contraseña = contraseña;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	public String toString()
	{
		return super.toString()+ " - Contraseña: " +this.contraseña;
	}	
	
}

package clasesBasicas;

/**
 * Clase administrador que hereda de Usuario
 * @author equipo-05
 */
public class Administrador extends Usuario
{
	private String contrase�a;
	
	public Administrador() {}	
	
	/**
	 * Contructor de administrador
	 * @param nombre
	 * @param contrase�a
	 */
	public Administrador(String nombre, String contrase�a)
	{
		super(nombre);
		this.contrase�a = contrase�a;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	
	public String toString()
	{
		return super.toString()+ " - Contrase�a: " +this.contrase�a;
	}	
	
}

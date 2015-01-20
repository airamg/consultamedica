package clasesBasicas;

/**
 * Clase usuario de la que heredan Administrador y Paciente
 * @author equipo-05
 */
public class Usuario {

	private String nombre;

	public Usuario() {		
	}

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString()
	{
		return "Nombre de usuario: " +this.nombre;
	}
	
}

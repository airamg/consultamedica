package clasesBasicas;

/**
 * Clase Examen 
 * @author equipo-05
 */
public class Examen 
{
	private int codigo; //1 vista, 2 memoria, 3 ingenio
	private String nombre;	
	private String descripcion;

	public Examen() {}
	
	/**
	 * Constructor principal de la clase Examen
	 * @param codigo
	 * @param nombre
	 * @param descripcion	
	 */
	public Examen(int codigo, String nombre, String descripcion) 
	{
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}		
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método toString que muestra por consola todos los atributos de esta clase
	 * Dependiendo del código del examen que sea, muestra un nombre u otro
	 * @return String
	 */
	public String toString()
	{
		if (this.codigo==1)		
			this.nombre = "vista";
		else if (this.codigo==2)
			this.nombre = "memoria";
		else if (this.codigo==3)
			this.nombre = "ingenio";		
		return this.codigo+ " - " +this.nombre+ "\n Descripción: " +this.descripcion;
	}	
	
}

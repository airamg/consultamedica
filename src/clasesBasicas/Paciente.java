package clasesBasicas;

import java.sql.Blob;

/**
 * Clase Paciente que hereda de usuario
 * @author equipo-05
 */
public class Paciente extends Usuario
{
	private long nss;
	private String apellido;	
	private Blob foto;
	private String historialMedico;	//enfermedades, intervenciones quirurgicas, alergias, vacunas, antecedentes familiares
	private int añoNacimiento;
	private String trabajo;
	private String medicacion;
	private int resultadoUltimaPrueba;
		
	/**
	 * Constructor por defecto	
	 */
	public Paciente() 
	{
		nss = 00000000; //8 digitos
	}	

	/**
	 * Constructor principal de la clase Paciente
	 * @param nss
	 * @param nombre
	 * @param apellido
	 * @param foto
	 * @param historialMedico
	 * @param añoNacimiento
	 * @param trabajo
	 * @param medicacion
	 * @param resultadoUltimaPrueba
	 */
	public Paciente(String nombre, long nss, String apellido, Blob foto, String historialMedico, int añoNacimiento, String trabajo, String medicacion, int resultadoUltimaPrueba)
	{
		super(nombre);
		this.nss = nss;
		this.apellido = apellido;	
		this.foto = foto;		
		this.historialMedico = historialMedico;
		this.añoNacimiento = añoNacimiento;
		this.trabajo = trabajo;
		this.medicacion = medicacion;
		this.resultadoUltimaPrueba = resultadoUltimaPrueba;		
	}
	
	/**
	 * Nuevo constructor de prueba para el gestor de paciente
	 * Sin foto, solo datos primitivos
	 * @param nss
	 * @param nombre
	 * @param apellido
	 * @param historialMedico
	 * @param añoNacimiento
	 * @param trabajo
	 * @param medicacion
	 * @param resultadoUltimaPrueba
	 */
	public Paciente(String nombre, long nss, String apellido, String historialMedico, int añoNacimiento, String trabajo, String medicacion, int resultadoUltimaPrueba)
	{
		super(nombre);
		this.nss = nss;		
		this.apellido = apellido;					
		this.historialMedico = historialMedico;
		this.añoNacimiento = añoNacimiento;
		this.trabajo = trabajo;
		this.medicacion = medicacion;
		this.resultadoUltimaPrueba = resultadoUltimaPrueba;		
	}	
	
	public long getNss() {
		return nss;
	}
	
	public void setNss(long nss) {
		this.nss = nss;
	}
		
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	public String getHistorialMedico() {
		return historialMedico;
	}

	public void setHistorialMedico(String historialMedico) {
		this.historialMedico = historialMedico;
	}

	public int getAñoNacimiento() {
		return añoNacimiento;
	}

	public void setAñoNacimiento(int añoNacimiento) {
		this.añoNacimiento = añoNacimiento;
	}

	public String getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public String getMedicacion() {
		return medicacion;
	}

	public void setMedicacion(String medicacion) {
		this.medicacion = medicacion;
	}

	public int getResultadoUltimaPrueba() {
		return resultadoUltimaPrueba;
	}

	public void setResultadoUltimaPrueba(int resultadoUltimaPrueba) {
		this.resultadoUltimaPrueba = resultadoUltimaPrueba;
	}

	/**
	 * Método toString que devuelve una cadena de caracteres con los datos del Paciente
	 * Se visualizan en consola
	 * @return String
	 */	
	public String toString()
	{
		return this.nss+ " - " +super.toString()+ " " +this.apellido+ "\n Año nacimiento: " +this.añoNacimiento+ " - Trabajo: " +this.trabajo+ "\n Resultado: " +this.resultadoUltimaPrueba;
	}
	
}

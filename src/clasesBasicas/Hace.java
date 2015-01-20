package clasesBasicas;

/**
 * Clase Hace que se encarga de las citas de los pacientes
 * @author equipo-05
 */
public class Hace 
{
	private long nss_paciente;
	private int codigo_examen;
	private String fecha;
	private String horaCita;
	private int intentos;
	private int fallos;
	private int aciertos;
	
	public Hace() {}
	
	/**
	 * Constructor principal con parametros de la clase Hace
	 * @param nss_paciente
	 * @param codigo_examen
	 * @param fecha
	 * @param horaCita
	 * @param intentos
	 * @param fallos
	 * @param aciertos
	 */
	public Hace(long nss_paciente, int codigo_examen, String fecha, String horaCita, int intentos, int fallos, int aciertos) 
	{
		this.nss_paciente = nss_paciente;
		this.codigo_examen = codigo_examen;
		this.fecha = fecha;
		this.horaCita = horaCita;
		this.intentos = intentos;
		this.fallos = fallos;
		this.aciertos = aciertos;
	}

	public long getNss_paciente() {
		return nss_paciente;
	}

	public void setNss_paciente(long nss_paciente) {
		this.nss_paciente = nss_paciente;
	}

	public int getCodigo_examen() {
		return codigo_examen;
	}

	public void setCodigo_examen(int codigo_examen) {
		this.codigo_examen = codigo_examen;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	public int getFallos() {
		return fallos;
	}

	public void setFallos(int fallos) {
		this.fallos = fallos;
	}

	public int getAciertos() {
		return aciertos;
	}

	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}
	
	/**
	 * Método toStrign que muestra los resultados de la prueba del examen realizado por el paciente
	 * @return String
	 */
	public String toString()
	{
		String tipo = "";
		if (this.codigo_examen==01)		
			tipo = "vista";
		else if (this.codigo_examen==02)
			tipo = "memoria";
		else if (this.codigo_examen==03)
			tipo = "ingenio";		
		return "Paciente: " +this.nss_paciente+ " hace un examen de " +tipo+ " el " +fecha+ " a las " +horaCita+ "\n Ha obtenido: " +this.fallos+ " fallos y " +this.aciertos+ " aciertos en " +this.intentos+ " intentos.";
	}

}

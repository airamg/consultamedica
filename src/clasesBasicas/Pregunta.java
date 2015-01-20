package clasesBasicas;

/**
 * Clase Pregunta
 * @author equipo-05
 */
public class Pregunta 
{
	private final int id; 
	private int codigo_examen;
	private String pregunta;
	private String respuestaCorrecta;
	private String respuestaIncorrecta1;
	private String respuestaIncorrecta2;
	private String tema; //5 temas: nutricion, naturaleza, deportes, ciencias, geografia
	
	public Pregunta()
	{
		id = 1;		
	}	
	
	/**
	 * Constructor principal con parámetros de la clase Pregunta
	 * @param id
	 * @param codigo_examen
	 * @param pregunta
	 * @param respuestaCorrecta
	 * @param respuestaIncorrecta1
	 * @param respuestaIncorrecta2	
	 * @param tema
	 */
	public Pregunta(int id, int codigo_examen, String pregunta, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String tema)
	{
		this.id = id;
		this.codigo_examen = codigo_examen;
		this.pregunta = pregunta;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestaIncorrecta1 = respuestaIncorrecta1;
		this.respuestaIncorrecta2 = respuestaIncorrecta2;		
		this.tema = tema;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPregunta() {
		return pregunta;
	}	

	public int getCodigo_examen() {
		return codigo_examen;
	}

	public void setCodigo_examen(int codigo_examen) {
		this.codigo_examen = codigo_examen;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getRespuestaIncorrecta1() {
		return respuestaIncorrecta1;
	}

	public void setRespuestaIncorrecta1(String respuestaIncorrecta1) {
		this.respuestaIncorrecta1 = respuestaIncorrecta1;
	}

	public String getRespuestaIncorrecta2() {
		return respuestaIncorrecta2;
	}

	public void setRespuestaIncorrecta2(String respuestaIncorrecta2) {
		this.respuestaIncorrecta2 = respuestaIncorrecta2;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	/**
	 * Método toString que muestra por consola todos los atributos de la clase
	 * @return String
	 */
	public String toString()
	{
		return this.id+ " - " +this.tema+ " - " +this.pregunta+ "\n Correcta: " +this.respuestaCorrecta+ "\n Incorrecta 1: " +this.respuestaIncorrecta1+ "\n Incorrecta 2: " +this.respuestaIncorrecta2;
	}	

}

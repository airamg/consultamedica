package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tablero extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private ImageIcon equis;
	private ImageIcon tick;	
    private boolean tipoTablero;
    private Casilla[][] casillas;  	
	private static String rutaImagenes = "/imagenes/";
	private int contador;
        
    public Tablero(int size, boolean tipo, int contador) 
    {
    	super();    	
    	int x, y; 
    	this.contador = contador;
        this.setBackground(new Color(0, 0, 0));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.setPreferredSize(new Dimension(351, 351));
    	this.setLayout(new GridLayout(size, size));
    	    	
    	//inicializo elementos
    	tipoTablero = tipo;
        equis = new ImageIcon(getClass().getResource((rutaImagenes+"equis.jpg")));
        tick = new ImageIcon(getClass().getResource((rutaImagenes+"tick.jpg")));
        casillas = new Casilla[size][size];
        
        //pongo las fotos en las casillas del tablero
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                casillas[i][j] = new Casilla(this, contador); 
                casillas[i][j].setFondo(equis);            
                x = (i * 35)+1;
                y = (j * 35)+1;
                casillas[i][j].setBounds(x, y, 34, 34);
                this.add(casillas[i][j]);
            }
        }
    }

    public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public boolean getTipoTablero(){
        return this.isTipoTablero();
    }
    
    public void pintar(int x, int y){
        this.casillas[x][y].setFondo(tick);
        this.repaint();
    } 
    
    public int[] getCoordenadas(Casilla casilla) 
    {
        int [] coordenadas = new int[2];
        for (int i=0; i < this.casillas.length; i++) 
        {
            for (int j=0; j < this.casillas.length; j++) 
            {
                if (this.casillas[i][j] == casilla) 
                {
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                }
            }
        }        
        return coordenadas;
    }
    
    public Casilla[][] getCasillas() {
        return casillas;
    }
    
    public void setCasillas(Casilla[][] casillas) {
        this.casillas = casillas;
    }
    
    public boolean isTipoTablero() {
        return tipoTablero;
    }  
    
    public void setTipoTablero(boolean tipoTablero) {
        this.tipoTablero = tipoTablero;
    }                       
                     
}

package utils;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Casilla extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private Tablero tablero;
	private ImageIcon fondo;
	private static int[] casillaMarcada = new int[2];
	private boolean haSidoMarcada;
	private static int contador = 0;

	public Casilla(Tablero t, int contador) {
		super();
		this.contador = contador;
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		tablero = t;
		haSidoMarcada = false;
		if (tablero.getTipoTablero() == true) {
			this.addMouseListener(this);
		}
	}

	public static int getContador() {
		return contador;
	}
	
	public boolean haSidoMarcada(){
		return haSidoMarcada;
	}
	
	public void setHaSidoMarcada(boolean opcion){		
		haSidoMarcada = opcion;
	}

	public static void setContador(int contador) {
		Casilla.contador = contador;
	}

	public void setFondo(ImageIcon fondo) {
		this.fondo = fondo;
	}

	public ImageIcon getFondo() {
		return this.fondo;
	}

	public static int[] getCasillaMarcada() {
		return casillaMarcada;
	}

	public static void setCasillaMarcada(int[] aCasillaMarcada) {
		casillaMarcada = aCasillaMarcada;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fondo.getImage(), 0, 0, this.getWidth(), this.getHeight(),this);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		Casilla.setCasillaMarcada(tablero.getCoordenadas((Casilla) e.getComponent()));
		tablero.pintar(Casilla.getCasillaMarcada()[0],Casilla.getCasillaMarcada()[1]);
		//si no ha sido marcada
		if(!((Casilla)e.getComponent()).haSidoMarcada())
		{
			contador++;
			((Casilla)e.getComponent()).setHaSidoMarcada(true);
		}
		tablero.setContador(contador);
	}

	public void mouseReleased(MouseEvent e) {
	}

}

package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private boolean rompible;
	private Entorno entorno;
	private int contador;

	private Image bloqueR;
	private Image bloqueR1;
	private Image bloqueR2;
	private Image bloqueR3;
	private Image bloqueNR;

	public Bloque(int x, int y, int ancho, int alto, boolean rompible, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.rompible = rompible;
		this.entorno = entorno;
		imagenes();
	}

	public void dibujarse() {
		if(rompible) {
			contador++;
			if(contador <= 80) {
				entorno.dibujarImagen(bloqueR, this.x + 16, this.y + 15, 0, 2);
				contador ++;
			}
			if(contador >= 80 && contador <= 100) {
				entorno.dibujarImagen(bloqueR1, this.x + 16, this.y + 15, 0, 2);
				contador ++;
			}
			if(contador >= 100 && contador <= 120) {
				entorno.dibujarImagen(bloqueR2, this.x + 16, this.y + 15, 0, 2);
				contador ++;
			}
			if(contador >= 120 && contador <= 140) {
				entorno.dibujarImagen(bloqueR3, this.x + 16, this.y + 15, 0, 2);
				contador ++;
			}
			if(contador >= 140)
				contador = 0;
		}else
			entorno.dibujarImagen(bloqueNR, this.x + 16, this.y + 15, 0, 2);
	}
	public void imagenes() {
		this.bloqueR = Herramientas.cargarImagen("bloqueRompible.png");
		this.bloqueR1 = Herramientas.cargarImagen("bloqueRompible1.png");
		this.bloqueR2 = Herramientas.cargarImagen("bloqueRompible2.png");
		this.bloqueR3 = Herramientas.cargarImagen("bloqueRompible3.png");
		this.bloqueNR = Herramientas.cargarImagen("bloqueNoRompible.png");
	}

	public boolean esRompible() {
		return rompible;
	}

	//GETTERS Y SETTERS

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}

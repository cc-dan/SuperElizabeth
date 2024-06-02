package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Gatito {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Entorno entorno;
	private int contador; // se va usar para animacion
	//imagenes:
	private Image gatito1;
	private Image gatito2;
	private Image gatito3;
	private Image gatito4;
	private Image gatito5;
	private Image gatito6;

	public Gatito(int x, int y, int ancho, int alto, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.entorno = entorno;
		imagenes();
	}

	public void dibujarse() {
		contador++;
		if(contador <= 200) {
			entorno.dibujarImagen(gatito1, this.x + 16, this.y + 15, 0, 0.2);
			contador ++;
		}
		if(contador >= 200 && contador <= 300) {
			entorno.dibujarImagen(gatito2, this.x + 16, this.y + 15, 0, 0.2);
			contador ++;
		}
		if(contador >= 300 && contador <= 400) {
			entorno.dibujarImagen(gatito3, this.x + 16, this.y + 15, 0, 0.2);
			contador ++;
		}
		if(contador >= 400 && contador <= 500) {
			entorno.dibujarImagen(gatito4, this.x + 16, this.y + 15, 0, 0.2);
			contador ++;
		}
		if(contador >= 500 && contador <= 600) {
			entorno.dibujarImagen(gatito2, this.x + 16, this.y + 15, 0, 0.2);
			contador ++;
		}
		if(contador >= 600)
			contador = 0;
		
		
		
	}
		/*if(rompible) {
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

		if(seRompio) {
			entorno.dibujarImagen(bloqueRoto1, this.xb + 16, this.yb + 15, 0, 2);
		}
		//metodo que guarde posiciones? */
	
	public void imagenes() {
		this.gatito1 = Herramientas.cargarImagen("gatito1.png");
		this.gatito2 = Herramientas.cargarImagen("gatito2.png");
		this.gatito3 = Herramientas.cargarImagen("gatito3.png");
		this.gatito4 = Herramientas.cargarImagen("gatito4.png");
		this.gatito5 = Herramientas.cargarImagen("gatito5.png");
		this.gatito6 = Herramientas.cargarImagen("gatito6.png");
		
	}
	
	//GETTERS Y SETTERS

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}

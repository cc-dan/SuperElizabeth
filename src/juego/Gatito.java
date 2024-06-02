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
	private int contador; 

	private Image gatito1;
	private Image gatito2;
	private Image gatito3;
	private Image gatito4;

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

	public void imagenes() {
		this.gatito1 = Herramientas.cargarImagen("gatito1.png");
		this.gatito2 = Herramientas.cargarImagen("gatito2.png");
		this.gatito3 = Herramientas.cargarImagen("gatito3.png");
		this.gatito4 = Herramientas.cargarImagen("gatito4.png");
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

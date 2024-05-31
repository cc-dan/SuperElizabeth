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
	private Image gatitoSprite;

	public Gatito(int x, int y, int ancho, int alto, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.entorno = entorno;
		//imagenes();
	}

	public void dibujarse() {
		
		
		
		
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
		//metodo que guarde posiciones?
	}
	public void imagenes() {
		this.bloqueR = Herramientas.cargarImagen("bloqueRompible.png");
		this.bloqueR1 = Herramientas.cargarImagen("bloqueRompible1.png");
		this.bloqueR2 = Herramientas.cargarImagen("bloqueRompible2.png");
		this.bloqueR3 = Herramientas.cargarImagen("bloqueRompible3.png");
		this.bloqueNR = Herramientas.cargarImagen("bloqueNoRompible.png");
		this.bloqueRoto1 = Herramientas.cargarImagen("bloqueRoto1.png");
		this.bloqueRoto2 = Herramientas.cargarImagen("bloqueRoto1.png");
		this.bloqueRoto3 = Herramientas.cargarImagen("bloqueRoto1.png");
	}*/
	
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

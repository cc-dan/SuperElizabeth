package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	private int x;
	private int y;
	private int velocidad;
	private int ancho; 
	private int alto;
	private boolean inofensivo;
	private boolean mirandoDerecha;
	private Personaje padre;

	private Entorno entorno;
	//imagenes
	private Image proyectilDer;
	private Image proyectilIzq;

	public Proyectil(int x, int y, boolean inofensivo, boolean derecha, Personaje padre, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.alto = 10;
		this.ancho = 20;
		this.inofensivo = inofensivo;
		this.velocidad = 7;
		this.mirandoDerecha = derecha;
		this.padre = padre;
		this.entorno = entorno;
		imagenes();
	}

	public void dibujar() {
		/*if(inofensivo)*/ {
			if(mirandoDerecha)
				entorno.dibujarImagen(this.proyectilDer, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 2);
			if(!mirandoDerecha)
				entorno.dibujarImagen(this.proyectilIzq, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 2);
		}
	}

	public void imagenes() {
		this.proyectilDer = Herramientas.cargarImagen("puerro.der.png");
		this.proyectilIzq = Herramientas.cargarImagen("puerro.izq.png");
	}
	public void mover() {
		if(mirandoDerecha) {
			this.x += velocidad;
		} else {
			this.x -= velocidad;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
		if(velocidad > 0) {
			this.mirandoDerecha = true;
		} else if(velocidad < 0) {
			this.mirandoDerecha = false;
		}
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public boolean isInofensivo() {
		return inofensivo;
	}

	public Personaje getPadre() {
		return padre;
	}

}

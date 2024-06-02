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
	private int contador;
	private Entorno entorno;
	//imagenes
	private Image proyectilDer;
	private Image proyectilIzq;
	private Image proyectilEnem1;
	private Image proyectilEnem2;
	private Image proyectilEnem3;
	private Image proyectilEnem4;

	public Proyectil(int x, int y, boolean inofensivo, boolean derecha, Personaje padre, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.alto = 10;
		this.ancho = 20;
		this.inofensivo = inofensivo;
		if(inofensivo == true) {
			this.velocidad = 7;
			} else {
				this.velocidad = 5;
			}
		this.mirandoDerecha = derecha;
		this.padre = padre;
		this.entorno = entorno;
		imagenes();
	}

	public void dibujar() {
		if(inofensivo){
			if(mirandoDerecha)
				entorno.dibujarImagen(this.proyectilDer, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 2);
			if(!mirandoDerecha)
				entorno.dibujarImagen(this.proyectilIzq, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 2);
		}
		
		if(!inofensivo) {
				contador ++;
				if(contador <= 20) {
				entorno.dibujarImagen(this.proyectilEnem1, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 0.2);
				contador++;
				}
				if(contador >= 20 && contador <= 40) {
					entorno.dibujarImagen(this.proyectilEnem2, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 0.2);
					contador ++;
				}
				if(contador >= 40 && contador <= 60) {
					entorno.dibujarImagen(this.proyectilEnem3, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 0.2);
					contador ++;
				}
				if(contador >= 60 && contador <= 80) {
					entorno.dibujarImagen(this.proyectilEnem4, this.x + this.ancho / 2, this.y + this.alto / 2, 0, 0.2);
					contador ++;
				}
				if(contador >= 80)
					contador = 0;
						
		}
	}

	public void imagenes() {
		this.proyectilDer = Herramientas.cargarImagen("puerro.der.png");
		this.proyectilIzq = Herramientas.cargarImagen("puerro.izq.png");
		this.proyectilEnem1 = Herramientas.cargarImagen("fuego1.png");
		this.proyectilEnem2 = Herramientas.cargarImagen("fuego2.png");
		this.proyectilEnem3 = Herramientas.cargarImagen("fuego3.png");
		this.proyectilEnem4 = Herramientas.cargarImagen("fuego4.png");
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

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
		if(velocidad > 0) {
			this.mirandoDerecha = true;
		} else if(velocidad < 0) {
			this.mirandoDerecha = false;
		}
	}

	public boolean mirandoALaDerecha()
	{
		return mirandoDerecha;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public boolean isInofensivo() {
		return inofensivo;
	}

	public void setInofensivo(boolean inofensivo) {
		this.inofensivo = inofensivo;
	}

	public Personaje getPadre() {
		return padre;
	}

}

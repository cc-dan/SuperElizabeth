package juego;

import java.awt.Image;
import java.math.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
	private int x;
	private int y;
	private int velocidadHorizontal;
	private float velocidadVertical;
	private int ancho;
	private int alto;
	//private boolean saltando = false;
	private boolean saltando;
	private boolean mirandoDerecha = true;
	private boolean enMovimiento = false;
	private boolean esJugador = false;
	private boolean puedeDisparar = true;
	private int velocidadSalto = 7;
	private Entorno entorno;
	private int contador;
	//imagenes:
	private Image mikuquietader;
	private Image mikuquietaizq;
	private Image mikusaltoder;
	private Image mikusaltoizq;
	private Image miku1der;
	private Image miku1izq;
	private Image miku2der;
	private Image miku2izq;
	private Image miku3der;
	private Image miku3izq;
	private Image enemigo;
	
	
	public Personaje(int x, int y, boolean jugable, Entorno entorno) 
	{
		this.x = x;
		this.y = y;
		this.velocidadHorizontal = 5;
		this.ancho = 32;
		this.alto = 64;
		this.esJugador = jugable;
		this.entorno = entorno;
		imagenes();
		this.contador = 0;	}
	
	public void dibujarse() {
		//System.out.println(contador);
		if(mirandoDerecha) {
			if(saltando) {
				entorno.dibujarImagen(this.mikusaltoder, this.x + 15, this.y+25, 0, 0.2);
			}
			else if(!enMovimiento) {
				entorno.dibujarImagen(this.mikuquietader, this.x + 15, this.y+25, 0, 0.2);
			}
			else{
				contador ++;
				if(contador <= 10) {
					entorno.dibujarImagen(this.miku1der, this.x + 15, this.y+25, 0, 0.2);
					contador ++;
				}
				if(contador >= 10 && contador <= 20) {
					entorno.dibujarImagen(this.miku2der, this.x + 15, this.y+25, 0, 0.2);
					contador ++;

				}
				if(contador >= 20 && contador <= 30) {
					entorno.dibujarImagen(this.miku3der, this.x + 15, this.y+25, 0, 0.2);
					contador ++;
				
				}
				if(contador >= 30)
					contador = 0;
			}		
		}
		
		if(!mirandoDerecha) {
			if(saltando) {
				entorno.dibujarImagen(this.mikusaltoizq, this.x + 15, this.y + 25, 0, 0.2);
			}
			else if(!enMovimiento) {
				entorno.dibujarImagen(this.mikuquietaizq, this.x + 15, this.y + 25, 0, 0.2);
			}
			else{
				contador ++;
				if(contador <= 10) {
					entorno.dibujarImagen(this.miku1izq, this.x + 15, this.y + 25, 0, 0.2);
					contador ++;
				}
				if(contador >= 10 && contador <= 20) {
					entorno.dibujarImagen(this.miku2izq, this.x + 15, this.y + 25, 0, 0.2);
					contador ++;

				}
				if(contador >= 20 && contador <= 30) {
					entorno.dibujarImagen(this.miku3izq, this.x + 15, this.y + 25, 0, 0.2);
					contador ++;
				
				}
				if(contador >= 30)
					contador = 0;
			
			}
		}
	}

	public void imagenes() {
		this.mikuquietader = Herramientas.cargarImagen("miku.quieta.der.png");
		this.mikuquietaizq = Herramientas.cargarImagen("miku.quieta.izq.png");
		this.mikusaltoder = Herramientas.cargarImagen("miku.salto.der.png");
		this.mikusaltoizq = Herramientas.cargarImagen("miku.salto.izq.png");
		this.miku1der = Herramientas.cargarImagen("miku.1.der.png");
		this.miku1izq = Herramientas.cargarImagen("miku.1.izq.png");
		this.miku2der = Herramientas.cargarImagen("miku.2.der.png");
		this.miku2izq = Herramientas.cargarImagen("miku.2.izq.png");
		this.miku3der = Herramientas.cargarImagen("miku.3.der.png");
		this.miku3izq= Herramientas.cargarImagen("miku.3.izq.png");
		
	}
	
	public void moverHorizontal() 
	{
		this.x += this.velocidadHorizontal;
			
	}
	
//	public void mostrarX() {
//		System.out.println(this.x);
//	}
//	
	public void moverVertical()
	{
		this.y += Math.ceil(this.velocidadVertical); // siempre que velocidadVertical sea mayor a 0 se debería ver modificada la posición vertical
	}
	
	public void saltar() {
		this.velocidadVertical = -velocidadSalto;
	}
	
	public boolean mirandoALaDerecha()
	{
		return this.mirandoDerecha;
	}
	
	public boolean estaSaltando()
	{
		return this.saltando;
	}
	
	public boolean esJugador()
	{
		return this.esJugador;
	}
	
	public Proyectil disparar() {
		this.puedeDisparar = false;
		return new Proyectil(this.x, this.y, this.esJugador, this.mirandoDerecha, this, this.entorno);
	}
	
	////// GETTERS & SETTERS:
	
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
	
	public boolean getPuedeDisparar() {
		return this.puedeDisparar;
	}
	
	public void setPuedeDisparar(boolean puedeDisparar) {
		this.puedeDisparar = puedeDisparar;
	}


	public int getVelocidadHorizontal() {
		return velocidadHorizontal;
	}

	public void setVelocidadHorizontal(int velocidad) {
		this.velocidadHorizontal = velocidad;
		if(velocidad > 0) {
			this.mirandoDerecha = true;
		} else if(velocidad < 0) {
			this.mirandoDerecha = false;
		}
	}
	
	public float getVelocidadVertical()
	{
		return this.velocidadVertical;
	}
	
	public void setVelocidadVertical(float velocidad)
	{
		this.velocidadVertical = velocidad;
	}
	
	public void setMirandoALaDerecha(boolean b)
	{
		this.mirandoDerecha = b;
	}
	
	public void setSaltando(boolean b)
	{
		this.saltando = b;
	}
	
	public void setEnMovimiento(boolean x) {
		this.enMovimiento = x;
	}
	
	// IMAGENES
	
	
}

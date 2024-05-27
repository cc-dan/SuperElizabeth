package juego;

import java.math.*;

public class Personaje {
	private int x;
	private int y;
	private int velocidadHorizontal;
	private float velocidadVertical;
	private int ancho;
	private int alto;
	private boolean saltando = false;
	private boolean mirandoDerecha = true;

	private boolean esJugador = false;
	private boolean puedeDisparar = true;
<<<<<<< HEAD

=======
	private int velocidadSalto = 7;
	
>>>>>>> b165a0921161abb3220419c564d42204d934fa3b
	public Personaje(int x, int y, boolean jugable) 
	{
		this.x = x;
		this.y = y;
		this.velocidadHorizontal = 5;
		this.ancho = 32;
		this.alto = 64;
		this.esJugador = jugable;
	}

	public void moverHorizontal() 
	{
		this.x += this.velocidadHorizontal;
	}

	public void moverVertical()
	{
		this.y += Math.ceil(this.velocidadVertical); // siempre que velocidadVertical sea mayor a 0 se debería ver modificada la posición vertical
	}

	public void saltar() {
		this.velocidadVertical = -velocidadSalto;
	}

	public boolean getPuedeDisparar() {
		return this.puedeDisparar;
	}

	public void setPuedeDisparar(boolean puedeDisparar) {
		this.puedeDisparar = puedeDisparar;
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

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}

	public boolean mirandoALaDerecha()
	{
		return this.mirandoDerecha;
	}
<<<<<<< HEAD

	public boolean saltando()
	{
		return this.velocidadVertical < 0;
	}


=======
	
	public void setMirandoALaDerecha(boolean b)
	{
		this.mirandoDerecha = b;
	}
	
>>>>>>> b165a0921161abb3220419c564d42204d934fa3b
	public boolean estaSaltando()
	{
		return this.saltando;
	}

	public void setSaltando(boolean b)
	{
		this.saltando = b;
	}

	public boolean esJugador()
	{
		return this.esJugador;
	}
	public Proyectil disparar() {
		this.puedeDisparar = false;
		return new Proyectil(this.x, this.y, this.esJugador, this.mirandoDerecha, this);
	}

	public void cambiarSentido()
	{
		this.velocidadHorizontal = -1 * this.velocidadHorizontal;
	}
}

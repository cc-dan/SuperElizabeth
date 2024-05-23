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
<<<<<<< HEAD:src/juego/Jugador.java

	public Jugador(int x, int y) {

=======
	private boolean esJugador = false;
	
	public Personaje(int x, int y, boolean jugable) 
	{
>>>>>>> 1b14dbda453f3fb68ad074f8539cf53d6d7e515e:src/juego/Personaje.java
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
		this.y += Math.ceil(this.velocidadVertical); // evitamos que el jugador no se mueva cuando 0 < velocidad vertical < 1 
	}

	public void saltar() {
		this.velocidadVertical = -10;
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
<<<<<<< HEAD:src/juego/Jugador.java

	public void setAlto(int alto) {
		this.alto = alto;
	}

=======
	
>>>>>>> 1b14dbda453f3fb68ad074f8539cf53d6d7e515e:src/juego/Personaje.java
	public boolean mirandoALaDerecha()
	{
		return mirandoDerecha;
	}
<<<<<<< HEAD:src/juego/Jugador.java

	public Proyectil disparar() {

		int direccionDerecha = 0;

		Proyectil proyectil = new Proyectil(this.getX(), this.getY(), 1, 20, 10, true);

		if(direccionDerecha > 0) {
			this.mirandoDerecha = true;
		} else if(direccionDerecha < 0) {
			this.mirandoDerecha = false;
		}
		
		return proyectil;

	}

	public boolean saltando()
	{
		return this.velocidadVertical < 0;
	}

=======
	
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
	
	//public Proyectil disparar() {
		
	//}
>>>>>>> 1b14dbda453f3fb68ad074f8539cf53d6d7e515e:src/juego/Personaje.java
}

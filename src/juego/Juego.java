package juego;

import java.awt.Color;
import java.util.random.*;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	Jugador jugador;
	Proyectil proyectil[];
	Enemigo enemigos[];
	Bloque bloques[];
	int velocidadJugador = 5;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo ... - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.jugador = new Jugador(400, 550);
		this.enemigos = new Enemigo[10];
		this.bloques = new Bloque[] {
		new Bloque(0, 300, 800, 50, true)		
		};
		

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		this.entorno.dibujarRectangulo(this.jugador.getX(), this.jugador.getY(), this.jugador.getAncho(), this.jugador.getAlto(), 0, Color.CYAN);
		//this.entorno.dibujar
		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			jugador.mover(velocidadJugador);
			
		}
		
		if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			jugador.mover(-velocidadJugador);
			
		}
		for(int i = 0; i < bloques.length; i++) {
			this.entorno.dibujarRectangulo(this.bloques[i].getX(), this.bloques[i].getY(), this.bloques[i].getAncho(), this.bloques[i].getAlto(), 0, Color.GREEN);
		}
		
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}

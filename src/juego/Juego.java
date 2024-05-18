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
	//float gravedad = 0.6f;
	float gravedad = 0.2f;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo ... - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.jugador = new Jugador(100, 500);
		this.enemigos = new Enemigo[10];
		this.bloques = new Bloque[] {
			new Bloque(0, 504, 64, 64, false),
			new Bloque(736, 504, 64, 64, false),
			new Bloque(0, 568, 800, 64, false),
			new Bloque(0, 400, 800, 64, true)
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

		jugador.setVelocidadVertical(jugador.getVelocidadVertical() + gravedad);
		
		if (entorno.estaPresionada(entorno.TECLA_DERECHA))
			jugador.setVelocidadHorizontal(velocidadJugador);	
		else if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA))
			jugador.setVelocidadHorizontal(-velocidadJugador);
		else
			jugador.setVelocidadHorizontal(0);
		
		// movimiento horizontal y colisión horizontal
		jugador.moverHorizontal();
		for (Bloque bloque : bloques)
		{
			if (bloque == null)
				continue;
			
			if (colision(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
			{
				jugador.setX(jugador.getX() - jugador.getVelocidadHorizontal());
				
				// ajuste para que el jugador quede al borde del bloque y no a una distancia de x + velocidad
				int direccion = ((jugador.getVelocidadHorizontal() > 0)? 1 : -1);
				while (!colision(jugador.getX() + direccion, jugador.getY(), jugador.getAncho(), jugador.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
				{
					jugador.setX(jugador.getX() + direccion);
				}
			}
		}

		// movimiento vertical y colisión vertical
		jugador.moverVertical();
		for (int i = 0; i < bloques.length; i++)
		{
			Bloque bloque = bloques[i];
			if (bloque == null)
				continue;
			
			if (colision(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
			{
				jugador.setY((int)(jugador.getY() - jugador.getVelocidadVertical()));

				// ajuste para que el jugador quede al borde del bloque y no a una distancia de x + velocidad
				int direccion = ((jugador.getVelocidadVertical() >= 0)? 1 : -1);
				while (!colision(jugador.getX(), jugador.getY() + direccion, jugador.getAncho(), jugador.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
				{
					jugador.setY(jugador.getY() + direccion);
				}
				
				// destrucción de bloques
				if (jugador.getVelocidadVertical() < 0 && bloque.esRompible())
					bloques[i] = null;
				
				jugador.setVelocidadVertical(0);	
				if (entorno.sePresiono('x'))
					jugador.saltar();
			}
		}
		
		// dibujar jugador
		entorno.dibujarRectangulo(jugador.getX() + jugador.getAncho() / 2, 
									jugador.getY() + jugador.getAlto() / 2, 
									jugador.getAncho(), 
									jugador.getAlto(), 
									0, 
									Color.CYAN);

		// dibujar nivel
		for (Bloque bloque : bloques) {
			if (bloque == null)
				continue;
			
			entorno.dibujarRectangulo(bloque.getX() + bloque.getAncho() / 2, 
										bloque.getY() + bloque.getAlto() / 2, 
										bloque.getAncho(), 
										bloque.getAlto(), 
										0, 
										Color.GREEN);
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
	public boolean colision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
	{
		return (x1 + w1 > x2 && x2 + w2 > x1) && 
				(y1 + h1 > y2 && y2 + h2 > y1);
	}
}

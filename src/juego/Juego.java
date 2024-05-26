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
	Personaje jugador;
	Proyectil proyectil[];
	
	Bloque bloques[];
	int velocidadJugador = 3;
	int velocidadEnemigos = 3;
	float gravedad = 0.1f;
	
	int cantidadEnemigos = 4;
	Personaje enemigos[] = new Personaje[cantidadEnemigos];
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Super Elizabeth Sisters", 1024, 768);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.jugador = new Personaje(entorno.ancho() / 2, entorno.alto() - 96, true);
		
		// crear nivel
		int anchoBloque = 32; // métodos estáticos?
		int altoBloque = 32;
		int pisos = 4;
		
		int bloquesPorPiso = entorno.ancho() / anchoBloque;
		int distanciaEntrePisos = entorno.alto() / pisos;
		int cantidadDeBloques = bloquesPorPiso * pisos;
		int bloquesRompiblesPorPiso = 4;
		
		bloques = new Bloque[cantidadDeBloques];

		int indice = 0;
		for (int piso = 0; piso < pisos; piso++)
			for (int bloque = 0; bloque < bloquesPorPiso; bloque++)
			{
				boolean rompible = false;
				if (piso > 0)
					if (piso % 2 != 0)
						rompible = bloque < bloquesRompiblesPorPiso || bloque >= bloquesPorPiso - bloquesRompiblesPorPiso;
					else
						rompible = bloque >= (bloquesPorPiso / 2 - bloquesRompiblesPorPiso / 2) && bloque < (bloquesPorPiso - (bloquesPorPiso / 2 - bloquesRompiblesPorPiso / 2));
					
				bloques[indice++] = new Bloque(bloque * anchoBloque, 
												(entorno.alto() - distanciaEntrePisos * piso) - altoBloque, 
												anchoBloque, altoBloque, rompible);
			}
		
		// esto es provisorio, solo para pruebas
		for (int i = 0; i < cantidadEnemigos; i++)
		{
			Personaje nuevo = new Personaje(this.entorno.ancho() / 2, 0, false);
			nuevo.setX(100 + nuevo.getAncho() * i);
			nuevo.setVelocidadHorizontal(velocidadEnemigos * ((i % 2 == 0)? 1 : -1));
			enemigos[i] = nuevo;
		}

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
		
		jugador.moverHorizontal();
		procesarColisionHorizontal(jugador);
		
		jugador.moverVertical();
		procesarColisionVertical(jugador);
		
		if (!jugador.estaSaltando() && entorno.sePresiono('x'))
			jugador.saltar();
		
		// dibujar jugador
		entorno.dibujarRectangulo(jugador.getX() + jugador.getAncho() / 2, 
									jugador.getY() + jugador.getAlto() / 2, 
									jugador.getAncho(), 
									jugador.getAlto(), 
									0, 
									Color.CYAN);
		
		for (Personaje enemigo : enemigos)
		{
			if (enemigo == null)
				continue;
			
			enemigo.setVelocidadVertical(enemigo.getVelocidadVertical() + gravedad);
			
			enemigo.moverHorizontal();
			procesarColisionHorizontal(enemigo);
			
			enemigo.moverVertical();
			procesarColisionVertical(enemigo);
			
			// dibujar enemigo
			entorno.dibujarRectangulo(enemigo.getX() + enemigo.getAncho() / 2, 
										enemigo.getY() + enemigo.getAlto() / 2, 
										enemigo.getAncho(), 
										enemigo.getAlto(), 
										0, 
										Color.RED);
		}

		// dibujar nivel
		for (Bloque bloque : bloques)
		{
			if (bloque == null)
				continue;
			
			entorno.dibujarRectangulo(bloque.getX() + bloque.getAncho() / 2, 
										bloque.getY() + bloque.getAlto() / 2, 
										bloque.getAncho(), 
										bloque.getAlto(), 
										0, 
										bloque.esRompible()? Color.ORANGE : Color.GREEN);
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
	
	public void procesarColisionHorizontal(Personaje personaje)
	{
		// bordes de la pantalla
		if (personaje.getX() < 0 || personaje.getX() > entorno.ancho() - personaje.getAncho())
		{
			if (!personaje.esJugador()) // control automático para los enemigos
				personaje.setVelocidadHorizontal(personaje.getVelocidadHorizontal() * -1);
			else
				if (personaje.getX() < 0)
					personaje.setX(0);
				else
					personaje.setX(this.entorno.ancho() - personaje.getAncho());
			return;
		}
		
		// bloques
		for (Bloque bloque : this.bloques)
		{
			if (bloque == null)
				continue;
			
			if (colision(personaje.getX(), personaje.getY(), personaje.getAncho(), personaje.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
			{
				personaje.setX(personaje.getX() - personaje.getVelocidadHorizontal());
				
				// ajuste para que el jugador quede al borde del bloque y no a una distancia de x + velocidad
				int direccion = ((personaje.getVelocidadHorizontal() > 0)? 1 : -1);
				while (!colision(personaje.getX() + direccion, personaje.getY(), personaje.getAncho(), personaje.getAlto(),
					bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
				{
					personaje.setX(personaje.getX() + direccion);
				}
				
				if (!personaje.esJugador()) // control automático para los enemigos
					personaje.setVelocidadHorizontal(personaje.getVelocidadHorizontal() * -1);
			}
		}
	}
	
	public void procesarColisionVertical(Personaje personaje)
	{
		personaje.setSaltando(true);
		for (int i = 0; i < bloques.length; i++)
		{
			Bloque bloque = bloques[i];
			if (bloque == null)
				continue;
			
			if (colision(personaje.getX(), personaje.getY(), personaje.getAncho(), personaje.getAlto(),
						bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
			{
				personaje.setY((int)(personaje.getY() - personaje.getVelocidadVertical()));

				// ajuste para que el jugador quede al borde del bloque y no a una distancia de x + velocidad
				int direccion = ((personaje.getVelocidadVertical() >= 0)? 1 : -1);
				while (!colision(personaje.getX(), personaje.getY() + direccion, personaje.getAncho(), personaje.getAlto(),
								bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto()))
				{
					personaje.setY(personaje.getY() + direccion);
				}
				
				// destrucción de bloques
				if (personaje.getVelocidadVertical() < 0 && bloque.esRompible())
					bloques[i] = null;
				
				personaje.setVelocidadVertical(0);
				personaje.setSaltando(false);
			}
		}
	}
}

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
	Proyectil proyectiles[];
	
	Bloque bloques[];
	int velocidadJugador = 5;
	int velocidadEnemigos = 3;
	float gravedad = 0.2f;
	
	int cantidadEnemigos = 4;
	Personaje enemigos[] = new Personaje[cantidadEnemigos];
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo ... - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.jugador = new Personaje(100, 500, true);
		this.bloques = new Bloque[] {
			new Bloque(-64, 504, 64, 64, false),
			new Bloque(800, 504, 64, 64, false),
			new Bloque(0, 568, 800, 64, false),
			new Bloque(0, 400, 800, 64, true)
		};
		this.proyectiles = new Proyectil[enemigos.length + 1];
		
		// esto es provisorio, solo para pruebas
		for (int i = 0; i < cantidadEnemigos; i++)
		{
			Personaje nuevo = new Personaje(0, 300, false);
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
		
		if (jugador.getPuedeDisparar() && entorno.sePresiono('c'))
			agregarProyectil(jugador.disparar());
		
		// dibujar jugador
		entorno.dibujarRectangulo(jugador.getX() + jugador.getAncho() / 2, 
									jugador.getY() + jugador.getAlto() / 2, 
									jugador.getAncho(), 
									jugador.getAlto(), 
									0, 
									Color.CYAN);
		
		for (int i = 0; i < enemigos.length; i++)
		{
			Personaje enemigo = enemigos[i];
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
			
			for (int j = 0; j < proyectiles.length; j++) {
				if(proyectiles[j] == null)
					continue;
				
				if(proyectiles[j].isInofensivo() && colision(proyectiles[j].getX(), 
						proyectiles[j].getY(), 
						proyectiles[j].getAncho(),
						proyectiles[j].getAlto(),
						enemigo.getX(),
						enemigo.getY(),
						enemigo.getAncho(),
						enemigo.getAlto())) {
					eliminarProyectil(j);
					enemigos[i] = null;
				}
			}
		}
		
		for (int i = 0; i < proyectiles.length; i++) {
			Proyectil proyectil = proyectiles[i];
			
			if(proyectil == null) 
				continue;
				
			entorno.dibujarRectangulo(proyectil.getX(),
					proyectil.getY(),
					proyectil.getAncho(),
					proyectil.getAlto(),
					0,
					Color.MAGENTA);
			
			proyectil.mover();
			
			if(proyectil.getX() < 0 || proyectil.getX() > entorno.ancho()) {
				eliminarProyectil(i);
			}
		}
		
		

		// dibujar nivel
		for (Bloque bloque : bloques)
		{
			if (bloque == null)
				continue;
			
			entorno.dibujarRectangulo(bloque.getX() + bloque.getAncho() / 2, bloque.getY() + bloque.getAlto() / 2, bloque.getAncho(), bloque.getAlto(), 0, Color.GREEN);
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
	
	public void agregarProyectil(Proyectil proyectil) {
		for (int i = 0; i < this.proyectiles.length; i++) {
			if(proyectiles[i] == null) {
				proyectiles[i] = proyectil;
				return;
			}
		}
	}
	
	public void eliminarProyectil(int i) {
		if(proyectiles[i].getPadre() != null) {
			proyectiles[i].getPadre().setPuedeDisparar(true);
		}
		proyectiles[i] = null;
	}
}

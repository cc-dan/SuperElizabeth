package juego;

import java.awt.Color;
import java.util.Random;
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

		this.proyectiles = new Proyectil[enemigos.length + 1];

		// spawn de enemigos
		int y_enemigo = 50;
		for (int i = 0; i < cantidadEnemigos; i++)
		{
			Personaje enemigo = new Personaje(this.entorno.ancho() / 2, y_enemigo, false);
			enemigo.setX(100 + enemigo.getAncho() * i);
			enemigo.setVelocidadHorizontal(velocidadEnemigos * ((i % 2 == 0)? 1 : -1));
			enemigos[i] = enemigo;

			if(cantidadEnemigos > 2) {
				y_enemigo += 200;
			}
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

		jugador.moverHorizontal();
		procesarColisionHorizontal(jugador);
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

		procesarColisionVertical(jugador);

		if (!jugador.estaSaltando() && entorno.sePresiono('x'))
			jugador.saltar();

		if (jugador.getPuedeDisparar() && entorno.sePresiono('c'))
			agregarProyectil(jugador.disparar());

		eliminarJugador(jugador);


		// dibujar jugador
		entorno.dibujarRectangulo(jugador.getX() + jugador.getAncho() / 2, 
				jugador.getY() + jugador.getAlto() / 2, 
				jugador.getAncho(), 
				jugador.getAlto(), 
				0, 
				Color.CYAN);

		//enemigos
		for (int i = 0; i < enemigos.length; i++)
		{
			Personaje enemigo = enemigos[i];
			if (enemigo == null)
				continue;

			// *TO FIX* Enemigo dispara proyectil hacia una misma direccion por mas
			// que rebote contra el entorno, en cambio, cuando toca los bloques
			// si cambia la direccion del proyectil

			if (enemigo.getPuedeDisparar())
				agregarProyectil(enemigo.disparar());

			enemigo.setVelocidadVertical(enemigo.getVelocidadVertical() + gravedad);

			enemigo.moverHorizontal();
			procesarColisionHorizontal(enemigo);

			// *TO FIX* enemigos no rebotan contra los costados
			if (enemigo.getX() < 1 || enemigo.getX() > 1020)
				enemigo.cambiarSentido();

			enemigo.moverVertical();
			procesarColisionVertical(enemigo);

			// dibujar enemigo
			entorno.dibujarRectangulo(enemigo.getX() + enemigo.getAncho() / 2, 
					enemigo.getY() + enemigo.getAlto() / 2, 
					enemigo.getAncho(), 
					enemigo.getAlto(), 
					0, 
					Color.RED);

			// matar enemigo, desaparecer enemigo y proyectil
			for (int j = 0; j < proyectiles.length; j++) 
			{
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

			//dibujar proyectiles
			entorno.dibujarRectangulo(proyectil.getX(),
					proyectil.getY() +25, //altura del disparo
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

	public void eliminarJugador(Personaje jugador) {

		for (int i = 0; i < proyectiles.length; i++) 
		{
			if(proyectiles[i] == null)
				continue;

			if(colision(proyectiles[i].getX(), 
					proyectiles[i].getY(), 
					proyectiles[i].getAncho(),
					proyectiles[i].getAlto(),
					jugador.getX(),
					jugador.getY(),
					jugador.getAncho(),
					jugador.getAlto()) ||
					colision(enemigos[i].getX(), 
							enemigos[i].getY(), 
							enemigos[i].getAncho(),
							enemigos[i].getAlto(),
							jugador.getX(),
							jugador.getY(),
							jugador.getAncho(),
							jugador.getAlto())) 
			{
				eliminarProyectil(i);
				System.out.println("colision");
				//jugador = null;
				//jugador.setX(500);
			}
		}	
	}
}

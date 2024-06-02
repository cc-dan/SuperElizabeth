package juego;

import java.awt.Color;
import java.util.Random;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	Personaje jugador;
	Gatito gatito;
	Proyectil proyectiles[];
	Bloque bloques[];
	int velocidadJugador = 5;
	int velocidadEnemigos = 2;
	float gravedad = 0.1f;
	boolean sePerdio;
	boolean seGano;
	private Image fondo;
	int puntaje = 0;
	int enemEliminados = 0;	
	int anchoBloque = 32;
	int altoBloque = 32;
	int pisos = 4;
	int bloquesRompiblesPorPiso = 4;
	int enemigosPorPiso = 2;
	int cantidadEnemigos = enemigosPorPiso * (pisos - 1);
	Personaje enemigos[] = new Personaje[cantidadEnemigos];
	int bloquesPorPiso;
	int distanciaEntrePisos;
	int cantidadDeBloques;

	Juego()
	{
		// Inicializa el objeto entorno

		this.entorno = new Entorno(this, "Super Elizabeth Sisters", 1024, 768);

		// Inicializar lo que haga falta para el juego
		// ...
		this.jugador = new Personaje(entorno.ancho() / 2, entorno.alto() - 96, true, entorno);

		this.fondo = Herramientas.cargarImagen("fondo2.png");

		bloquesPorPiso = entorno.ancho() / anchoBloque;
		distanciaEntrePisos = entorno.alto() / pisos;
		cantidadDeBloques = bloquesPorPiso * pisos;

		int bordes = 2;
		bloques = new Bloque[cantidadDeBloques + bordes];

		int piso;
		int indiceBloques = 0;
		for (piso = 0; piso < pisos; piso++)
			for (int bloque = 0; bloque < bloquesPorPiso; bloque++)
			{
				boolean rompible = false;
				if (piso > 0)
					if (piso % 2 != 0)
						rompible = bloque < bloquesRompiblesPorPiso || bloque >= bloquesPorPiso - bloquesRompiblesPorPiso;

						else
							rompible = bloque >= (bloquesPorPiso / 2 - bloquesRompiblesPorPiso / 2) && bloque < (bloquesPorPiso - (bloquesPorPiso / 2 - bloquesRompiblesPorPiso / 2));

							bloques[indiceBloques++] = new Bloque(bloque * anchoBloque, 
									(entorno.alto() - distanciaEntrePisos * piso) - altoBloque, 
									anchoBloque, altoBloque, rompible, entorno);
			}
		bloques[cantidadDeBloques] = new Bloque(0 - anchoBloque, 0, anchoBloque, entorno.alto() - distanciaEntrePisos, false, entorno);
		bloques[cantidadDeBloques + 1] = new Bloque(entorno.ancho(), 0, anchoBloque, entorno.alto() - distanciaEntrePisos, false, entorno);

		this.gatito = new Gatito(entorno.ancho() / 2, 20, anchoBloque, altoBloque, entorno);

		int margen = 5; // Los enemigos pueden morir pero sus proyectiles siguen existiendo, por lo cual se puede llenar el array al morir un enemigo tras disparar, reaparecer y disparar otra vez.
		this.proyectiles = new Proyectil[enemigos.length + 1 + margen];

		// spawn de enemigos
		int indiceEnemigos = 0;
		for (int i = 0; i < pisos - 1; i++)
		{
			Personaje a = new Personaje(this.entorno.ancho() / 2, distanciaEntrePisos * i + distanciaEntrePisos + 32, false, entorno);
			Personaje b = new Personaje(this.entorno.ancho() / 2, distanciaEntrePisos * i + distanciaEntrePisos + 32, false, entorno);
			a.setY(a.getY() - a.getAlto() * 2);
			a.setVelocidadHorizontal(-velocidadEnemigos);
			b.setY(b.getY() - b.getAlto() * 2);
			b.setVelocidadHorizontal(velocidadEnemigos);
			enemigos[indiceEnemigos] = a;
			enemigos[indiceEnemigos + 1] = b;
			indiceEnemigos += 2;
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

		if(seGano || sePerdio) {			
			entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2 -90, 0, 1);
			puntaje();
			return;
		}

		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2 -90, 0, 1);

		puntaje();

		// MOVIMIENTO JUGADOR:
		jugador.setVelocidadVertical(jugador.getVelocidadVertical() + gravedad);

		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			jugador.setVelocidadHorizontal(velocidadJugador);
		}
		else if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			jugador.setVelocidadHorizontal(-velocidadJugador);
		}
		else {
			jugador.setVelocidadHorizontal(0);

		}		
		jugador.moverHorizontal();
		procesarColisionHorizontal(jugador);
		jugador.moverVertical();

		procesarColisionVertical(jugador);

		if (!jugador.estaSaltando() && entorno.sePresiono('x')) {
			jugador.saltar();
			jugador.setSaltando(true);
		}
		if (jugador.getPuedeDisparar() && entorno.sePresiono('c')) {
			agregarProyectil(jugador.disparar());
		}
		// DIBUJAR JUGADOR
		jugador.dibujarse();

		if(colision(gatito.getX(), 
				gatito.getY(), 
				gatito.getAncho(),
				gatito.getAlto(),
				jugador.getX(),
				jugador.getY(),
				jugador.getAncho(),
				jugador.getAlto()))
			ganar();

		for (int i = 0; i < enemigos.length; i++)
		{
			Personaje enemigo = enemigos[i];

			if (enemigo == null)
				continue;

			enemigo.setContProyectilActual(enemigo.getContProyectilActual() - 1);

			enemigos[i].setVelocidadHorizontal(velocidadEnemigos * Integer.signum(enemigos[i].getVelocidadHorizontal()));

			for (Personaje e : enemigos)
			{
				if (e == null || e == enemigos[i]) break;
				if (colision(e.getX(), e.getY(), e.getAncho(), e.getAlto(), enemigos[i].getX(), enemigos[i].getY(), enemigos[i].getAncho(), enemigos[i].getAlto()))
					enemigos[i].setVelocidadHorizontal(enemigos[i].getVelocidadHorizontal() + 1);
			}

			if (enemigo.getContProyectilActual() == 0 && enemigo.getPuedeDisparar() && !enemigo.estaSaltando())
				agregarProyectil(enemigo.disparar());

			enemigo.setVelocidadVertical(enemigo.getVelocidadVertical() + gravedad);

			enemigo.moverHorizontal();
			procesarColisionHorizontal(enemigo);

			enemigo.moverVertical();
			procesarColisionVertical(enemigo);

			if(colision(enemigo.getX(), 
					enemigo.getY(), 
					enemigo.getAncho(),
					enemigo.getAlto(),
					jugador.getX(),
					jugador.getY(),
					jugador.getAncho(),
					jugador.getAlto())) {

				perder();
			}
			enemigo.dibujarse();

			// matar enemigo, desaparecer enemigo y proyectil
			if (enemigo.getX() < 0 - enemigo.getAncho() || enemigo.getX() > entorno.ancho())
			{
				matarEnemigo(i);
				break;
			}

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
					matarEnemigo(i);
					puntaje += 2;
					enemEliminados++;
				}
			}
		}
		for (int i = 0; i < proyectiles.length; i++) {

			Proyectil proyectil = proyectiles[i];

			if(proyectil == null) 
				continue;

			proyectil.dibujar();
			proyectil.mover();

			if(!proyectil.isInofensivo() && colision(proyectil.getX(), 
					proyectil.getY(), 
					proyectil.getAncho(),
					proyectil.getAlto(),
					jugador.getX(),
					jugador.getY(),
					jugador.getAncho(),
					jugador.getAlto())) {
				perder();
			}

			if(proyectil.getX() < 0 || proyectil.getX() > entorno.ancho()) {
				eliminarProyectil(i);
				break;
			}

			// colisión con proyectiles enemigos
			for (int j = 0; j < proyectiles.length; j++) {
				if (proyectiles[j] == null) break;
				if (proyectiles[j].isInofensivo() != proyectil.isInofensivo() && 
						colision(proyectil.getX(), proyectil.getY(), proyectil.getAncho(), proyectil.getAlto(),
								proyectiles[j].getX(), proyectiles[j].getY(), proyectiles[j].getAncho(), proyectiles[j].getAlto()))
				{
					eliminarProyectil(i);
					eliminarProyectil(j);
				}
			}
		}

		// dibujar nivel/bloques
		for (Bloque bloque : bloques)
		{
			if (bloque == null)
				continue;

			bloque.dibujarse();
		}
		gatito.dibujarse();
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
		if (personaje.esJugador())
		{
			if ((personaje.getX() < 0 && personaje.getVelocidadHorizontal() < 0))
				personaje.setX(0);
			else if (personaje.getX() + personaje.getAncho() > entorno.ancho() && personaje.getVelocidadHorizontal() > 0)
				personaje.setX(entorno.ancho() - personaje.getAncho());
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
		if (personaje.getY() < 0)
		{
			personaje.setVelocidadVertical(0);
			return;
		}
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
				if (personaje.getY() > bloque.getY() && bloque.esRompible())
				{
					bloques[i] = null;
				}
				else if (personaje.getY() < bloque.getY())
					personaje.setSaltando(false);

				personaje.setVelocidadVertical(0);
			}
		}
	}
	public void agregarProyectil(Proyectil proyectil) {
		for (int i = 0; i < this.proyectiles.length; i++) {
			if(proyectiles[i] == null) {
				Personaje padre = proyectil.getPadre();				
				proyectiles[i] = proyectil;
				padre.setPuedeDisparar(false);
				padre.setContProyectilActual(padre.getContProyectil());
				return;
			}
		}
	}
	public void eliminarProyectil(int i) {
		if(proyectiles[i] != null && proyectiles[i].getPadre() != null) {
			proyectiles[i].getPadre().setPuedeDisparar(true);
		}
		proyectiles[i] = null;
	}
	public void perder() {

		sePerdio = true;
	}
	public void ganar() {		
		seGano = true;		
	}
	public void puntaje() {

		if (seGano || sePerdio) {
			entorno.cambiarFont("times new roman", 50, Color.white);
			entorno.escribirTexto("PUNTAJE = " + puntaje, 200 , 320);

			entorno.cambiarFont("times new roman", 50, Color.white);
			entorno.escribirTexto("ENEMIGOS ELIMINADOS = " + enemEliminados, 200 , 370);

		}else {
			entorno.cambiarFont("times new roman", 20, Color.white);
			entorno.escribirTexto("PUNTAJE = " + puntaje, 5 , 700);

			entorno.cambiarFont("times new roman", 20, Color.white);
			entorno.escribirTexto("ENEMIGOS ELIMINADOS = " + enemEliminados, 5 , 720);			
		}
	}

	void matarEnemigo(int index)
	{
		enemigos[index] = null;
		agregarEnemigo(index);
	}

	void agregarEnemigo(int index)
	{
		for (int i = 0; i < pisos; i++)
		{
			int cantidadEnemigos = 0;
			for (int x = 0; x < enemigos.length; x++)
			{
				if (enemigos[x] == null) continue;
				if (!estaEnPiso(i, enemigos[x]))
				{					
					continue;
				}				
				cantidadEnemigos++;

				for (int j = 0; j < enemigos.length; j++)
				{
					if (j == x || enemigos[j] == null) continue;
					if (estaEnPiso(i, enemigos[j]))
					{						
						cantidadEnemigos++;
						break;
					}
				}
				// ya terminó de contar
				if (cantidadEnemigos >= 2)
					break;
			}
			if (cantidadEnemigos < 2)
			{
				for (int x = 0; x < enemigos.length; x++)
					if (enemigos[x] == null)
					{
						boolean mirandoDerecha = new Random().nextBoolean();
						Personaje e = new Personaje(mirandoDerecha? 0 : this.entorno.ancho() - 32, distanciaEntrePisos * i + distanciaEntrePisos + 32, false, entorno);
						e.setY(e.getY() - e.getAlto() * 2);
						e.setVelocidadHorizontal(mirandoDerecha? velocidadEnemigos : -velocidadEnemigos);
						enemigos[x] = e;
						return;
					}
			}
		}
	}
	boolean estaEnPiso(int nivel, Personaje p)
	{
		int techo = nivel * distanciaEntrePisos;
		int piso = techo + distanciaEntrePisos;
		return (p.getY() > techo && p.getY() < piso);
	}
}

package juego;

public class Jugador {
	private int x;
	private int y;
	private int velocidad;
	private int ancho;
	private int alto;
	private int direccion;
	
	
	public Jugador(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.velocidad = 5;
		this.ancho = 32;
		this.alto = 64;
	}

	public void mover(int velocidad) {
		this.x += velocidad;
		if(velocidad > 0) {
			this.direccion = 1;
		} else if(velocidad < 0) {
			this.direccion = -1;
		}
		
	}
	
	public void saltar() {
		
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
	
	//public Proyectil disparar() {
		
	//}
	
	
	
}

package juego;

public class Proyectil {
	private int x;
	private int y;
	private int velocidad;
	private int ancho; //= ?
	private int alto; //= ?
	private boolean inofensivo;
	private boolean mirandoDerecha = true;

	public Proyectil(int x, int y, int velocidad, int ancho, int alto, boolean inofensivo) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.ancho = 20;
		this.alto = 10;
		this.inofensivo = inofensivo;	
	}
	public void mover(int direccion) {
		this.x += this.velocidad * direccion;		
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
		if(velocidad > 0) {
			this.mirandoDerecha = true;
		} else if(velocidad < 0) {
			this.mirandoDerecha = false;
		}
	}

	public boolean mirandoALaDerecha()
	{
		return mirandoDerecha;
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

	public boolean isInofensivo() {
		return inofensivo;
	}

	public void setInofensivo(boolean inofensivo) {
		this.inofensivo = inofensivo;
	}
}

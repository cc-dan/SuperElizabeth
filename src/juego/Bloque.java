package juego;

public class Bloque {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private boolean rompible;
	
	public Bloque(int x, int y, int ancho, int alto, boolean rompible) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.rompible = rompible;
	
	}

	
	//GETTERS Y SETTERS
	
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

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public boolean esRompible() {
		return rompible;
	}
}

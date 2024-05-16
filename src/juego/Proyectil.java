package juego;

public class Proyectil {
	private int x;
	private int y;
	private int velocidad;
	private int ancho; //= ?
	private int alto; //= ?
	private boolean inofensivo;
	
	public Proyectil(int x, int y, boolean inofensivo) {
		this.x = x;
		this.y = y;
		this.inofensivo = inofensivo;
	}
}

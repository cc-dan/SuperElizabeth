package juego;

public class Enemigo {
	private int x;
	private int y;
	private int velocidadHorizontal;
	private int velocidadVertical;
	private int ancho;
	private int alto;
	
	public Enemigo(int x, int y) {
		this.x = x;
		this.y = y;
		this.velocidadHorizontal = 5;
		this.ancho = 32;
		this.alto = 64;
	}

	public void moverHorizontal() {
		this.x += this.velocidadHorizontal;
		
	}
	
	public void moverVertical()
	{
		this.y += Math.ceil(this.velocidadVertical); // evitamos que el jugador no se mueva cuando 0 < velocidad vertical < 1 
	}
	
	public int getVelocidadHorizontal() {
		return velocidadHorizontal;
	}
	
	
	
	//public Proyectil disparar() {
		
	//}
	
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
}

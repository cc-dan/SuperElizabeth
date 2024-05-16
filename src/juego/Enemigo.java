package juego;

public class Enemigo {
	private int x;
	private int y;
	private int velocidad;
	private int ancho;
	private int alto;
	
	public Enemigo(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public void mover(int direccion) {
		this.x += this.velocidad * direccion;
		
	}
	
	
	
	//public Proyectil disparar() {
		
	//}
	
}

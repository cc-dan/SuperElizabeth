package juego;

public class Enemigo {
	private int x;
	private int y;
	private int velocidad;
	private int ancho;
	private int alto;
	private boolean mirandoDerecha = true;
	private float velocidadVertical;
	
	public Enemigo(int x, int y, int ancho, int alto, int velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = 32;
		this.alto = 64;
		this.velocidad = velocidad;
		
	}
	public void mover(int direccion) {
		this.x += this.velocidad * direccion;		
	}
	public void cambiarSentido()
	{
		this.velocidad = -1 * this.velocidad;
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
	public boolean mirandoALaDerecha()
	{
		return mirandoDerecha;
	}
	public float getVelocidadVertical()
	{
		return this.velocidadVertical;
	}
	
	public void setVelocidadVertical(float velocidad)
	{
		this.velocidadVertical = velocidad;
	}
	public boolean caida()
	{
		return this.velocidadVertical < 0;
	}
	public void moverVertical()
	{
		this.y += Math.ceil(this.velocidadVertical);
	}
	
	
	//public Proyectil disparar() {
		
	//}
	
}

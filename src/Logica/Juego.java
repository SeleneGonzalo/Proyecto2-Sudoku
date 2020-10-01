package Logica;
import java.util.Random;

public class Juego {
	private Casillas [][] tablero;
	private int filas;
	public Juego() {
		this.filas = 9;
		tablero = new Casillas[filas][filas];
		
		for (int i =0; i<filas; i++) {
			for (int j =0; j<filas; j++) {
				/*Random rand = new Random();
				int value = rand.nextInt(2);*/
				tablero[i][j] = new Casillas();
				/*if (value == 0){
					int valor = rand.nextInt(tablero[i][j].getCantElementos());
					tablero[i][j].setValor(valor);	
				}*/
			}
		}
		
	}
	
	public void accionar(Casillas c) {
		c.actualizar();
	}
	
	public Casillas getCelda(int i, int j) {
		return tablero[i][j];
	}
	
	public int getCantFilas() {
		return filas;
	}
}
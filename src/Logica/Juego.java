package Logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Juego {
	private Casillas [][] tablero;
	private int filas;
	
	public Juego(){		
			this.filas = 9;
			tablero = new Casillas[filas][filas];
			for (int i =0; i<filas; i++) {
				for (int j =0; j<filas; j++)
					tablero[i][j] = new Casillas();
			}
			try {
				inicializar_tablero ();
			} catch (FileNotFoundException e) {}
	}
	
	private boolean establecer_valor () {
		boolean establecer=false;
		Random rand = new Random();
		int valor = rand.nextInt(2);
		if (valor == 0)
			establecer=true;
		return establecer;
	}
	private void inicializar_tablero () throws FileNotFoundException {
		String []arreglo;
		try {
			FileReader f = new FileReader("C:\\Users\\Selene\\eclipse-workspace\\Sudoku\\src\\archivo.txt");
			BufferedReader b = new BufferedReader(f);
			for (int i=0; i<9; i++) {
				arreglo = b.readLine().split(" ");
				for (int j=0; j<9; j++) {
					if (establecer_valor()) {
						tablero[i][j].setValor(Integer.parseInt(arreglo[j])-1);
						tablero[i][j].getEntidadGrafica().getLabel().setEnabled(false);
					}
				}
			}
			b.close();
	    } catch (IOException e) {}
	}
	

	public void presionar(Casillas c) {
		c.actualizar();
	}
	
	public Casillas get_casilla(int i, int j) {
		return tablero[i][j];
	}
	
	public int get_cantidad_filas() {
		return filas;
	}
}
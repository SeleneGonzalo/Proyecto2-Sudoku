package Logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Juego {
	private Casilla [][] tablero;
	private LinkedList<Casilla> lista_control;
	private int filas;
	
	public Juego(){		
		this.filas = 9;
		tablero = new Casilla[filas][filas];
		lista_control = new LinkedList<Casilla>();
		for (int i =0; i<filas; i++) {
			for (int j =0; j<filas; j++)
				tablero[i][j] = new Casilla(i,j,this);
		}
		try {
			inicializar_tablero ();
		} catch (FileNotFoundException e) {}
	}
	
	private void inicializar_tablero () throws FileNotFoundException {
 		String []arreglo;
 		int tablero_auxiliar[][] = new int [9][9];
		try {
			FileReader f = new FileReader("C:\\Users\\Selene\\eclipse-workspace\\Sudoku\\src\\archivo.txt");
			BufferedReader b = new BufferedReader(f);
			for (int i=0; i<9; i++) {
				arreglo = b.readLine().split(" ");
				for (int j=0; j<9; j++) {
					int valor = (Integer.parseInt(arreglo[j]));
					if (!se_repiten_elementos_inicio (tablero_auxiliar,i,j,valor)) {
						tablero_auxiliar[i][j] = valor;
						if (establecer_valor()) {
							tablero[i][j].setValor(valor);
							tablero[i][j].getEntidadGrafica().getLabel().setEnabled(false);
						} else
							lista_control.add(tablero[i][j]);
					} else {
						JOptionPane.showMessageDialog(null, "Los elementos del archivo cargado incumplen las reglas del juego");
						System.exit(0);
					}
				}
			}
			b.close();
	    } catch (IOException e) {}
	}
	
	private boolean se_repiten_elementos_inicio (int matriz[][], int fila, int columna, int valor) {
		boolean se_repite = false;
		int valor_fila = (fila/3)*3;
		int valor_columna = (columna/3)*3;
		for (int i=0; i<9 && !se_repite; i++) {
			se_repite = matriz[i][columna] == valor && i != fila;
		}
		
		for (int j=0; j<9 && !se_repite; j++) {
			se_repite = matriz[fila][j] == valor && j != columna;
		}
	
		for (int i=valor_fila; i < 3 && !se_repite; i++) {
			for (int j=valor_columna; j < 3 && !se_repite; j++) {
				se_repite = matriz[i][j] == valor && i!= fila && j != columna;
			} 
		}
		return se_repite;
	}
	
	public boolean se_repiten_elementos (int fila, int columna, int valor) {
		boolean se_repite = false;
		int valor_fila = (fila/3)*3;
		int valor_columna = (columna/3)*3;
		for (int i=0; i<9 && !se_repite; i++) {
			if (tablero[i][columna].getValor() != null)
				se_repite = tablero[i][columna].getValor() == valor && i != fila;
		}
		
		for (int j=0; j<9 && !se_repite; j++) {
			if (tablero[fila][j].getValor() != null)
				se_repite = tablero[fila][j].getValor() == valor && j != columna;
		}
	
		for (int i=valor_fila; i < (valor_fila + 3) && !se_repite; i++) {
			for (int j=valor_columna; j < (valor_columna + 3) && !se_repite; j++) {
				if (tablero[i][j].getValor() != null)
					se_repite = tablero[i][j].getValor() == valor && i!= fila && j != columna;
			} 
		}
		
		if(fila == 7 && columna == 7) {
			for (int i = 0; i<lista_control.size();i++)
				System.out.println(lista_control.get(i).getValor());
		}
		
		lista_control.remove(tablero[fila][columna]);
		if (se_repite)
			lista_control.add(tablero[fila][columna]);
		
		
		
		if (lista_control.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Sudoku resuelto de forma correcta");
			System.exit(0);
		}
		return se_repite;
	}
	private boolean establecer_valor () {
		boolean establecer=false;
		Random rand = new Random();
		int valor = rand.nextInt(2);
		if (valor == 0) 
			establecer=true;
		return establecer;
	}
	

	public void presionar(Casilla c) {
		c.actualizar();
	}
	
	public Casilla get_casilla(int i, int j) {
		return tablero[i][j];
	}
	
	public int get_cantidad_filas() {
		return filas;
	}
}
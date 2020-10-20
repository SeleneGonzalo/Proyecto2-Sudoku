package Logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import GUI.GUI;

public class Juego {
	private Casilla [][] tablero;
	private LinkedList<Casilla> lista_control;
	private int filas;
	private boolean gano;
	
	public Juego(){		
		this.filas = 9;
		tablero = new Casilla[filas][filas];
		lista_control = new LinkedList<Casilla>();
		gano=false; 
		
		for (int i =0; i<filas; i++) {
			for (int j =0; j<filas; j++)
				tablero[i][j] = new Casilla(i,j,this);
		}
		try {
			inicializar_tablero ();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se pudo inicializar el tablero");
		}
	}
	
	private void inicializar_tablero () throws FileNotFoundException {
 		String []arreglo;
 		int tablero_auxiliar[][] = new int [9][9];
		try {
			FileReader f = new FileReader(System.getProperty("user.dir")+"\\src\\archivo.txt");
			BufferedReader b = new BufferedReader(f);
			for (int i=0; i<9; i++) {
				arreglo = b.readLine().split(" ");
				if (cumple_formato_archivo (arreglo)) {
					for (int j=0; j<9; j++) {
						int valor = (Integer.parseInt(arreglo[j]));
						if (!se_repiten_elementos_inicio (tablero_auxiliar,i,j,valor)) {
							tablero_auxiliar[i][j] = valor;
							if (establecer_valor()) {
								tablero[i][j].setValor(valor);
								tablero[i][j].getGrafico().getLabel().setEnabled(false);
							} else 
								lista_control.add(tablero[i][j]);
						} else {
							JOptionPane.showMessageDialog(null, "Los elementos del archivo incumplen con las reglas del juego");
							System.exit(0);
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Los elementos del archivo cargado no cumplen con el formato necesario");
					System.exit(0);
							
				}
			}
			b.close();
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, "El archivo no pudo ser leido");
	    	System.exit(0);
	    }
	}
	
	private boolean cumple_formato_archivo (String arreglo []) {
		boolean cumple = true;
		if (arreglo.length == 9) {
			for (int i=0; i < 9 && cumple; i++)
				cumple = isInteger(arreglo[i]);
		} else
			cumple=false;
		return cumple;
	}
	
	private boolean isInteger (String s) {
		boolean toReturn=false;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El archivo no tiene un formato válido");
			System.exit(0);
		}
		toReturn = true;
		return toReturn;
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
		
		lista_control.remove(tablero[fila][columna]);
		if (se_repite)
			lista_control.add(tablero[fila][columna]);
			
		if (lista_control.isEmpty()) {
			tablero[fila][columna].getGrafico().actualizar(tablero[fila][columna].getValor(),false);
			setGano(true);
			JOptionPane.showMessageDialog(null, "Sudoku resuelto de forma correcta");
			System.exit(0);
		}
		return se_repite;
	}
	
	public void controlar_errores() {
		Casilla c;
		for (int i =0; i< lista_control.size(); i++) {
			c = lista_control.get(i);
			if(c.getValor()!=0)
				c.estaRepetido(se_repiten_elementos(c.getFila(),c.getColumna(),c.getValor()));
		}
		
	}
	private boolean establecer_valor () {
		boolean establecer=false;
		Random rand = new Random();
		int valor = rand.nextInt(100);
		if (valor < 90)
			establecer=true;
		return establecer;
	}
	
	public boolean getGano () {
		return gano;
	}
	
	public void setGano (boolean gano) {
		this.gano = gano;
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
package Logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Juego {
	private Casilla [][] tablero;
	private LinkedList<Casilla> lista_control;
	private int filas;
	private int columnas;
	private boolean gano;
	private String ruta;
	public Juego(String ruta){	
		this.ruta = ruta;
		this.filas = 9;
		this.columnas = 9;
		gano=false;
		tablero = new Casilla[filas][filas];
		lista_control = new LinkedList<Casilla>();
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
 		InputStream in;
 		InputStreamReader inr;
 		BufferedReader bfr;
		try {
			in = Juego.class.getClassLoader().getResourceAsStream(ruta);
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
			for (int i=0; i<9; i++) {
				arreglo = bfr.readLine().split(" ");
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
			bfr.close();
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
	
	public LinkedList<Casilla> se_repiten_elementos (int fila, int columna, int valor) {
		LinkedList<Casilla> lista_repetidos = new LinkedList<Casilla>();
		int valor_fila = ((int) (fila / 3)) *3;
		int valor_columna = ((int) (columna / 3)) * 3;
		for (int i=0; i<filas; i++) {
			if (tablero[i][columna].getValor() != null)
				if (tablero[i][columna].getValor() == valor && i != fila)
					lista_repetidos.add(tablero[i][columna]);
		}
		
		for (int j=0; j<columnas; j++) {
			if (tablero[fila][j].getValor() != null)
				if (tablero[fila][j].getValor() == valor && j != columna)
					lista_repetidos.add(tablero[fila][j]);
		}
	
		for (int i=valor_fila; i < (valor_fila + 3); i++) {
			for (int j=valor_columna; j < (valor_columna + 3); j++) {
				if (tablero[i][j].getValor() != null)
					if (tablero[i][j].getValor() == valor && i!= fila && j != columna)
						lista_repetidos.add(tablero[i][j]);
			} 
		}
		
		return lista_repetidos;
	}
	
	public void controlar_lista() {
		LinkedList<Casilla> lista_auxiliar = new LinkedList<Casilla>();
		LinkedList<Casilla> lista_con_errores= new LinkedList<Casilla>();
		boolean repetido=false;
		Casilla c =null,aux = null;
		for (int i=0; i<lista_control.size(); i++) {
			c = lista_control.get(i);
			repetido = !(se_repiten_elementos(c.getFila(), c.getColumna(), c.getValor()).isEmpty());
			if ((repetido || c.getValor() == 0) && !lista_auxiliar.contains(c))
				lista_auxiliar.add(c);
			
			if (c.getValor() != 0)
				c.estaRepetido(repetido);
		}
		if (!(lista_control.isEmpty()))
				aux = lista_control.getLast();
		lista_con_errores = (se_repiten_elementos(aux.getFila(), aux.getColumna(), aux.getValor()));
		for (int e =0; e < lista_con_errores.size(); e++) {
			lista_auxiliar.add(lista_con_errores.get(e));
			if (lista_con_errores.get(e).getValor() != 0)
				lista_con_errores.get(e).estaRepetido(true);
		}
		lista_control = lista_auxiliar;
		if (lista_control.isEmpty()) {
			c.getGrafico().actualizar(c.getValor(),false);
			gano = true;
			JOptionPane.showMessageDialog(null, "Sudoku resuelto de forma correcta");
			System.exit(0);
		}
	}
	public boolean controlar_errores(int fila, int columna, int valor) {
		boolean repetido = false;
		if(valor!=0) {
			repetido = !(se_repiten_elementos(fila, columna, valor).isEmpty());
				if(!repetido)
					lista_control.remove(tablero[fila][columna]);
				
				if((repetido || tablero[fila][columna].getValor()==0))
					lista_control.add(tablero[fila][columna]);	
		}
		return repetido;
	}
	
	private boolean establecer_valor () {
		boolean establecer=false;
		Random rand = new Random();
		int valor = rand.nextInt(3);
		if (valor < 2)
			establecer=true;
		return establecer;
	}
	
	public boolean getGano () {
		return gano;
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
	public int get_cantidad_columnas() {
		return columnas;
	}
}
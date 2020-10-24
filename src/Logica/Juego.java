package Logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Juego {
	private Casilla [][] tablero;
	private int filas;
	private int columnas;
	private boolean gano;
	private String ruta;
	private boolean se_inicio_correcto;
	
	public Juego(String ruta){	
		se_inicio_correcto = false;
		this.ruta = ruta;
		this.filas = 9;
		this.columnas = 9;
		gano=false;
		tablero = new Casilla[filas][filas];
		for (int i =0; i<filas; i++) {
			for (int j =0; j<filas; j++)
				tablero[i][j] = new Casilla(i,j);
		}
		try {
			inicializar_tablero ();
		} catch (FileNotFoundException e) {}
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
								tablero[i][j].setInicial(true);
							} 
						} 
					}
				}
			}
			bfr.close();
	    } catch (IOException e) {}
		se_inicio_correcto = true;
	}
	public boolean se_inicio () {
		return se_inicio_correcto;
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
		} catch (NumberFormatException e) {}
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
	
	public void se_repiten_elementos () {
		boolean casillas_con_error=false;
		boolean casillas_vacias=false;
		for (int i=0; i<filas; i++) {
			for (int j=0; j<columnas; j++) {
				tablero[i][j].setEstado(false);
				this.se_repiten_elementos(i,j, tablero[i][j].getValor());
				if (tablero[i][j].getEstado())
					casillas_con_error = true;
				if (tablero[i][j].CeldaVacia())
					casillas_vacias=true;
			}	
		}
		if (!casillas_con_error && !casillas_vacias) 
			gano=true;
	}
	
	public void se_repiten_elementos (int fila, int columna, int valor) {
		boolean se_repite = false, aux = false;
		int valor_fila = ((int) (fila / 3)) *3;
		int valor_columna = ((int) (columna / 3)) * 3;
		
		for (int i=0; i<filas; i++) {
			if (tablero[i][columna].getValor() != null)
				se_repite = (tablero[i][columna].getValor() == valor && i != fila);
			if (se_repite) {
				aux = true;
				tablero[i][columna].setEstado(true);
			}
		}
		
		for (int j=0; j<columnas; j++) {
			if (tablero[fila][j].getValor() != null)
				se_repite = (tablero[fila][j].getValor() == valor && j != columna);
			if (se_repite) {
				aux = true;
				tablero[fila][j].setEstado(true);
			} 
		}
	
		for (int i=valor_fila; i < (valor_fila + 3); i++) {
			for (int j=valor_columna; j < (valor_columna + 3); j++) {
				if (tablero[i][j].getValor() != null)
					se_repite = (tablero[i][j].getValor() == valor && i!= fila && j != columna);
				if (se_repite) {
					aux = true;
					tablero[i][j].setEstado(true);
				}
			} 
		}
		tablero[fila][columna].setEstado(aux);
	}
	
	
	private boolean establecer_valor () {
		boolean establecer=false;
		Random rand = new Random();
		int valor = rand.nextInt(3);
		if (valor == 0)
			establecer=true;
		return establecer;
	}
	
	public boolean getGano () {
		return gano;
	}
	
	public void presionar(Casilla c) {
		c.actualizar();
		this.se_repiten_elementos();
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
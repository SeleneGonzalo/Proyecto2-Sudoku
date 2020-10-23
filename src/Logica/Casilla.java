package Logica;
public class Casilla {
	private Integer valor;
	private CasillaGrafica grafico;
	private int fila,columna;
	private Juego juego;
	
	public Casilla(int fila,int columna, Juego juego) {
		this.valor = 0;
		this.grafico = new CasillaGrafica();
		this.fila = fila;
		this.columna = columna;
		this.juego = juego;
	}
	
	public void actualizar() {
		boolean se_repite_elemento;
		if (valor != 0 && valor < grafico.getCantidadElementos())
			valor++;
		else 
			valor = 1;
		
		se_repite_elemento = juego.se_repiten_elementos(fila, columna, valor);
		grafico.actualizar(valor,se_repite_elemento);
		juego.controlar_errores();
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer valor) {
		if (valor != 0 && valor <= grafico.getCantidadElementos()) {
			this.valor = valor;
			grafico.actualizar(this.valor,true);
		} else 
			this.valor = 1;
	}
	
	public int getFila () {
		return fila;
	}
	public int getColumna() {
		return columna;
	}
	public CasillaGrafica getGrafico() {
		return grafico;
	}
	public void estaRepetido(boolean repetido) {
		grafico.actualizar(valor, repetido);
	}
	public void setGrafica(CasillaGrafica gra) {
		grafico = gra;
	}
}
package Logica;


public class Casilla {
	private Integer valor;
	private Graficos grafico;
	private int fila,columna;
	private Juego juego;
	
	public Casilla(int fila,int columna, Juego juego) {
		this.valor = 0;
		this.grafico = new Graficos();
		this.fila = fila;
		this.columna = columna;
		this.juego = juego;
	}
	
	public void actualizar() {
		if (valor != null && valor < this.getCantElementos())
			valor++;
		else {
			valor = 1;
		}
		boolean cumple = !juego.se_repiten_elementos(fila, columna, valor);
		grafico.actualizar(valor,cumple);
		
	}
	
	public int getCantElementos() {
		return this.grafico.getImagenes().length;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer valor) {
		if (valor!=null && valor <= this.getCantElementos()) {
			this.valor = valor;
			grafico.actualizar(this.valor,true);
		}else {
			this.valor = 1;
		}
	}
	
	public Graficos getEntidadGrafica() {
		return grafico;
	}
	
	public void setGrafica(Graficos gra) {
		grafico = gra;
	}
}
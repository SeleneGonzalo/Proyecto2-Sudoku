package Logica;
public class Casilla {
	private Integer valor;
	private CasillaGrafica grafico;
	private int fila,columna;
	private boolean error;
	private boolean inicio;
	
	public Casilla(int fila,int columna ) {
		inicio = false;
		error=false;
		this.valor = 0;
		this.grafico = new CasillaGrafica();
		this.fila = fila;
		this.columna = columna;
	}
	
	public void setEstado (boolean error) {
        this.error = error;
        grafico.actualizar(valor, error);
    }
	
	public boolean getEstado () {
		return error;
	}
	
	public void setInicial(boolean inicio) {
		this.inicio = inicio;
		grafico.Deshabilitar();
	}
	
	public boolean CeldaVacia () {
		return valor == 0;
	}
	
	public void actualizar() {
        if (!inicio) {
			if (valor != 0 && valor < grafico.getCantidadElementos())
	            valor++;
	        else 
	             valor = 1;
	        grafico.actualizar(valor, error);
        }
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

	public void setGrafica(CasillaGrafica gra) {
		grafico = gra;
	}
}
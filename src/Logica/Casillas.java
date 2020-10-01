package Logica;

public class Casillas {
	private Integer valor;
	private Graficos grafico;
	
	public Casillas() {
		this.valor = null;
		this.grafico = new Graficos();
	}
	
	public void actualizar() {
		if (valor != null && valor + 1 < this.getCantElementos())
			valor++;
		else
			valor = 0;
		
		grafico.actualizar(valor);
	}
	
	public int getCantElementos() {
		return this.grafico.getImagenes().length;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer valor) {
		if (valor!=null && valor < this.getCantElementos()) {
			this.valor = valor;
			grafico.actualizar(this.valor);
		}else {
			this.valor = null;
		}
	}
	
	public Graficos getEntidadGrafica() {
		return grafico;
	}
	
	public void setGrafica(Graficos gra) {
		grafico = gra;
	}
}
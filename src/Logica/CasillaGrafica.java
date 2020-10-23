package Logica;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CasillaGrafica {
	private ImageIcon grafico;
	private String[] imagenes;
	private String [] imagenes_error;
	private JLabel label;
	
	public CasillaGrafica() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/Imagenes/1.png","/Imagenes/2.png","/Imagenes/3.png","/Imagenes/4.png","/Imagenes/5.png",
									"/Imagenes/6.png","/Imagenes/7.png","/Imagenes/8.png","/Imagenes/9.png"};
		
		this.imagenes_error = new String[] {"/Imagenes/1 mal.png","/Imagenes/2 mal.png","/Imagenes/3 mal.png","/Imagenes/4 mal.png","/Imagenes/5 mal.png",
				"/Imagenes/6 mal.png","/Imagenes/7 mal.png","/Imagenes/8 mal.png","/Imagenes/9 mal.png"};

		ImageIcon trebol = new ImageIcon(this.getClass().getResource("/Imagenes/trebol.png"));
		this.grafico.setImage(trebol.getImage());
		label = new JLabel();
		label.setIcon(trebol);
	}
	
	public ImageIcon getIcono() {
		return this.grafico;
	}
	
	public int getCantidadElementos() {
		return imagenes.length;
	}
	public void actualizar(int valor, boolean se_repite) {
		if (se_repite) { 
			this.grafico.setImage(new ImageIcon(this.getClass().getResource(this.imagenes_error[valor-1])).getImage());
		} else
			this.grafico.setImage(new ImageIcon (this.getClass().getResource(this.imagenes[valor-1])).getImage());

		label.repaint();
	}
	public JLabel getLabel () {
		return label;
	}
}

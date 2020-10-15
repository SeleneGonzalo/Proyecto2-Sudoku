package Logica;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Graficos {
	private ImageIcon grafico;
	private String[] imagenes;
	private JLabel label;
	
	public Graficos() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/Imagenes/1.png","/Imagenes/2.png","/Imagenes/3.png","/Imagenes/4.png","/Imagenes/5.png",
									"/Imagenes/6.png","/Imagenes/7.png","/Imagenes/8.png","/Imagenes/9.png"};
		
		ImageIcon trebol = new ImageIcon(this.getClass().getResource("/Imagenes/trebol.png"));
		this.grafico.setImage(trebol.getImage());
		label = new JLabel("");
		label.setIcon(trebol);
	}

	public ImageIcon getIcono() {
		return this.grafico;
	}
	
	public void actualizar(int valor, boolean cumple) {
		if (cumple) {
			this.grafico.setImage(new ImageIcon (this.getClass().getResource(this.imagenes[valor-1])).getImage());
		} else 
			this.grafico.setImage(new ImageIcon(this.getClass().getResource("/Imagenes/"+valor+" mal.png")).getImage());
			
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	public JLabel getLabel () {
		return label;
	}
}

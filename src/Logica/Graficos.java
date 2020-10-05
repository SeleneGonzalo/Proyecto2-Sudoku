package Logica;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Graficos {
	private ImageIcon grafico;
	private String[] imagenes;
	private String[] imagenes_incorrecto;
	private JLabel label;
	
	public Graficos() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/Imagenes/1.png","/Imagenes/2.png","/Imagenes/3.png","/Imagenes/4.png","/Imagenes/5.png",
									"/Imagenes/6.png","/Imagenes/7.png","/Imagenes/8.png","/Imagenes/9.png"};
		this.imagenes_incorrecto = new String [] {"/Imagenes/1 mal.png","/Imagenes/2 mal.png","/Imagenes/3 mal.png","/Imagenes/4 mal.png","/Imagenes/5 mal.png",
				"/Imagenes/6 mal.png","/Imagenes/7 mal.png","/Imagenes/8 mal.png","/Imagenes/9 mal.png"};
		
		ImageIcon trebol = new ImageIcon(this.getClass().getResource("/Imagenes/trebol.png"));
		this.grafico.setImage(trebol.getImage());
		label = new JLabel("");
		label.setIcon(trebol);
	}
	
	public void actualizar(int index) {
		if (index < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[index]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	public ImageIcon getIcono() {
		return this.grafico;
	}
	
	public void setIcono(ImageIcon grafico) {
		this.grafico = grafico;
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

package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Logica.Casilla;
import Logica.Juego;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Juego juego;
	int tiempo;
	JLabel lb;
	JLabel lb_1;
	JLabel lb_2;
	JLabel lb_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "No se pudo iniciar el juego");
					System.exit(0);
				}
			}
		});
	}

	@SuppressWarnings("unused")
	public GUI() {
		String error = null;
		juego = new Juego("Solucion/archivo.txt");
		if (!juego.se_inicio()) {
			JOptionPane.showMessageDialog(null, "No se pudo iniciar el juego");
			System.exit(0);
		}
		JOptionPane.showMessageDialog(null, "Los objetos disponibles para completar el tablero son los numeros del 1 al 9.\n Al clickear cada casilla se va incrementando el valor desde el 1 hasta el 9, y luego vuelve a comenzar \n ¡Suerte!");
		setResizable(false);
		temporizador(1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 72, 684, 425);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(9, 9, 0, 0));
		
		lb= new JLabel("New label");
		lb.setBounds(219, 11, 40, 54);
		contentPane.add(lb);
		lb.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0 reloj.png")));
		
		lb_1 = new JLabel("New label");
		lb_1.setBounds(269, 11, 40, 54);
		contentPane.add(lb_1);
		lb_1.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0 reloj.png")));
		
		lb_2 = new JLabel("New label");
		lb_2.setBounds(338, 11, 40, 54);
		contentPane.add(lb_2);
		lb_2.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0 reloj.png")));
		
		lb_3 = new JLabel("New label");
		lb_3.setBounds(388, 11, 40, 54);
		contentPane.add(lb_3);
		lb_3.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0 reloj.png")));
		
		for (int i = 0; i <juego.get_cantidad_filas(); i++) {
			for(int j =0; j<juego.get_cantidad_columnas(); j++) {
				Casilla c = juego.get_casilla(i,j);
				ImageIcon grafico = c.getGrafico().getIcono();
				JLabel label = c.getGrafico().getLabel();
				if (j%3 == 0 && i%3 == 0)
					label.setBorder(BorderFactory.createMatteBorder(5,5, 1, 1, Color.DARK_GRAY));
				else if (i%3==0)
					label.setBorder(BorderFactory.createMatteBorder(5,1, 1, 1, Color.DARK_GRAY));
				else if (j%3==0)
					label.setBorder(BorderFactory.createMatteBorder(1,5, 1, 1, Color.DARK_GRAY));
				else
					label.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.DARK_GRAY));
				
				panel.add(label);
				
				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						label.setIcon(grafico);
					}
				});

				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						juego.presionar(c);
						if (juego.getGano()) {
							JOptionPane.showMessageDialog(null, "Sudoku resulta de forma correcta");
							System.exit(0);
						}
					}
				});
			}
		}
	}

	public void temporizador (int time) {
		Timer timer = new Timer();
		TimerTask tk = new TimerTask() {
			public void run() {
				int unidad_max, unidad_min;
				tiempo++;
				unidad_max = tiempo/60;
				unidad_min = tiempo %60;
				lb_3.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+unidad_min%10+" reloj.png")));
				lb_2.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+unidad_min/10+" reloj.png")));
				lb_1.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+unidad_max%10+" reloj.png")));
				lb.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+unidad_max/10+" reloj.png")));
				
				if (juego.getGano()) {
					cancel();
				}
			}
		};
		timer.schedule(tk,time,time);
	}
}

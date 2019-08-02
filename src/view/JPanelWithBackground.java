package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel {

	private Image backgroundImage;

	// Some code to initialize the background image.
	// Here, we use the constructor to load the image. This
	// can vary depending on the use case of the panel.

	public JPanelWithBackground(String fileName)  {
		try {
			backgroundImage = ImageIO.read(new File(fileName)).getScaledInstance(1600, 1000, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
//	public static void main(String[] args) throws IOException {
//		JFrame f = new JFrame("aa");
//		  f.setSize(1000, 1000);
//		  f.setVisible(true);
//		  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		  f.add(new JPanelWithBackground("C:\\Users\\HP\\eclipse-workspace\\milestone2\\src\\view\\overbg.jpg"));
////		  f.repaint();
//		  f.revalidate();
//	}
}



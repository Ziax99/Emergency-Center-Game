package view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controller.CommandCenter;

public class StartMenu extends JFrame {

	private JButton start;
	private JButton exit;
	private JLabel label;
	private JPanel panel;
	public static String name;
	CommandCenter command;

	public StartMenu(CommandCenter command) throws IOException {

		super("THE CURSED CITY");
		
		this.command = command;

		panel = new PanelWithBackground("C:\\gui\\backGround.jpg");
		panel.setSize(new Dimension(1680, 1050));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel temp = new JPanel();
		temp.setMaximumSize(new Dimension(2000,200));
		panel.add(temp);
		temp.setBackground(new Color(0, 0, 0, 0));
		
		label = new JLabel("WELCOME TO THE CURSED CITY");
		JPanel p = new JPanel();
		p.setBackground(new Color(0,0,0,0));
		p.setMaximumSize(new Dimension(1400, 300));
		p.add(label);
		panel.add(p);
		label.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 60));
		label.setForeground(Color.WHITE);
		
		JPanel p1 = new JPanel();
		p1.setMaximumSize(new Dimension(2000, 100));
		p1.setBackground(new Color(0,0,0,0));
		start = new JButton("START");
		p1.add(start);
		start.setPreferredSize(new Dimension(200, 70));
		start.setBackground(GUI.bg);
		start.setFocusable(false);
		start.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		start.setForeground(Color.white);
		start.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		panel.add(p1);
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				name = JOptionPane.showInputDialog("Please enter your name");
				if(name == null || name == " ")
					name = "Guest";
				JOptionPane.showMessageDialog(null, "welcome " + name + "\nyou are now in charge of saving this city",
						"WELCOME", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				command.setGui(new GUI(command));
				try {
					command.GUIStuff();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		exit = new JButton("EXIT");
		JPanel p2 = new JPanel();
		p2.setMaximumSize(new Dimension(2000, 100));
		p2.setBackground(new Color(0,0,0,0));
		p2.add(exit);
		panel.add(p2);
		exit.setPreferredSize(new Dimension(200, 70));
		exit.setBackground(GUI.bg);
		exit.setFocusable(false);
		exit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		exit.setForeground(Color.white);
		exit.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				System.exit(0);
			}
		});


//		setLocationRelativeTo(null);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1680, 1050);
		setVisible(true);
		validate();

	}

//	public static void main(String[] args) throws IOException {
//		new StartMenu();
//	}

	public class PanelWithBackground extends JPanel {

		private Image backgroundImage;

		// Some code to initialize the background image.
		// Here, we use the constructor to load the image. This
		// can vary depending on the use case of the panel.

		public PanelWithBackground(String fileName) {
			try {
				backgroundImage = ImageIO.read(new File(fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
//			.getScaledInstance(1000, 1000, Image.SCALE_SMOOTH)
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw the background image.
			g.drawImage(backgroundImage, 0, 0, this);
		}
}
}

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controller.CommandCenter;

public class GameOver extends JFrame {
	
	JPanel panel;
	
	JLabel over, casaulties, percentage;
	
	JButton restart, endGame;
	
	public GameOver(int dead, int total) throws IOException {
		super("Game Over");
		
		panel = new JPanelWithBackground("C:\\gui\\over3.jpg");
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel t0 = new JPanel();
		t0.setMaximumSize(new Dimension(2000, 30));
		panel.add(t0);
		t0.setBackground(new Color(0,0,0,0));
		over = new JLabel("Game Over");
		over.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 130));
		over.setForeground(new Color(9, 4, 70));
		JPanel t1 = new JPanel();
		t1.setMaximumSize(new Dimension(2000, 220));
		t1.add(over);
		t1.setBackground(new Color(0, 0, 0, 0));
		panel.add(t1);
		
		if(dead != 0)
			casaulties = new JLabel("You Couldn't Save " + dead + " citizens");
		else
			casaulties = new JLabel("Congratulations, You managed to save all the citizens");
		casaulties.setFont(new Font(Font.SERIF, Font.BOLD, 60));
		casaulties.setForeground(Color.white);
		JPanel t2 = new JPanel();
		t2.setMaximumSize(new Dimension(2000, 150));
		t2.add(casaulties);
		t2.setBackground(new Color(0, 0, 0, 0));
		panel.add(t2);
		
		String text = "";
		percentage = new JLabel();
		percentage.setFont(new Font(Font.SERIF, Font.BOLD, 50));
		JPanel t3 = new JPanel();
		t3.setMaximumSize(new Dimension(2000, 100));
		t3.add(percentage);
		t3.setBackground(new Color(0, 0, 0, 0));
		panel.add(t3);
		
		int per = (int)Math.ceil((1.0*dead / total) * 100);
		if(dead == 0)
			text = "";
		else if(per > 50) {
			text = ("Thanos gives you his regards! you erased more than half of humanity");
			percentage.setForeground(new Color(142,36,170));
		}
		else if(per > 30) {
			text = "Good Job! You managed to save " + (100-per) + " of the population";
			percentage.setForeground(new Color(142,36,170));
		}
		else {
			text = "Excellent Job! You saved " + (100-per) + "of the population";
			percentage.setForeground(new Color(142,36,170));
		}
		percentage.setText(text);
		
		JPanel t5 = new JPanel();
		t5.setMaximumSize(new Dimension(2000, 150));
		t5.setBackground(new Color(0, 0, 0, 0));
		panel.add(t5);
		
		restart = new JButton("Restart");
		restart.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 24));
		restart.setPreferredSize(new Dimension(200, 100));
		JPanel t4 = new JPanel();
		t4.setMaximumSize(new Dimension(2000, 150));
		t4.add(restart);
		t4.setBackground(new Color(0, 0, 0, 0));
		restart.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		restart.setFocusable(false);
		restart.setBackground(GUI.bg);
		restart.setForeground(Color.white);
		restart.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						GameOver.this.dispose();
						try {
							new CommandCenter();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		panel.add(t4);
		t4.add(new JLabel("                                             "));
		
		endGame = new JButton("End Game");
		endGame.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 24));
		endGame.setPreferredSize(new Dimension(200, 100));
		endGame.setBackground(GUI.bg);
		endGame.setForeground(Color.white);
		endGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		endGame.setFocusable(false);

		t4.add(endGame);

		endGame.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.exit(0);
					}
				});
		
		setVisible(true);
		setSize(1600, 1000);
		setLocationRelativeTo(null);
		this.add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		panel.revalidate();
		panel.repaint();
		revalidate();
		repaint();
	}
//	public static void main(String[] args) {
//		try {
//			new GameOver(0,5);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

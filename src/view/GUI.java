package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import controller.CommandCenter;
import controller.GUIListener;
import controller.Tap;
import controller.UnitPane;

public class GUI extends JFrame {

	JPanel panel;
	

	JPanel right;

	JPanel left;

	JPanel info;

	JPanel display;

	Container contentPane;

	GUIListener command;

	titleBar title;

	public static final Color bg = new Color(22, 25, 37);

	public GUI(GUIListener command) {

		super("THE CURSED CITY");

		this.command = command;

		panel = (JPanel) getContentPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(bg);
		panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 0));
		
		
		
		right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(bg);
		right.setMaximumSize(new Dimension(1000, 1000));

		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBackground(bg);
		left.setMaximumSize(new Dimension(1000, 1000));

		title = new titleBar((CommandCenter)command);

		info = new JPanel();
		info.setBackground(bg);
		info.setPreferredSize(new Dimension(1000, 500));
		info.setMaximumSize(new Dimension(1000, 500));
		
		
		display = new JPanel();
		display.setLayout(new GridLayout(10, 10));
		display.setBackground(bg);
		display.setMaximumSize(new Dimension(1000, 1000));

		left.add(title);
		left.add(info);


		panel.add(left);
		panel.add(right);

		left.revalidate();
		title.revalidate();
		panel.revalidate();
		display.revalidate();
		right.revalidate();

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
		setVisible(true);

	}

	public JPanel getLeft() {
		return left;
	}

	public JPanel getInfo() {
		return info;
	}

	public titleBar gettitle() {
		return title;
	}

	public JPanel getDisplay() {
		return display;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JPanel getRight() {
		return right;
	}
}

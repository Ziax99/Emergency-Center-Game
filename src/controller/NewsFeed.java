package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import TCP.ChatScreen;
import view.GUI;

public class NewsFeed {

	public JPanel panel;
	public JScrollPane scroll;
	public JPanel container;
	public JPanel container1;

	public JButton share;
	public JPanel container2;

	CommandCenter command;

	public NewsFeed(CommandCenter command) {
		// TODO Auto-generated constructor stub
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 8000));
		panel.setAutoscrolls(true);
		panel.setBackground(GUI.bg);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.command = command;

		scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//		avaS.setMaximumSize(new Dimension(800, 110));
		scroll.setPreferredSize(new Dimension(900, 320));
		scroll.setBackground(GUI.bg);
		scroll.setAutoscrolls(true);
		//		avaS.setFocusable(false);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		container1 = new JPanel();
		container1.add(scroll);
		container1.setMaximumSize(new Dimension(950, 330));
		container1.setPreferredSize(new Dimension(950, 330));
		container1.setBackground(GUI.bg);

		share = new JButton("Share");
		share.setFont(new Font(Font.SERIF, Font.BOLD, 26));
		share.setPreferredSize(new Dimension(100, 50));
		share.setFocusable(false);
		share.setBackground(GUI.bg);
		share.setForeground(Color.white);
		share.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		share.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							command.clientScreen = new ChatScreen(NewsFeed.this);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
						}
					}
				});


		container2 = new JPanel();
		container2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		container2.setBackground(GUI.bg);
		container2.setPreferredSize(new Dimension(950, 70));
		container2.setMaximumSize(new Dimension(950, 70));
		container2.add(share);

		container = new JPanel();
		container.add(container1);
		container.add(container2);
		container.setMaximumSize(new Dimension(1000, 470));
		container.setPreferredSize(new Dimension(1000, 470));
		container.setBackground(GUI.bg);
		container.setBorder(BorderFactory.createTitledBorder(null, "News Feed", 0, 0, new Font(Font.SERIF, 0, 20), Color.WHITE));

		share.revalidate();
		share.repaint();
		container.repaint();
		container1.repaint();
		container2.repaint();
		container.revalidate();
		container1.revalidate();
		container2.revalidate();
	}

}

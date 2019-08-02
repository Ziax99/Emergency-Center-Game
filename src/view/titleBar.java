package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controller.CommandCenter;
import controller.GUIListener;

public class titleBar extends JPanel{
	
	CommandCenter command;
	
	JLabel name, casaulties, currentCycle;
	JButton ret, sound, endCycle;
	
	boolean on;
	
	long ms;
	
	public titleBar(CommandCenter command) {
		// TODO Auto-generated constructor stub
		this.command = (CommandCenter)command;
		
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(GUI.bg);
		
		
		ret = new JButton();
		ret.setBackground(GUI.bg);
		ret.setFocusable(false);
		ret.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		ret.setPreferredSize(new Dimension(50, 50));
		ret.setIcon(createImage("C:\\gui\\back.png"));
		ret.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						command.getGui().dispose();
						try {
							new StartMenu(command);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
		add(ret);
		
		sound = new JButton();
		sound.setBackground(GUI.bg);
		sound.setFocusable(false);
		sound.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		sound.setPreferredSize(new Dimension(50, 50));
		on = true;
		sound.setIcon(createImage("C:\\gui\\speakerOn.png"));
		sound.addActionListener(
			new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(on) {
					on = false;
					ms = command.clip.getMicrosecondPosition();
					command.clip.stop();
					sound.setIcon(createImage("C:\\gui\\soundOff.png"));
				}else {
					on = true;
					command.clip.setMicrosecondPosition(ms);
					command.clip.start();
					command.clip.loop(Clip.LOOP_CONTINUOUSLY);
					sound.setIcon(createImage("C:\\gui\\speakerOn.png"));
				}
			}
		});
		add(sound);
		
		JLabel temp = new JLabel("              ");
		add(temp);
		
		name = new JLabel("Player: " + StartMenu.name + "     ");
		add(name);
		name.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 24));
		name.setForeground(Color.white);
		
		casaulties = new JLabel("Casaulties: " + 0 + "     ");
		add(casaulties);
		casaulties.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		casaulties.setForeground(Color.white);
		
		
		currentCycle = new JLabel("Current Cycle: " + 0 + "     ");
		add(currentCycle);
		currentCycle.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		currentCycle.setForeground(Color.white);
		
		
		endCycle = new JButton("End Cycle");
		add(endCycle);
		endCycle.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		endCycle.setBackground(GUI.bg);
		endCycle.setFocusable(false);
		endCycle.setForeground(Color.white);
		endCycle.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		endCycle.addActionListener(
				
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							command.nextCycle();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
	}
	
	public void nextCycle(int cycle, int dead) {
		currentCycle.setText("Current Cycle: " + cycle + "     ");
		casaulties.setText("Casaulties: " + dead + "     ");
	}
	
	public ImageIcon createImage(String path) {
		BufferedImage bf;
		try {
			bf = ImageIO.read(new File(path));
			return new ImageIcon(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
}

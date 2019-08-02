package controller;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


import view.GUI;

public class Tap {

	JPanel avaP;
	JScrollPane avaS;
	JPanel avaC;
	
	
	public Tap() {
		
		avaP = new JPanel();
		avaP.setPreferredSize(new Dimension(800, 1500));
		avaP.setAutoscrolls(true);
		avaP.setBackground(GUI.bg);
		
		avaS = new JScrollPane(avaP, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		avaS.setMaximumSize(new Dimension(800, 110));
		avaS.setPreferredSize(new Dimension(800, 110));
		avaS.setBackground(GUI.bg);
//		avaS.setFocusable(false);
		avaS.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		avaS.setAutoscrolls(true);
		
		avaC = new JPanel();
		avaC.add(avaS);
		avaC.setMaximumSize(new Dimension(1000, 120));
//		avaC.setPreferredSize(new Dimension(1000, 120));
		avaC.setBackground(GUI.bg);
		
	}
	
}

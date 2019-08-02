package controller;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


import view.GUI;

public class CitizensPane {

	JPanel avaP;
	JScrollPane avaS;
	JPanel avaC;
	
	
	public CitizensPane() {
		
		avaP = new JPanel();
		avaP.setPreferredSize(new Dimension(250, 1500));
		avaP.setAutoscrolls(true);
		avaP.setBackground(GUI.bg);
		
		avaS = new JScrollPane(avaP, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		avaS.setMaximumSize(new Dimension(300, 300));
		avaS.setPreferredSize(new Dimension(300, 300));
		avaS.setBackground(GUI.bg);
//		avaS.setFocusable(false);
		avaS.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		avaC = new JPanel();
		avaC.add(avaS);
		avaC.setPreferredSize(new Dimension(350, 350));
		avaC.setMaximumSize(new Dimension(300, 350));
//		avaC.setPreferredSize(new Dimension(1000, 120));
		avaC.setBackground(GUI.bg);
		
	}
	
}

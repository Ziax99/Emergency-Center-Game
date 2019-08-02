package TCP;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import controller.NewsFeed;
import view.GUI;
import view.StartMenu;

public class ServerScreen extends JFrame implements KeyListener{

	TextArea ta;
	JPanel panel, left, right, chat;


	JPanel container;

	JPanel chatp;

	JButton enter;

	JScrollPane scroll;

	Server server;

	public ServerScreen(Server server) {
		// TODO Auto-generated constructor stub
		super("Chat Screen");

		this.server = server;

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(GUI.bg);
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		left = new JPanel();
		left.setBackground(GUI.bg);
		left.setMaximumSize(new Dimension(600, 1000));
		panel.add(left);

		JPanel temp = new JPanel();
		temp.setBackground(GUI.bg);
		temp.setMaximumSize(new Dimension(20, 1000));
		panel.add(temp);

		right = new JPanel();
		right.setBackground(GUI.bg);
		right.setMaximumSize(new Dimension(600, 1000));
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		panel.add(right);

		chat = createChat();
		right.add(chat);

		container = new JPanel();
		container.setBackground(GUI.bg);
		container.setMaximumSize(new Dimension(600, 130));
		container.setLayout(new FlowLayout(FlowLayout.LEFT));
		container.setBorder(BorderFactory.createCompoundBorder());

		ta = new TextArea();
		ta.setPreferredSize(new Dimension(530, 50));
		ta.setMaximumSize(new Dimension(530, 50));
		ta.setBackground(GUI.bg);
		ta.setFont(new Font(Font.SERIF, 0, 18));
		ta.setForeground(Color.WHITE);
		ta.addKeyListener(this);

		container.add(ta);
		right.add(container);

		enter = new JButton();
		enter.setPreferredSize(new Dimension(50, 50));
		enter.setFocusable(false);
		enter.setToolTipText("Send");
		enter.setBackground(GUI.bg);
		enter.setIcon(createImage("C:\\gui\\enter.png"));
		enter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		enter.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ServerScreen.this.addmsgServer(ta.getText());
					}
				});
		container.add(enter);

		add(panel);
		setSize(1300, 1000);
		setBackground(GUI.bg);

		setLocationRelativeTo(null);
		setVisible(true);

		addWindowListener(new WindowDestroyer(server, this));

		nextCycle();
		container.revalidate();
		container.repaint();
		right.revalidate();
		right.repaint();
		left.revalidate();
		left.repaint();
		panel.revalidate();
		panel.repaint();
		revalidate();
		repaint();

	}

	public void nextCycle() {
		//		JPanel toAdd = createPanel();
		//		left.removeAll();
		//		left.add(toAdd);
		//		if(!isVisible())
		//			setVisible(true);
		//		toAdd.revalidate();
		//		toAdd.repaint();
		left.revalidate();
		left.repaint();
		panel.revalidate();
		panel.repaint();
		revalidate();
		repaint();
	}

	public JPanel createChat() {
		// TODO Auto-generated method stub

		chatp = new JPanel();
		chatp.setPreferredSize(new Dimension(2000, 8000));
		chatp.setMaximumSize(new Dimension(2000, 8000));
		chatp.setAutoscrolls(true);
		chatp.setBackground(GUI.bg);
		chatp.setLayout(new FlowLayout(FlowLayout.LEFT));

		scroll = new JScrollPane(chatp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//		avaS.setMaximumSize(new Dimension(800, 110));
		scroll.setPreferredSize(new Dimension(550, 830));
		scroll.setBackground(GUI.bg);
		//		scroll.setAutoscrolls(true);

		//		avaS.setFocusable(false);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		//		JScrollBar sb = scroll.getVerticalScrollBar();
		//		sb.setValue( sb.getMaximum()-100 );

		JPanel container1 = new JPanel();
		container1.add(scroll);
		container1.setMaximumSize(new Dimension(600, 850));
		container1.setPreferredSize(new Dimension(600, 850));
		container1.setBackground(GUI.bg);
		container1.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		chatp.revalidate();
		chatp.repaint();
		scroll.revalidate();
		scroll.repaint();
		container1.revalidate();
		container1.repaint();
		return container1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER) {
			String s = ta.getText();
			if(s == "")
				return;
			addmsgServer(s);
		}
	}

	public void addmsgServer(String s) {
		// TODO Auto-generated method stub


		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerScreen.this.server.dout.writeUTF(s);
					ServerScreen.this.server.dout.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}).start();

		container.removeAll();

		ta = new TextArea();
		ta.setPreferredSize(new Dimension(530, 50));
		ta.setMaximumSize(new Dimension(530, 50));
		ta.setBackground(GUI.bg);
		ta.setFont(new Font(Font.SERIF, 0, 18));
		ta.setForeground(Color.WHITE);
		ta.addKeyListener(this);
		container.add(ta);
		container.add(enter);


		JLabel label = new JLabel("<html>" + StartMenu.name + ": " + s.replaceAll("\n", "<br>") + "</html>");
		label.setFont(new Font(Font.SERIF, 0, 24));
		label.setForeground(Color.black);


		JPanel tmp2 = new JPanel();
		tmp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		tmp2.setPreferredSize(new Dimension(2000, 50));
		tmp2.setBackground(new Color(235, 94, 40));
		tmp2.add(label);
		chatp.add(tmp2);


		container.revalidate();
		container.repaint();
		chatp.revalidate();
		chatp.repaint();
		tmp2.revalidate();
		tmp2.repaint();
		chat.revalidate();
		chat.repaint();

	}

	public void addmsgClient(String s) {

		JLabel label = new JLabel("<html>" + server.clientName + ": " + s.replaceAll("\n", "<br>") + "</html>");
		label.setFont(new Font(Font.SERIF, 0, 24));
		label.setForeground(Color.black);

		JPanel tmp = new JPanel();
		tmp.setLayout(new FlowLayout(FlowLayout.LEFT));
		tmp.setPreferredSize(new Dimension(2000, 50));
		tmp.setBackground(new Color(25, 133, 161));
		tmp.add(label);
		chatp.add(tmp);

		container.revalidate();
		container.repaint();
		chatp.revalidate();
		chatp.repaint();
		tmp.revalidate();
		tmp.repaint();
		chat.revalidate();
		chat.repaint();

	}

	public void addNews(String s) {

		JPanel news = new JPanel();
		news.setPreferredSize(new Dimension(540, 8000));
		news.setAutoscrolls(true);
		news.setBackground(GUI.bg);
		news.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel label = new JLabel("<html>" + s.replaceAll("\n", "<br>") + "</html>");
		label.setFont(new Font(Font.SERIF, 0, 20));
//		label.setPreferredSize(new Dimension(540, 8000));
		label.setForeground(Color.white);
		label.setLayout(new FlowLayout(FlowLayout.LEFT));
		news.add(label);

		JScrollPane scroll = new JScrollPane(news, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//		avaS.setMaximumSize(new Dimension(800, 110));
		scroll.setPreferredSize(new Dimension(550, 910));
		scroll.setBackground(GUI.bg);
		scroll.setAutoscrolls(true);
		//		avaS.setFocusable(false);
		scroll.setAutoscrolls(true);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JScrollBar sb = scroll.getVerticalScrollBar();
		sb.setValue( sb.getMaximum()-100 );

		JPanel container1 = new JPanel();
		container1.add(scroll);
		container1.setMaximumSize(new Dimension(600, 930));
		container1.setPreferredSize(new Dimension(600, 930));
		container1.setBackground(GUI.bg);
		container1.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		
		left.removeAll();
		left.add(container1);
		
		left.revalidate();
		left.repaint();
		news.revalidate();
		news.repaint();
		scroll.revalidate();
		scroll.repaint();
		container1.revalidate();
		container1.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public ImageIcon createImage(String path) {
		BufferedImage bf;
		try {
			bf = ImageIO.read(new File(path));
			return new ImageIcon(bf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
}

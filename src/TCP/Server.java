package TCP;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ShutdownChannelGroupException;

import javax.swing.JOptionPane;

import view.StartMenu;

public class Server {

	ServerSocket ss;
	Socket s;

	DataInputStream din;
	DataOutputStream dout;


	String str = "";

	public boolean opened;
	
	String clientName;
	
	ServerScreen serverScreen;
	
	
	public Server() throws IOException {
		// TODO Auto-generated constructor stub
		ss = new ServerSocket(5000);
		
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						s = ss.accept();
						break;
					}catch(Exception e) {
						
					}
				}

				try {
					din = new DataInputStream(s.getInputStream());
					dout = new DataOutputStream(s.getOutputStream());
					dout.writeUTF(StartMenu.name);
					
					clientName = din.readUTF();
					
//					String news = din.readUTF();
//					serverScreen.addNews(news);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				
				serverScreen = new ServerScreen(Server.this);

				opened = true;
				while(opened) {
					try {
						str = din.readUTF();
						if(str.startsWith("persil"))
							serverScreen.addNews(str.substring(6));
						else if(str.equals("detol")) {
							JOptionPane.showMessageDialog(null, clientName + " has left the game");
							dout.writeUTF("detol2");
							break;
						}
						else if(str.equals("detol2")) {
							break;
						}
						else
							serverScreen.addmsgClient(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
//						JOptionPane.showMessageDialog(null, clientName + " has left the game");
//						break;
					}
				}
				//add str to the chat
				
				try {
					
					din.close();
					dout.close();
					ss.close();
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
				try {
					new Server();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(opened) {
//					try {
//						str2 = br.readLine();
//						dout.writeUTF(str2);
//						dout.flush();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//
//			}
//		}).start();

//		System.out.println((din == null) + "   " + (dout == null));

	}

}

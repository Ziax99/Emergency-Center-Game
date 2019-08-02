package TCP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import view.StartMenu;

public class Client {

	Socket s;

	DataInputStream din;
	DataOutputStream dout;

//	BufferedReader br;

	String str = "", str2 = "";
	
	boolean opened;
	
	String ServerName;
	
	ChatScreen chatScreen;
	
	public Client(String host, ChatScreen chatScreen) throws IOException {
		// TODO Auto-generated constructor stub
		s = new Socket(host, 5000);
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		
		this.chatScreen = chatScreen;
		
//		br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("before");
		ServerName = din.readUTF();
//		System.out.println("after");
		dout.writeUTF(StartMenu.name);
		
		opened = true;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(opened && !s.isClosed()) {
					try {
						str = din.readUTF();
						System.out.println(str);
						if(str.equals("detol")){
							System.out.println("here");
							JOptionPane.showMessageDialog(null, ServerName + " has left the game");
							dout.writeUTF("detol2");
							break;
						}
						else if(str.equals("detol2")) {
							break;
						}
						else
							chatScreen.addmsgServer(str);
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
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
//				}
//			}
//		}).start();
	}

}

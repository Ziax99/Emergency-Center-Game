package TCP;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

public class WindowDestroyer extends WindowAdapter {

	Object o;
	JFrame frame;
	
	public WindowDestroyer(Object o, JFrame frame) {
		this.o = o;
		this.frame = frame;
	}
	
	public void windowClosing(WindowEvent e) {
		frame.dispose();
		if(o instanceof Client && !((Client) o).s.isClosed()) {
			Client c = (Client) o;
			try {
				c.dout.writeUTF("detol");
				c.dout.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(o instanceof Server && !((Server) o).s.isClosed()) {
			Server c = (Server) o;
			try {
				c.dout.writeUTF("detol");
				c.dout.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
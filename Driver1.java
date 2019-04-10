import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Driver1 {
	/**
	 * @param args
	 */
	public static void main (String[] args) {
		MessageGui MessageGui = new MessageGui();
		do {
			MessageGui.recievePackets();
		} while(true);
	}
	 
}


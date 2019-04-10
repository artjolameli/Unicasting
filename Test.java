import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Test {

	private static InetAddress myAddress;
	private static final int MY_PORT_NUMBER = 64000;
	private static DatagramSocket mySocket;
	
	public static void main(String[] args) {

		try {
			myAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.exit(-1);
		}

		System.out.println("My IP Address = " + myAddress.getHostAddress());
	
		try {
			mySocket = new DatagramSocket(MY_PORT_NUMBER, myAddress);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(-1);
		}
		
		byte[] outBuffer;
		String outMessage = "Hello Communication World!!!";
		outBuffer = outMessage.getBytes();
		
		DatagramPacket outPacket = new DatagramPacket(outBuffer,
													  outBuffer.length,
													  myAddress,
													  MY_PORT_NUMBER);
		
		try {
			mySocket.send(outPacket);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Going to sleep .... z z z z z");
	
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			System.exit(-1);
		}		
		
		System.out.println("Just woke up!!");
		
		byte[] inBuffer = new byte[100];
		for ( int i = 0 ; i < inBuffer.length ; i++ ) {
			inBuffer[i] = ' ';
		}
		
		DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
		
		try {
			mySocket.receive(inPacket);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}

		inBuffer = inPacket.getData();
		String inMessage = new String(inBuffer);
		InetAddress senderAddress = inPacket.getAddress();
		int senderPort = inPacket.getPort();
		
		System.out.println();
		System.out.println("Message        = " + inMessage);
		System.out.println("Sender Address = " + senderAddress.getHostAddress());
		System.out.println("Sender Port    = " + senderPort);
		
		mySocket.close();
	}

}

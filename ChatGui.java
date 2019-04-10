import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGui extends JFrame {
	
	private JPanel portPanel;
	private JPanel ipPanel;
	private JPanel buttonPanel;
	private JTextField messageField;
	private JButton sendButton;
	//static JButton exit;
	//static JButton send;
	public static Socket mySocket;
	static boolean sendEnabled;
	private InetAddress ipAddress;
	private JTextArea messageArea;
	private int port;
	
	public ChatGui (Socket socket, int port, String ip) {
		this.port = port;
		
		try {
			this.ipAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.exit(-1);
		}
		
		setTitle(" IP address: " + ip + ", Port number: " + port);
		setSize(500, 400);
		
		portPanel = new JPanel();
		portPanel.setLayout(new BorderLayout());
		
		getContentPane().add(portPanel);
		
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		portPanel.add(messageArea);
		
		ipPanel = new JPanel(new BorderLayout());
		
		portPanel.add(ipPanel, BorderLayout.SOUTH);
		
		messageField = new JTextField();
	    ipPanel.add(messageField);
		
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(Color.BLUE);
		portPanel.add(buttonPanel, BorderLayout.EAST);
		
		sendButton = new JButton(" Send ");
		buttonPanel.add(sendButton);
		
		sendButton.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = messageField.getText();
				
				if (!message.isEmpty()) {
					messageArea.append(" Artjola : " + message + "\n");
					messageField.setText("");
					socket.send(message, ipAddress, port);
				}
				
			}
			
		});
		
		
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	public JTextArea getTextArea () {
		return this.messageArea;
	}

	public void append(String string) {
		// TODO Auto-generated method stub
		
	}
}

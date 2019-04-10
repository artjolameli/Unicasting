import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author artjolameli
 *
 */  

public class MessageGui extends JFrame implements ActionListener {

	private static Socket mySocket;
	private JLabel ipAddrLabel;
	private JLabel portLabel;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JPanel Ip;
	private JPanel portPanel;
	private JButton startButton;

	private static HashMap<String, ChatGui> hm = new HashMap<>();
	
	/**
	 * 
	 */
	public MessageGui() {

		final boolean shouldFill = true;
		final boolean shouldWeightX = true;
		
		mySocket = new Socket(64000, Socket.SocketType.NoBroadcast);
		setTitle(" Frame");
		setLayout(new GridBagLayout());
		setSize(500, 430);

		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			// natural height, maximum width
			c.fill = GridBagConstraints.HORIZONTAL;
		}

		if (shouldWeightX) {
			c.weightx = 1;
			}
	
		ipAddrLabel = new JLabel(" IP Address: ");
		ipTextField = new JTextField(10);

		Ip = new JPanel();
		Ip.add(ipAddrLabel);
		Ip.add(ipTextField);

		c.gridx = 0;
		c.gridy = 0;
		// c.fill = GridBagConstraints.HORIZONTAL; // horizontal IP Address Label
		// c.weightx = 1;
		add(Ip, c);

		portLabel = new JLabel(" Port Number: ");
		portTextField = new JTextField(10);

		portPanel = new JPanel();
		portPanel.add(portLabel);
		portPanel.add(portTextField);

		c.gridy = 1;

		add(portPanel, c);

		startButton = new JButton(" Send  ");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipTextField.getText();
				String portString = portTextField.getText();
				String key = ip + ":" + portString;

				if (!ip.isEmpty() && !portString.isEmpty() && !hm.containsKey(key)) {
					ChatGui cwindow = new ChatGui(mySocket, Integer.parseInt(portString), ip);

					ipTextField.setText("");
					portTextField.setText("");
					hm.put(key, cwindow);

				}

			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10, 0, 0, 0);
	
		add(startButton, c);
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 2;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public void recievePackets() {
		DatagramPacket inPacket = null;

		do {
			try {
				try {
					inPacket = mySocket.receive();

					if (inPacket != null) {
						String chatmessage = new String(inPacket.getData());
						String ip = inPacket.getAddress().getHostAddress();
						int port = inPacket.getPort();
						String key = ip + " : " + port;

						ChatGui cwindow = null;

						if (hm.containsKey(key)) {
							cwindow = hm.get(key);
							cwindow.append(" Receiver: " + chatmessage + "\n");
							cwindow.setVisible(true);
						} else {
							cwindow = new ChatGui(mySocket, port, ip);
							hm.put(key, cwindow);
							cwindow.append(" Receiver: " + chatmessage + "\n");
							cwindow.setVisible(true);
						}
					}
				} catch (NullPointerException npe) {
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);
	}

	public void actionPerformed(ActionEvent e) {

	}
}

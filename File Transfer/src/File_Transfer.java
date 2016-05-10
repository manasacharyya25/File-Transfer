/*** IMPORTS ***/

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/*** PUBLIC CLASS File_Transfer ***/
public class File_Transfer {

	/*** GUI VARIABLES ***/
	private JFrame frame;
	private JTextField ipField;
	private JTextField filesField;
	private JTextField proxyField;
	private JTextField portField;
	private JTextField nameField;
	private JTextArea receiveStatus;
	
	
	/*** DATA TYPE VARIBALES ***/
	private String username_S;
	private String proxy_S;
	private String port_S;
	private String ip;
	private File[] selectedFiles;
	private static String dir="C:\\File Transfer\\";

	/*** CONNECTION VARIABLES ***/
	private Socket s=null;
	private ServerSocket ss=null;
	private DataInputStream din=null;
	private DataOutputStream dout=null;
	private FileInputStream fis=null;
	private FileOutputStream fout=null;
	private JTextField dirField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File f = new File(dir);
					if(!f.exists()){
						f.mkdirs();
					}
					File_Transfer window = new File_Transfer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/*** Appear FileChooser ***/
	private void AppearFileChooser(ActionEvent e){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		int returnValue = fileChooser.showOpenDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION)
	    {
	        selectedFiles = fileChooser.getSelectedFiles();
	        filesField.setText(selectedFiles.length+" Files Selected");
	    }
	}

	/*** Select Download Directory ***/
	private void SelectDirectory(ActionEvent e){
		JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = directoryChooser.showSaveDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION){
			dir = directoryChooser.getSelectedFile().toString()+"\\";
		}
		
	}
	
	
	/**
	 * Create the application.
	 */
	public File_Transfer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("File Transfer");
		frame.setBounds(100, 100, 300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

		/*** MAIN PANEL "cards" THAT ALLOWS ALL OTHER PANELS ***/
		JPanel cards = new JPanel(new CardLayout());
		cards.setBounds(0, 0, 284, 462);
		frame.getContentPane().add(cards);
		
		
		
		JPanel Send = new JPanel();
		Send.setBackground(Color.WHITE);
		cards.add(Send, "1");			// ADDING A PANEL TO "cards" PANEL
		Send.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 284, 21);
		Send.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewConnection = new JMenuItem("Send Files");
		mnFile.add(mntmNewConnection);
		
		JMenuItem mntmReceiveFiles = new JMenuItem("Receive Files");
		mnFile.add(mntmReceiveFiles);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnFile.add(mntmSettings);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JLabel ipLabel = new JLabel("IP Address");
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		ipLabel.setBounds(23, 61, 96, 29);
		Send.add(ipLabel);
		
		ipField = new JTextField();
		ipField.setBounds(129, 68, 145, 20);
		Send.add(ipField);
		ipField.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		connectButton.setBounds(85, 117, 105, 29);
		Send.add(connectButton);
		
		JLabel connectionLabel = new JLabel("Disconnected");
		connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		connectionLabel.setForeground(Color.LIGHT_GRAY);
		connectionLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		connectionLabel.setBounds(23, 169, 228, 44);
		Send.add(connectionLabel);
		
		JPanel fileChooserPanel = new JPanel();
		fileChooserPanel.setBounds(10, 224, 264, 227);
		Send.add(fileChooserPanel);
		fileChooserPanel.setLayout(null);
		
		filesField = new JTextField();
		filesField.setBounds(28, 30, 210, 20);
		fileChooserPanel.add(filesField);
		filesField.setColumns(10);
		
		JButton chooseFilesButton = new JButton("Choose Files");
		chooseFilesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppearFileChooser(e);
			}
		});
		chooseFilesButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		chooseFilesButton.setBounds(52, 71, 156, 32);
		fileChooserPanel.add(chooseFilesButton);
		
		JButton btnSendFiles = new JButton("Send Files");
		btnSendFiles.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		btnSendFiles.setBounds(65, 132, 129, 32);
		fileChooserPanel.add(btnSendFiles);
		
		JLabel resultLabel = new JLabel("");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		resultLabel.setBounds(28, 188, 210, 28);
		fileChooserPanel.add(resultLabel);
		
		JPanel Receive = new JPanel();
		cards.add(Receive, "3");
		Receive.setLayout(null);
		
		JButton listenButton = new JButton("Start Listening");
		listenButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		listenButton.setBounds(66, 42, 154, 60);
		Receive.add(listenButton);
		
		receiveStatus = new JTextArea();
		receiveStatus.setEditable(false);
		receiveStatus.setBounds(23, 137, 236, 292);
		Receive.add(receiveStatus);
		
		JMenuBar menuBar_2 = new JMenuBar();
		menuBar_2.setBounds(0, 0, 284, 21);
		Receive.add(menuBar_2);
		
		JMenu mnFile_2 = new JMenu("File");
		menuBar_2.add(mnFile_2);
		
		JMenuItem mntmSendFiles = new JMenuItem("Send Files");
		mnFile_2.add(mntmSendFiles);
		
		JMenuItem mntmReceiveFiles_1 = new JMenuItem("Receive Files");
		mnFile_2.add(mntmReceiveFiles_1);
		
		JMenuItem mntmSettings_2 = new JMenuItem("Settings");
		mnFile_2.add(mntmSettings_2);
		
		JMenuItem mntmExit_2 = new JMenuItem("Exit");
		mntmExit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile_2.add(mntmExit_2);
		
		JMenu mnHelp_2 = new JMenu("Help");
		menuBar_2.add(mnHelp_2);
		
		JPanel Settings = new JPanel();
		cards.add(Settings, "2");
		Settings.setLayout(null);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 284, 21);
		Settings.add(menuBar_1);
		
		JMenu mnFile_1 = new JMenu("File");
		menuBar_1.add(mnFile_1);
		
		JMenuItem mntmNewConnection_1 = new JMenuItem("Send Files");
		mnFile_1.add(mntmNewConnection_1);
		
		JMenuItem mntmReceiveFiles_2 = new JMenuItem("Receive Files");
		mnFile_1.add(mntmReceiveFiles_2);
		
		JMenuItem mntmSettings_1 = new JMenuItem("Settings");
		mnFile_1.add(mntmSettings_1);
		
		JMenuItem mntmExit_1 = new JMenuItem("Exit");
		mntmExit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile_1.add(mntmExit_1);
		
		JMenu mnHelp_1 = new JMenu("Help");
		menuBar_1.add(mnHelp_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 284, 250);
		Settings.add(panel);
		panel.setLayout(null);
		
		proxyField = new JTextField();
		proxyField.setText("Proxy");
		proxyField.setEnabled(false);
		proxyField.setBounds(10, 175, 250, 20);
		panel.add(proxyField);
		proxyField.setColumns(10);
		
		portField = new JTextField();
		portField.setText("Port");
		portField.setEnabled(false);
		portField.setBounds(10, 219, 250, 20);
		panel.add(portField);
		portField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setText("Username");
		nameField.setEnabled(false);
		nameField.setBounds(10, 59, 250, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		dirField = new JTextField();
		dirField.setEditable(false);
		dirField.setText("Download Directory: "+dir);
		dirField.setBounds(10, 103, 250, 20);
		panel.add(dirField);
		dirField.setColumns(10);
		
		JButton dirButton = new JButton("Change Download Directory");
		dirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectDirectory(e);
				dirField.setText("Download Directory: "+dir);
			}
		});
		dirButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 13));
		dirButton.setBounds(20, 134, 227, 23);
		dirButton.setEnabled(false);
		panel.add(dirButton);
		
		JButton changeButton = new JButton("Change");
		changeButton.setBounds(20, 310, 89, 23);
		Settings.add(changeButton);
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setBounds(10, 261, 250, 30);
		Settings.add(messageLabel);
		messageLabel.setForeground(Color.GRAY);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(171, 310, 89, 23);
		Settings.add(saveButton);
		
		
		/*** Event Handling ***/
		
		/**CardLayout**/
		CardLayout cl = (CardLayout) cards.getLayout();
		
		/**Connect Button**/
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ip = ipField.getText();
				if(!ip.isEmpty()){
					try{
						s = new Socket(ip,6666);
						  /***************************/
					     /*** PASSWORD TO CONNECT ***/
					    /***************************/
						connectionLabel.setText("Connected to "+ip);
						connectionLabel.setForeground(Color.black);
						Component comp[] = fileChooserPanel.getComponents();
						for(Component x:comp){
							x.setEnabled(true);}
					}
					catch(Exception e1){
						System.out.println(e1.getMessage());
						connectionLabel.setText("Failed to connect to "+ip);
					}
				}
				else{
					connectionLabel.setText("Fill in an IP address");
				}
			}
		});
		
		/*** Send Button ***/
		btnSendFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dout = new DataOutputStream(s.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				int count = 0;
				int outputLength = selectedFiles.length;	
				try{
					
					dout.writeInt(outputLength);
					dout.flush();
					
					while(count<selectedFiles.length){
						File file = selectedFiles[count];
						byte[] fileByte = new byte[(int) file.length()];
						
						String fName = file.getName();
						dout.writeUTF(fName);
						dout.flush();
						
						long fSize = file.length();
						dout.writeLong(fSize);
						dout.flush();
						
						fis = new FileInputStream(file);
						
	
						int bytesRead;
						while((bytesRead=fis.read(fileByte))>0){
							dout.write(fileByte,0,bytesRead);
							dout.flush();
						}
						
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
				try{
					dout.close();
					fis.close();
					s.close();
				}catch(Exception e){
					System.out.print("error");
				}
			}
		});
		
		/*** FileChooserPanel Disabled***/
		connectionLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if(connectionLabel.getText()!=("Connected to"+ip)){
					Component comp[] = fileChooserPanel.getComponents();
					for(Component x:comp){
						x.setEnabled(false);
					}
					
				}
			}
		});
		
		/**Settings MenuItem**/
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards,"2");
			}
		});
		
		mntmNewConnection_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards, "1");
			}
		});
		
		/**Change Settings**/
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameField.setEnabled(true);
				dirField.setEnabled(true);
				dirButton.setEnabled(true);
				proxyField.setEnabled(true);
				portField.setEnabled(true);
			}
		});
		
		/**Save Settings**/
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username_S=nameField.getText();
				proxy_S=proxyField.getText();
				port_S=portField.getText();
				
				if(!username_S.isEmpty() & !dir.isEmpty()){
					nameField.setEnabled(false);
					dirField.setEnabled(false);
					dirButton.setEnabled(false);
					proxyField.setEnabled(false);
					portField.setEnabled(false);
					messageLabel.setText("Settings Saved");
				}
				else{
					messageLabel.setText("Username cannot be empty");
				}
				
				
			}
		});

		/** Listen Button **/
		listenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					ss=new ServerSocket(6666);
					s=ss.accept();
					receiveStatus.setText("Connected to :"+s.getInetAddress());
					
					din = new DataInputStream(s.getInputStream());
					
					int inputLength = din.readInt();
					
					receiveStatus.append("\n"+inputLength+" incoming files");
					
					for(int i=1;i<=inputLength;i++){
						receiveStatus.append("\nReceiving "+i+" out of "+inputLength+" files");
						String fName=din.readUTF();
						
						fout = new FileOutputStream(dir+fName);
						
						long fSize = din.readLong();
						
						byte buffer[]=new byte[16*1024];
						int bytesRead;
						
						while(fSize>0){
							bytesRead=din.read(buffer,0,(int)Math.min(buffer.length,fSize));
							fSize-=bytesRead;
						}
						receiveStatus.append("\n"+fName+" received");
					}
					fout.close();
					din.close();
					s.close();
					ss.close();
					
				}catch(Exception e){
					
				}
			}
		});
		
		/** Receive Files MenuItem **/
		mntmReceiveFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards, "3");
			}
		});
		
		mntmSendFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards,"1");
			}
		});
		
		mntmSettings_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards, "2");
			}
		});
		
		mntmReceiveFiles_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards,"3");
			}
		});
		
		
		
		
		
	}
}

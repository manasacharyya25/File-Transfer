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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPasswordField;

import org.omg.CORBA.portable.OutputStream;


public class File_Transfer {

	private JFrame frame;
	private JTextField ipField;
	private JTextField filesField;
	private JTextField proxyField;
	private JTextField portField;
	private JTextField nameField;
	
	private String username_S;
	private String password_S="abc123";
	private String proxy_S;
	private String port_S;
	private String ip;
	private JPasswordField passField;
	private File[] selectedFiles;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		
		JPanel cards = new JPanel(new CardLayout());
		cards.setBounds(0, 0, 284, 462);
		frame.getContentPane().add(cards);
		
		
		
		JPanel Connect = new JPanel();
		Connect.setBackground(Color.WHITE);
		cards.add(Connect, "1");
		Connect.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 284, 21);
		Connect.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewConnection = new JMenuItem("New Connection");
		mnFile.add(mntmNewConnection);
		
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
		Connect.add(ipLabel);
		
		ipField = new JTextField();
		ipField.setBounds(129, 68, 145, 20);
		Connect.add(ipField);
		ipField.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		connectButton.setBounds(85, 117, 105, 29);
		Connect.add(connectButton);
		
		JLabel connectionLabel = new JLabel("Disconnected");
		connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		connectionLabel.setForeground(Color.LIGHT_GRAY);
		connectionLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		connectionLabel.setBounds(23, 169, 228, 44);
		Connect.add(connectionLabel);
		
		JPanel fileChooserPanel = new JPanel();
		fileChooserPanel.setBounds(10, 224, 264, 227);
		Connect.add(fileChooserPanel);
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
		
		JPanel Settings = new JPanel();
		cards.add(Settings, "2");
		Settings.setLayout(null);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar_1.setBounds(0, 0, 284, 21);
		Settings.add(menuBar_1);
		
		JMenu mnFile_1 = new JMenu("File");
		menuBar_1.add(mnFile_1);
		
		JMenuItem mntmNewConnection_1 = new JMenuItem("New Connection");
		mnFile_1.add(mntmNewConnection_1);
		
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
		
		JLabel username = new JLabel("Username");
		username.setBounds(10, 54, 77, 24);
		panel.add(username);
		username.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		
		JLabel password = new JLabel("Password");
		password.setBounds(10, 97, 74, 24);
		panel.add(password);
		password.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		
		JLabel proxy = new JLabel("Proxy");
		proxy.setBounds(10, 142, 44, 24);
		panel.add(proxy);
		proxy.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		
		JLabel port = new JLabel("Port");
		port.setBounds(10, 187, 31, 24);
		panel.add(port);
		port.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
		
		proxyField = new JTextField();
		proxyField.setEnabled(false);
		proxyField.setBounds(131, 147, 129, 20);
		panel.add(proxyField);
		proxyField.setColumns(10);
		
		portField = new JTextField();
		portField.setEnabled(false);
		portField.setBounds(131, 192, 129, 20);
		panel.add(portField);
		portField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setText("User");
		nameField.setEnabled(false);
		nameField.setBounds(131, 59, 129, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		passField = new JPasswordField();
		passField.setToolTipText("");
		passField.setBounds(131, 102, 129, 20);
		passField.setEnabled(false);
		panel.add(passField);
		
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
				connectionLabel.setText("Connected to "+ip);
				connectionLabel.setForeground(Color.black);
				Component comp[] = fileChooserPanel.getComponents();
				for(Component x:comp){
					x.setEnabled(true);}
				}
			}
		});
		
		/*** Send Button ***/
		btnSendFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Socket s=null;
				DataOutputStream dout=null;
				FileInputStream fis=null;
				
				try {
					s = new Socket(ip,6666);
				} catch (IOException e) {
					connectionLabel.setText("Failed to Connect");
				}
				try {
					dout = new DataOutputStream(s.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				int count = 0;
				
				try{
					while(count<selectedFiles.length){
						File file = selectedFiles[count];
						byte[] fileByte = new byte[(int) file.length()];
						
						String fName = file.getName();
						dout.writeUTF(fName);
						
						fis = new FileInputStream(file);
	
						int bytesRead;
						while((bytesRead=fis.read(fileByte))>0){
							dout.write(fileByte,0,bytesRead);
						}
						
						System.out.println(count++);
					}
				}catch(Exception e){
					e.getMessage();
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
				if(connectionLabel.getText()=="Disconnected"){
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
				passField.setEnabled(true);
				proxyField.setEnabled(true);
				portField.setEnabled(true);
			}
		});
		
		/**Save Settings**/
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username_S=nameField.getText();
				String pass_S=new String(passField.getPassword()); /***********Password to String ***************/
				if(!pass_S.isEmpty()){
					password_S=pass_S;
				}
				proxy_S=proxyField.getText();
				port_S=portField.getText();
				
				if(!username_S.isEmpty() & !password_S.isEmpty()){
					nameField.setEnabled(false);
					passField.setEnabled(false);
					proxyField.setEnabled(false);
					portField.setEnabled(false);
					messageLabel.setText("Settings Saved");
					
					System.out.println(password_S);
				}
				else{
					messageLabel.setText("Username cannot be empty");
				}
				
				
			}
		});

		
		
	}
}

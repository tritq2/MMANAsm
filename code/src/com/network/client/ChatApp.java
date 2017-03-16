package com.network.client;

import java.awt.EventQueue;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

import com.networking.data.DataFile;
import com.networking.login.Login;
import com.networking.tags.DeCode;
import com.networking.tags.enCode;

import cryptography.AES;
import cryptography.Convert;
import cryptography.DES;

import com.networking.tags.Tags;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Label;
import java.awt.TextArea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.security.Key;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ChatApp {

	private static String URL_DIR = System.getProperty("user.dir");
	private static String TEMP = "/temp/";

	private ChatRoom chat;
	private Socket socketChat;
	private String nameUser = "", nameGuest = "", nameFile = "";
	Key guessKey;
	private JFrame frame;
	private JTextField textName;
	private JPanel panelMessage;
	private Label textState, lblReceive;
	private TextArea textDisPlayChat;
	private JButton btnDisConnect, btnSend, btnChoose, btnUpLoad;
	public boolean isStop = false, isSendFile = false, isReceiveFile = false;
	private JProgressBar progressSendFile;
	private JTextField textPath;
	private int portServer = 0;
	private JTextField textSend;
	private JPanel panelFile;
	public DES des;
	public AES aes;
	public Key keyForDecryptReceiveFile;
	public IvParameterSpec ivForDecrytReceiveFile;

	public ChatApp(String user, String guest, Socket socket, int port) {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "INSERT YOUR LICENSE KEY HERE", "my company");
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatApp window = new ChatApp(nameUser, nameGuest, socketChat, portServer, 0);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "INSERT YOUR LICENSE KEY HERE", "my company");
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatApp window = new ChatApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateChat(String msg) {
		textDisPlayChat.append(msg + "\n");
	}

	public ChatApp() {
		initialize();
	}

	public ChatApp(String user, String guest, Socket socket, int port, int a) throws Exception {
		nameUser = user;
		nameGuest = guest;
		socketChat = socket;
		this.portServer = port;
		des = new DES();
		aes = new AES();
		
		//Get publickey of guess
		System .out.println("size of peer in file Chatapp: " + Peer.peer.size() );
		System.out.println("namequest: " + nameGuest);
		for (int i = 0; i< Peer.peer.size() ; i++){
			System.out.println("name loop: "+ Peer.peer.get(i).getName());
			if(Peer.peer.get(i).getName().equals(nameGuest)){
				System.out.println("found namequest: " + nameGuest);
				guessKey = Peer.peer.get(i).getPublicKey();
				if (guessKey != null)
					System.out.println("guesskey # null");
				break;
			}
		}
		initialize();
		chat = new ChatRoom(socketChat, nameUser, nameGuest);
		chat.start();
	}

	private void initialize() {
		File fileTemp = new File(URL_DIR + "/temp");
		if (!fileTemp.exists()) {
			fileTemp.mkdirs();
		}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 606, 584);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JLabel lblClientIP = new JLabel("Member In Group Chat:");
		lblClientIP.setBounds(6, 12, 155, 16);
		frame.getContentPane().add(lblClientIP);

		textName = new JTextField(nameUser);
		textName.setEditable(false);
		textName.setBounds(171, 6, 280, 28);
		frame.getContentPane().add(textName);
		textName.setText(nameUser + " and " + nameGuest);
		textName.setColumns(10);

		textDisPlayChat = new TextArea();
		textDisPlayChat.setEditable(false);
		textDisPlayChat.setBounds(6, 40, 568, 317);
		frame.getContentPane().add(textDisPlayChat);

		panelFile = new JPanel();
		panelFile.setBounds(6, 439, 568, 80);
		panelFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "File"));
		frame.getContentPane().add(panelFile);
		panelFile.setLayout(null);

		textPath = new JTextField("");
		textPath.setBounds(65, 21, 388, 25);
		panelFile.add(textPath);
		textPath.setEditable(false);
		textPath.setColumns(10);

		btnChoose = new JButton(new ImageIcon(this.getClass().getResource("/Attach_file.png")));
		btnChoose.setBounds(463, 21, 25, 25);
		panelFile.add(btnChoose);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					isSendFile = true;
					String path_send = fileChooser.getSelectedFile().getAbsolutePath();
					nameFile = fileChooser.getSelectedFile().getName();
					textPath.setText(path_send);
				}
			}
		});
		btnChoose.setBorder(BorderFactory.createEmptyBorder());
		btnChoose.setContentAreaFilled(false);

		btnUpLoad = new JButton(new ImageIcon(this.getClass().getResource("/Upload_file.png")));
		btnUpLoad.setBounds(498, 21, 25, 25);
		panelFile.add(btnUpLoad);

		btnUpLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isSendFile) {
					try {
						chat.sendMessage(enCode.sendFile(nameFile));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnUpLoad.setContentAreaFilled(false);
		btnUpLoad.setBorder(BorderFactory.createEmptyBorder());

		Label label = new Label("Path : ");
		label.setBounds(10, 21, 49, 22);
		panelFile.add(label);
		
		JRadioButton rdbtnAlg = new JRadioButton("DES");
		rdbtnAlg.setSelected(true);
		rdbtnAlg.setBounds(80, 50, 109, 23);
		panelFile.add(rdbtnAlg);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("AES");
		rdbtnNewRadioButton_1.setBounds(191, 50, 109, 23);
		panelFile.add(rdbtnNewRadioButton_1);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// code encrypt here:
				String path = textPath.getText();
				//File fileData = new File(path);
				if(path == null ||path.equals("")){
					Tags.show(frame,"You haven't choosen file to encrypt yet!", false);
					return;
				}
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					String pathSaveEncryptFile = fileChooser.getSelectedFile().getAbsolutePath();
					String key_str = "12345678";
					DES des = null;
					try {
						des = new DES();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Key key = Convert.Bytes2Key(key_str.getBytes(), "DES");

					//read file to bytes - > InputFiledata
					Path pathObj = Paths.get(path);
					byte[] InputFiledata = null;
					try {
						InputFiledata = Files.readAllBytes(pathObj);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//get output file name:
					String encryptFileName = pathSaveEncryptFile+'\\' + pathObj.getFileName().toString() + ".encrypted";
					// create new file to flush encrypted bytes data
					File encryptedFile = new File(encryptFileName);
					FileOutputStream outputStream = null;
					try {
						outputStream = new FileOutputStream(encryptedFile);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// get data encypted
					byte[] outputBytes = null;
					try {
						outputBytes = des.encrypt(InputFiledata, key);
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// write output file
					try {
						outputStream.write(outputBytes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// System.console().printf(pathSaveEncryptFile);
					return;
				}
			}
		});
		btnEncrypt.setBounds(397, 49, 72, 25);
		panelFile.add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(473, 50, 72, 23);
		panelFile.add(btnDecrypt);

		panelMessage = new JPanel();
		panelMessage.setBounds(6, 363, 568, 71);
		panelMessage.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Message"));
		frame.getContentPane().add(panelMessage);
		panelMessage.setLayout(null);

		textSend = new JTextField("");
		textSend.setBounds(10, 21, 479, 39);
		panelMessage.add(textSend);
		textSend.setColumns(10);

		btnSend = new JButton("SEND");
		btnSend.setBounds(499, 29, 59, 23);
		panelMessage.add(btnSend);
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (isStop) {
					updateChat("[ME]	:" + textSend.getText().toString());
					textSend.setText("");
					return;
				}
				String msg = textSend.getText();
				if (msg.equals("")) {
					return;
				}
				try {
					String algorithm = "DES";
					Key key = des.generateKey();
					String msg_cipher = Convert.Bytes2String(des.encrypt(msg.getBytes(),key ));
					IvParameterSpec iv = des.getIv();
					System.out.println("File ChatApp. Before encrypt key");
					if (guessKey == null)
						System.out.println("guesskey = null");
					String encryptedkey = Convert.Bytes2String(Login.rsa.encrypt(Convert.Key2Bytes(key), guessKey));
					System.out.println("File ChatApp. After encryptkey");
					chat.sendMessage(enCode.sendMessage(msg_cipher, algorithm, encryptedkey, Convert.Iv2String(iv)));
					System.out.println("File ChatApp. After send mm");
					updateChat("[ME]	:" + msg);
					textSend.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		textSend.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = textSend.getText();
					if (isStop) {
						updateChat("[ME]	:" + textSend.getText().toString());
						textSend.setText("");
						return;
					}
					if (msg.equals("")) {
						textSend.setText("");
						textSend.setCaretPosition(0);
						return;
					}
					try {
						String algorithm = "DES";
						Key key = des.generateKey();
						String msg_cipher = Convert.Bytes2String(des.encrypt(msg.getBytes(),key ));
						IvParameterSpec iv = des.getIv();
						System.out.println("File ChatApp. Before encrypt key");
						if (guessKey == null)
							System.out.println("guesskey = null");
						String encryptedkey = Convert.Bytes2String(Login.rsa.encrypt(Convert.Key2Bytes(key), guessKey));
						System.out.println("File ChatApp. After encryptkey");
						chat.sendMessage(enCode.sendMessage(msg_cipher, algorithm, encryptedkey, Convert.Iv2String(iv)));
						updateChat("[ME]	:" + msg);
						textSend.setText("");
						textSend.setCaretPosition(0);
					} catch (Exception e) {
						textSend.setText("");
						textSend.setCaretPosition(0);
					}
				}
			}
		});

		btnDisConnect = new JButton("CLOSE");
		btnDisConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Tags.show(frame, "Do you want close chat with " + nameGuest, true);
				if (result == 0) {
					try {
						isStop = true;
						frame.dispose();
						chat.sendMessage(Tags.CHAT_CLOSE_TAG);
						chat.stopChat();
						System.gc();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnDisConnect.setBounds(461, 6, 113, 29);
		frame.getContentPane().add(btnDisConnect);

		progressSendFile = new JProgressBar(0, 100);
		progressSendFile.setBounds(93, 530, 388, 14);
		progressSendFile.setStringPainted(true);
		frame.getContentPane().add(progressSendFile);
		progressSendFile.setVisible(false);

		textState = new Label("");
		textState.setBounds(6, 502, 81, 22);
		textState.setVisible(false);
		frame.getContentPane().add(textState);

		lblReceive = new Label("Receiving ...");
		lblReceive.setBounds(487, 530, 83, 14);
		lblReceive.setVisible(false);
		frame.getContentPane().add(lblReceive);

	}

	public class ChatRoom extends Thread {

		private Socket connect;
		private ObjectOutputStream outPeer;
		private ObjectInputStream inPeer;
		private boolean continueSendFile = true, finishReceive = false;
		private int sizeOfSend = 0, sizeOfData = 0, sizeFile = 0, sizeReceive = 0;
		private String nameFileReceive = "";
		private InputStream inFileSend;
		private DataFile dataFile;
		

		public ChatRoom(Socket connection, String name, String guest) throws Exception {
			connect = new Socket();
			connect = connection;
			nameGuest = guest;
			
		}

		@Override
		public void run() {
			super.run();
			OutputStream out = null;
			while (!isStop) {
				try {
					inPeer = new ObjectInputStream(connect.getInputStream());
					Object obj = inPeer.readObject();
					if (obj instanceof String) {
						String msgObj = obj.toString();
						if (msgObj.equals(Tags.CHAT_CLOSE_TAG)) {
							isStop = true;
							Tags.show(frame, nameGuest + " may be close chat with you!", false);
							connect.close();
							break;
						}
						if (DeCode.checkFile(msgObj)) { // receive file
							isReceiveFile = true;
							nameFileReceive = msgObj.substring(10, msgObj.length() - 11);
							int result = Tags.show(frame, nameGuest + " send file " + nameFileReceive + " for you",
									true);
							if (result == 0) {
								File fileReceive = new File(URL_DIR + TEMP + "/" + nameFileReceive);
								
								//System.out.println("đồng ý tải về"+URL_DIR + TEMP + "/" + nameFileReceive);
								
								if (!fileReceive.exists()) {
									fileReceive.createNewFile();
								}

								String msg = Tags.FILE_REQ_ACK_OPEN_TAG + Integer.toBinaryString(portServer)
										+ Tags.FILE_REQ_ACK_CLOSE_TAG;
								sendMessage(msg);
							} else {
								sendMessage(Tags.FILE_REQ_NOACK_TAG);
							}
						} else if (DeCode.checkFeedBack(msgObj)) {
							btnChoose.setEnabled(false);
							btnUpLoad.setEnabled(false);
							new Thread(new Runnable() {
								public void run() {
									try {
										sendMessage(Tags.FILE_DATA_BEGIN_TAG);
										updateChat("you are sending file : " + nameFile);
										isSendFile = false;
										sendFile(textPath.getText());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).start();
						} else if (msgObj.equals(Tags.FILE_REQ_NOACK_TAG)) { //cancel send file
							Tags.show(frame, nameGuest + " wantn't receive file", false);
							
							
						} else if (msgObj.equals(Tags.FILE_DATA_BEGIN_TAG)) { //receiving file
							finishReceive = false;
							lblReceive.setVisible(true);
							out = new FileOutputStream(URL_DIR + TEMP + nameFileReceive);
							
							
						} else if (msgObj.equals(Tags.FILE_DATA_END_TAG)) { // received file
							updateChat(
									"You receive file " + nameFileReceive 
									+ " with total: " + sizeReceive + "KB");
							sizeReceive = 0;
							out.flush();
							out.close();
							lblReceive.setVisible(false);
							new Thread(new Runnable() {
								@Override
								public void run() {
									showSaveFile();
								}
							}).start();
							finishReceive = true;
						}
						else {
							ArrayList<String> message = DeCode.getMessage(msgObj);
							System.out.println("receive raw mesage: " + msgObj);
							if (message ==null)
								System.out.println("massage is null!!!");
							String ctext = message.get(0);
							System.out.println("ctext: " + ctext);
							String algorithm = message.get(1);
							System.out.println("algo: " + algorithm);
							System.out.println("size message array: " + message.size());
							System.out.println("message.get(2): " + message.get(2));
							String encryptedkey = message.get(2);
							System.out.println("encryptedKey: " + encryptedkey);
							String key = Convert.Bytes2String(Login.rsa.decrypt(Convert.StringToBytes(encryptedkey), 
													Login.keyRSA.getPrivate()));
							System.out.println("key: " + key);
							String iv = message.get(3);
							System.out.println("iv: " + iv);
							String ptext = new String(des.decrypt(Convert.StringToBytes(ctext), 
									Convert.String2Key(key, "DES", true), Convert.String2Iv(iv)));
							System.out.println("ptext: " + ptext);
							updateChat("[" + nameGuest + "]	:" + ptext);
						}
					} else if (obj instanceof DataFile) {
						DataFile data = (DataFile) obj;
						byte[] encrypedmsg = data.data;
						String key_str_encrypt = data.encryptedkey;
						String iv_str = data.iv;
						String key_str_decrypted = Convert.Bytes2String(Login.rsa.decrypt(Convert.StringToBytes(key_str_encrypt), 
								Login.keyRSA.getPrivate()));
						
						byte[] decryptmsg = aes.decrypt(encrypedmsg, 
								Convert.String2Key(key_str_decrypted, "AES", true), Convert.String2Iv(iv_str));
						
						++sizeReceive;
						out.write(decryptmsg);
					}
				} catch (Exception e) {
					File fileTemp = new File(URL_DIR + TEMP + nameFileReceive);
					if (fileTemp.exists() && !finishReceive) {
						fileTemp.delete();
					}
				}
			}
		}

		private void getData(String path) throws Exception {
			File fileData = new File(path);
			if (fileData.exists()) {
				
				sizeOfSend = 0;
				sizeFile = (int) fileData.length();
				sizeOfData = sizeFile % (1024 *1024) == 0 ? (int) (fileData.length() / (1024 *1024))
						: (int) (fileData.length() / (1024 *1024)) + 1;
				System.out.println("size of data: " + sizeOfData);
				textState.setVisible(true);
				progressSendFile.setVisible(true);
				progressSendFile.setValue(0);
				System.out.println("path file: " + path);
				inFileSend = new FileInputStream(fileData);
				if (sizeFile < Tags.MAX_MSG_SIZE)
					dataFile = new DataFile(sizeFile);
				else 
					dataFile = new DataFile();
			}
		}

		public void sendFile(String path) throws Exception { // apply encrypt algorithms here!!
			getData(path);
			textState.setText("Sending ...");
			do {
				if (continueSendFile) {
					continueSendFile = false;
					//new Thread(new Runnable() {

					//	@Override
					//	public void run() {
							try {
								System.out.println("before read byte to dataFile");
								int sobyte = inFileSend.read(dataFile.data);
								System.out.println("after read byte to data file: " + dataFile.data.length);
								System.out.println("so byte:" + sobyte);
								String algorithm = "AES";
								
								Key key = aes.generateKey();
								System.out.println("generatekey success");
								byte[] msg_cipher = aes.encrypt(dataFile.data, key);
								System.out.println("encrypt sucess");
								IvParameterSpec iv = aes.getIv();
								System.out.println("get ic success");
								String encryptedkey = Convert.Bytes2String(Login.rsa.encrypt(Convert.Key2Bytes(key), guessKey));
								System.out.println("convert and encrypt ey success");
								DataFile encrypted_datafile = new DataFile(msg_cipher, encryptedkey, Convert.Iv2String(iv));
								System.out.println("new data file success");
								sendMessage(encrypted_datafile);
								System.out.println("send message success");
								sizeOfSend++;
								System.out.println("size of Send: " + sizeOfSend);
								
								if (sizeOfSend == 1 && sizeOfSend < sizeOfData -1)
									dataFile = new DataFile();
									
								if (sizeOfSend == sizeOfData - 1) {
									int size = sizeFile - sizeOfSend * 1024 *1024 ;
									dataFile = new DataFile(size);
								}
								System.out.println("truoc khi thanhg trnag thai");
								progressSendFile.setValue((int) (sizeOfSend * 100 / sizeOfData));
								System.out.println("sau khi thanhg trnag thai");
								if (sizeOfSend >= sizeOfData) {
									inFileSend.close();
									isSendFile = true;
									sendMessage(Tags.FILE_DATA_END_TAG);
									
									//System.out.println("ket thuc gui file");
									
									progressSendFile.setVisible(false);
									textState.setVisible(false);
									isSendFile = false;
									textPath.setText("");
									btnChoose.setEnabled(true);
									btnUpLoad.setEnabled(true);
									updateChat("... SUCCESSFUL");
									inFileSend.close();
								}
								continueSendFile = true;
								System.out.println("Chuan bi lap tiep");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
				//	}).start();
			
				
			} while (sizeOfSend < sizeOfData);
		}

		private void showSaveFile() {
			while (true) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showSaveDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = new File(
							fileChooser.getSelectedFile().getAbsolutePath()+"/"+nameFileReceive);
					if (!file.exists()) {
						try {
							file.createNewFile();
							Thread.sleep(1000);
							InputStream input = new FileInputStream(URL_DIR + TEMP + nameFileReceive);
							OutputStream output = new FileOutputStream(file.getAbsolutePath());
							copyFileReceive(input, output, URL_DIR + TEMP + nameFileReceive);
						} catch (Exception e) {
							Tags.show(frame, "Your file receive has error!", false);
						}
						break;
					} else {
						int resultContinue = Tags.show(
								frame, "File is exists. You want save file?", true);
						if (resultContinue == 0) {
							continue;
						} else {
							break;
						}
					}
				}
			}
		}

		public synchronized void sendMessage(Object obj) throws Exception {
			outPeer = new ObjectOutputStream(connect.getOutputStream());
			if (obj instanceof String) {
				String message = obj.toString();

				outPeer.writeObject(message);
				outPeer.flush();
				if (isReceiveFile) {
					isReceiveFile = false;
				}
			} else if (obj instanceof DataFile) {
				outPeer.writeObject(obj);
				outPeer.flush();
			}
		}

		public void stopChat() {
			try {
				connect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copyFileReceive(InputStream inputStr, OutputStream outputStr, String path) throws IOException {
		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = inputStr.read(buffer)) > 0) {
			outputStr.write(buffer, 0, lenght);
		}
		inputStr.close();
		outputStr.close();
		File fileTemp = new File(path);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}
	}
}

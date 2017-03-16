package com.networking.tags;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.networking.data.DataPeer;

//import com.networking.data.DataPeer;
import com.networking.tags.Tags;

import cryptography.Convert;

public class DeCode {

	private static Pattern users = Pattern.compile(Tags.SEND_REFRESH_OPEN_TAG + "(" + Tags.PEER_OPEN_TAG
			+ Tags.PEER_NAME_OPEN_TAG + ".+" + Tags.PEER_NAME_END_TAG + Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG
			+ Tags.PORT_OPEN_TAG + "[0-9]+" + Tags.PORT_END_TAG + Tags.PUBLIC_KEY_OPEN_TAG + ".*"
			+ Tags.PUBLIC_KEY_CLOSE_TAG + Tags.PEER_CLOSE_TAG + ")*" + Tags.SEND_REFRESH_CLOSE_TAG);

	public static Pattern signUp = Pattern.compile(Tags.SIGN_UP_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + ".*"
			+ Tags.USER_NAME_END_TAG + Tags.PASSWORD_OPEN_TAG + ".*" + Tags.PASSWORD_END_TAG + Tags.SIGN_UP_END_TAG);

	public static Pattern refresh = Pattern.compile(Tags.REFRESH);

	public static ArrayList<String> getSignUp(String msg) { // Lay thong tin
															// username va id
															// khi signup
		ArrayList<String> user = new ArrayList<String>();
		if (signUp.matcher(msg).matches()) {
			Pattern findName = Pattern.compile(Tags.USER_NAME_OPEN_TAG + "[^<>]*" + Tags.USER_NAME_END_TAG);
			Pattern findPass = Pattern.compile(Tags.PASSWORD_OPEN_TAG + "[^<>]*" + Tags.PASSWORD_END_TAG);

			Matcher find = findName.matcher(msg);
			if (find.find()) {
				String name = find.group(0);
				user.add(name.substring(Tags.USER_NAME_OPEN_TAG.length(),
						name.length() - Tags.USER_NAME_END_TAG.length()));

				find = findPass.matcher(msg);
				if (find.find()) {
					String port = find.group(0);
					user.add(port.substring(Tags.PASSWORD_OPEN_TAG.length(),
							port.length() - Tags.PASSWORD_END_TAG.length()));
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return user;
	}

	public static Pattern signUpSuccess = Pattern.compile(Tags.SIGN_UP_SUCCESS_OPEN_TAG + Tags.PORT_OPEN_TAG + ".*"
			+ Tags.PORT_END_TAG + Tags.SIGN_UP_SUCCESS_END_TAG);

	public static String getSignUpSuccess(String msg) { // lay thong tin cong
														// khi dang ky thanh
														// cong
		String portReturn = "";
		if (signUpSuccess.matcher(msg).matches()) {
			Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_END_TAG);

			Matcher find = findPort.matcher(msg);
			if (find.find()) {
				String port = find.group(0);
				portReturn = port.substring(Tags.PORT_OPEN_TAG.length(), port.length() - Tags.PORT_END_TAG.length());

			} else {
				return null;
			}
		} else {
			return null;
		}
		return portReturn;
	}

	private static Pattern signUpUnSuccess = Pattern.compile(Tags.SIGN_UP_UNSUCCESS);

	public static boolean checkSignUpUnSuccess(String msg) { // Khi dang ky
																// thanh cong se
																// tra ve true
																// va false
		if (signUpUnSuccess.matcher(msg).matches()) {
			return true;
		}
		return false;
	}

	public static Pattern logIn = Pattern.compile(Tags.LOG_IN_OPEN_TAG + Tags.USER_NAME_OPEN_TAG + ".*"
			+ Tags.USER_NAME_END_TAG + Tags.PASSWORD_OPEN_TAG + ".*" + Tags.PASSWORD_END_TAG + Tags.IP_OPEN_TAG + ".+"
			+ Tags.IP_END_TAG + Tags.PUBLIC_KEY_OPEN_TAG + ".*" + Tags.PUBLIC_KEY_CLOSE_TAG + Tags.LOG_IN_END_TAG);

	public static ArrayList<String> getLogIn(String msg) { // Lay thong tin ten
															// user , pass , ip
															// khi dang nhap

		System.out.println("message in GetLoginfunction  in Decode file: " + msg);
		ArrayList<String> user = new ArrayList<String>();
		if (logIn.matcher(msg).matches()) {
			Pattern findName = Pattern.compile(Tags.USER_NAME_OPEN_TAG + "[^<>]*" + Tags.USER_NAME_END_TAG);
			Pattern findPass = Pattern.compile(Tags.PASSWORD_OPEN_TAG + "[^<>]*" + Tags.PASSWORD_END_TAG);
			Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_END_TAG);
			Pattern findPublicKey = Pattern.compile(Tags.PUBLIC_KEY_OPEN_TAG + ".*" + Tags.PUBLIC_KEY_CLOSE_TAG);
			Matcher find = findName.matcher(msg);
			if (find.find()) {
				String name = find.group(0);
				user.add(name.substring(Tags.USER_NAME_OPEN_TAG.length(),
						name.length() - Tags.USER_NAME_END_TAG.length()));

				find = findPass.matcher(msg);
				if (find.find()) {
					String port = find.group(0);
					user.add(port.substring(Tags.PASSWORD_OPEN_TAG.length(),
							port.length() - Tags.PASSWORD_END_TAG.length()));

					find = findIp.matcher(msg);
					if (find.find()) {
						String ip = find.group(0);
						user.add(ip.substring(Tags.IP_OPEN_TAG.length(), ip.length() - Tags.IP_END_TAG.length()));

						find = findPublicKey.matcher(msg);
						if (find.find()) {
							System.out.println("match public key");
							String publickey_str = find.group(0);
							user.add(publickey_str.substring(Tags.PUBLIC_KEY_OPEN_TAG.length(),
									publickey_str.length() - Tags.PUBLIC_KEY_CLOSE_TAG.length()));
						} else {
							return null;
						}

					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return user;
	}

	public static ArrayList<DataPeer> updatePeerOnline(ArrayList<DataPeer> peerList, String msg) {
		Pattern userLogOut = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG);
		if (logOut.matcher(msg).matches()) {
			Matcher findState = userLogOut.matcher(msg);

			if (findState.find()) {
				String findPeer = findState.group(0);
				int size = peerList.size();
				String name = findPeer.substring(Tags.PEER_NAME_OPEN_TAG.length(),
						findPeer.length() - Tags.PEER_NAME_END_TAG.length());
				for (int i = 0; i < size; i++) {
					if (name.equals(peerList.get(i).getName())) {
						peerList.remove(i);
						break;
					}
				}
			}
		}
		return peerList;
	}

	public static Pattern logOut = Pattern.compile(
			Tags.LOG_OUT_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".*" + Tags.PEER_NAME_END_TAG + Tags.LOG_OUT_END_TAG);

	public static String getLogOut(String msg) { // Lay thong tin user va ip khi
													// dang xuat
		String user = "";
		if (logOut.matcher(msg).matches()) {
			Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG);
			Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_END_TAG);

			Matcher find = findName.matcher(msg);
			if (find.find()) {
				String name = find.group(0);
				user = (name.substring(Tags.PEER_NAME_OPEN_TAG.length(),
						name.length() - Tags.PEER_NAME_END_TAG.length()));
			} else {
				return null;
			}
		} else {
			return null;
		}
		return user;
	}

	public static Pattern requestChat = Pattern.compile(
			Tags.CHAT_REQ_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".+" + Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG);

	public static String getRequestChat(String msg) { // yeu cau chat tra ve ten
														// nguoi ket noi den
		String name = "";
		if (requestChat.matcher(msg).matches()) {
			Pattern findPeerName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG);

			Matcher find = findPeerName.matcher(msg);
			if (find.find()) {
				String peerName = find.group(0);

				name = peerName.substring(Tags.PEER_NAME_OPEN_TAG.length(),
						peerName.length() - Tags.PEER_NAME_END_TAG.length());
			} else {
				return null;
			}
		} else {
			return null;
		}
		return name;
	}

	private static Pattern serverSentPort = Pattern // Mau server sent port
			.compile(Tags.SERVER_SENT_PORT_OPEN_TAG + Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG + Tags.PORT_OPEN_TAG
					+ ".*" + Tags.PORT_END_TAG + Tags.SERVER_SENT_PORT_END_TAG);

	public static ArrayList<String> getServerSentPort(String msg) { // Lay thong
																	// tin ip 1
																	// , user ,
																	// ip2 , va
																	// port
		ArrayList<String> send_port = new ArrayList<String>();
		if (serverSentPort.matcher(msg).matches()) {
			Pattern findIpUser1 = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_END_TAG);
			Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*" + Tags.PORT_END_TAG);

			Matcher find = findIpUser1.matcher(msg);
			if (find.find()) {
				String ip = find.group(0);
				send_port.add(ip.substring(Tags.IP_OPEN_TAG.length(), ip.length() - Tags.IP_END_TAG.length()));

				find = findPort.matcher(msg);
				if (find.find()) {
					String port = find.group(0);
					send_port.add(
							port.substring(Tags.PORT_OPEN_TAG.length(), port.length() - Tags.PORT_END_TAG.length()));
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return send_port;
	}

	private static Pattern message = Pattern
			.compile(Tags.MESSAGE_OPEN_TAG + Tags.CRYPT_MESSAGE_OPEN_TAG + ".*" + Tags.CRYPT_MESSAGE_CLOSE_TAG
					+ Tags.ALGORITHM_OPEN_TAG + "[^<>]*" + Tags.ALGORITHM_CLOSE_TAG 
					+ Tags.KEY_OPEN_TAG + ".*" + Tags.KEY_CLOSE_TAG
					+ Tags.IV_OPEN_TAG + ".*" + Tags.IV_CLOSE_TAG
					+ Tags.MESSAGE_END_TAG);

	public static ArrayList<String> getMessage(String msg) {
		ArrayList<String> params = new ArrayList<String>();
		if (message.matcher(msg).matches()) {
			Pattern findCrypMessage = Pattern
					.compile(Tags.CRYPT_MESSAGE_OPEN_TAG + ".*" + Tags.CRYPT_MESSAGE_CLOSE_TAG);
			Pattern findAlgorithm = Pattern.compile(Tags.ALGORITHM_OPEN_TAG + "[^<>]*" + Tags.ALGORITHM_CLOSE_TAG);
			Pattern findKey = Pattern.compile(Tags.KEY_OPEN_TAG + ".*" + Tags.KEY_CLOSE_TAG);
			Pattern findIv = Pattern.compile(Tags.IV_OPEN_TAG + ".*" + Tags.IV_CLOSE_TAG);
			String crypMessage;
			String algorithm;
			String key;
			String iv;
			Matcher find = findCrypMessage.matcher(msg);
			if (find.find()) {
				crypMessage = find.group(0).substring(Tags.CRYPT_MESSAGE_OPEN_TAG.length(),
						find.group(0).length() - Tags.CRYPT_MESSAGE_CLOSE_TAG.length());
				params.add(crypMessage);
				find = findAlgorithm.matcher(msg);
				if (find.find()) {
					algorithm = find.group(0).substring(Tags.ALGORITHM_OPEN_TAG.length(),
							find.group(0).length() - Tags.ALGORITHM_CLOSE_TAG.length());
					params.add(algorithm);
					find = findKey.matcher(msg);
					if(find.find()){
						key = find.group(0).substring(Tags.KEY_OPEN_TAG.length(),
								find.group(0).length() - Tags.KEY_CLOSE_TAG.length());
						params.add(key);
						find = findIv.matcher(msg);
						if(find.find()){
							iv = find.group(0).substring(Tags.IV_OPEN_TAG.length(),
									find.group(0).length() - Tags.IV_CLOSE_TAG.length());
							params.add(iv);
						}
					}
				}
			}
			return params;
		}
		return null;
	}

	private static Pattern escape = Pattern
			.compile(Tags.ESC_CHAT_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + ".*" + Tags.PEER_NAME_END_TAG + Tags.IP_OPEN_TAG
					+ ".+" + Tags.IP_END_TAG + Tags.PORT_OPEN_TAG + ".*" + Tags.PORT_END_TAG + Tags.ESC_CHAT_END_TAG);

	public static ArrayList<String> getEscape(String msg) {
		ArrayList<String> esc = new ArrayList<String>();
		if (escape.matcher(msg).matches()) {
			Pattern findMyName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG);
			Pattern findIp = Pattern.compile(Tags.IP_OPEN_TAG + "[^<>]*" + Tags.IP_END_TAG);
			Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*" + Tags.PORT_END_TAG);

			Matcher find = findMyName.matcher(msg);
			if (find.find()) {
				String myName = find.group(0);
				esc.add(myName.substring(Tags.PEER_NAME_OPEN_TAG.length(),
						myName.length() - Tags.PEER_NAME_END_TAG.length()));

				find = findIp.matcher(msg);
				if (find.find()) {
					String ip = find.group(0);
					esc.add(ip.substring(Tags.IP_OPEN_TAG.length(), ip.length() - Tags.IP_END_TAG.length()));

					find = findPort.matcher(msg);
					if (find.find()) {
						String port = find.group(0);
						esc.add(port.substring(Tags.PORT_OPEN_TAG.length(),
								port.length() - Tags.PORT_END_TAG.length()));

					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return esc;
	}

	private static Pattern requestFile = Pattern.compile(Tags.FILE_REQ_OPEN_TAG + ".*" + Tags.FILE_REQ_END_TAG);

	public static boolean acceptRequestFile(String name) {
		if (requestFile.matcher(name).matches()) {
			return true;
		}
		return false;
	}

	private static Pattern sendFile = Pattern.compile(Tags.FILE_DATA_BEGIN_TAG + ".*" + Tags.FILE_DATA_END_TAG);

	public static boolean sendFileSuccess(String msg) {
		if (sendFile.matcher(msg).matches()) {
			return true;
		}
		return false;
	}

	public static Pattern Online = Pattern.compile(Tags.LOG_IN_RES_SUCCESS_OPEN_TAG + Tags.PORT_OPEN_TAG + ".*"
			+ Tags.PORT_END_TAG + Tags.LOG_IN_RES_SUCCESS_END_TAG);

	public static String portOnl(String msg) {
		Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[^<>]*" + Tags.PORT_END_TAG);
		String portReturn = "";
		if (Online.matcher(msg).matches()) {
			Matcher find = findPort.matcher(msg);

			if (find.find()) {
				String port = find.group(0);
				portReturn = port.substring(Tags.PORT_OPEN_TAG.length(), port.length() - Tags.PORT_END_TAG.length());
			}
		}
		return portReturn;
	}

	public static ArrayList<String> userOnl(String msg) {
		Pattern findUser = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG);
		ArrayList<String> users = new ArrayList<String>();
		if (Online.matcher(msg).matches()) {

			Matcher find = findUser.matcher(msg);
			while (find.find()) {
				String peer = find.group(0);
				users.add(peer.substring(Tags.PEER_NAME_OPEN_TAG.length(),
						peer.length() - Tags.PEER_NAME_END_TAG.length()));
			}
		}
		return users;
	}

	public static ArrayList<DataPeer> getAllUser(String msg) throws NoSuchAlgorithmException, InvalidKeySpecException { // bo
																														// sung
																														// :))
		ArrayList<DataPeer> user = new ArrayList<DataPeer>();
		Pattern findPeer = Pattern.compile(
				Tags.PEER_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + "[^<>]*" + Tags.PEER_NAME_END_TAG + Tags.IP_OPEN_TAG
						+ "[^<>]*" + Tags.IP_END_TAG + Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_END_TAG
						+ Tags.PUBLIC_KEY_OPEN_TAG + "[^<>]*" + Tags.PUBLIC_KEY_CLOSE_TAG + Tags.PEER_CLOSE_TAG);
		Pattern findName = Pattern.compile(Tags.PEER_NAME_OPEN_TAG + ".*" + Tags.PEER_NAME_END_TAG);
		Pattern findPort = Pattern.compile(Tags.PORT_OPEN_TAG + "[0-9]*" + Tags.PORT_END_TAG);
		Pattern findIP = Pattern.compile(Tags.IP_OPEN_TAG + ".+" + Tags.IP_END_TAG);
		Pattern findPublicKey = Pattern.compile(Tags.PUBLIC_KEY_OPEN_TAG + "[^<>]*" + Tags.PUBLIC_KEY_CLOSE_TAG);
		System.out.println("before get all user");
		if (users.matcher(msg).matches()) {
			System.out.println("if stat get all user");
			Matcher find = findPeer.matcher(msg);
			while (find.find()) {
				String peer = find.group(0);
				String data = "";
				DataPeer dataPeer = new DataPeer();
				Matcher findInfo = findName.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setName(data.substring(11, data.length() - 12));
				}
				findInfo = findIP.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setIp(findInfo.group(0).substring(4, data.length() - 5));
				}
				findInfo = findPort.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);
					dataPeer.setPort(Integer.parseInt(data.substring(6, data.length() - 7)));
				}
				findInfo = findPublicKey.matcher(peer);
				if (findInfo.find()) {
					data = findInfo.group(0);

					String key_Str = data.substring(Tags.PUBLIC_KEY_OPEN_TAG.length(),
							data.length() - Tags.PUBLIC_KEY_CLOSE_TAG.length());

					dataPeer.setPublicKey(Convert.String2Key(key_Str, "RSA", true));
				}
				user.add(dataPeer);
			}
		} else {
			return null;
		}
		return user;
	}

	public static String getNameRequestChat(String msg) {
		Pattern checkRequest = Pattern.compile(Tags.CHAT_REQ_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG + "[^<>]*"
				+ Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG);
		if (checkRequest.matcher(msg).matches()) {
			int lenght = msg.length();
			String name = msg.substring((Tags.CHAT_REQ_OPEN_TAG + Tags.PEER_NAME_OPEN_TAG).length(),
					lenght - (Tags.PEER_NAME_END_TAG + Tags.CHAT_REQ_END_TAG).length());
			return name;
		}
		return null;
	}

	public static boolean checkFile(String name) {
		if (checkNameFile.matcher(name).matches()) {
			return true;
		}
		return false;
	}

	private static Pattern checkNameFile = Pattern.compile(Tags.FILE_REQ_OPEN_TAG + ".*" + Tags.FILE_REQ_END_TAG);

	public static boolean checkFeedBack(String msg) {
		if (feedBack.matcher(msg).matches())
			return true;
		return false;
	}

	private static Pattern feedBack = Pattern.compile(Tags.FILE_REQ_ACK_OPEN_TAG + ".*" + Tags.FILE_REQ_ACK_CLOSE_TAG);
	
	public static String receiveChecksum(String msg){
		String checksum = msg.substring(Tags.CHECKSUM_OPEN_TAG.length(), msg.length() - Tags.CHECKSUM_CLOSE_TAG.length());
		return checksum;
	}
	
	public static boolean check_checksum(String msg){
		if (checksum_msg.matcher(msg).matches())
			return true;
		else return false;
	}
	
	private static Pattern checksum_msg = Pattern.compile(Tags.CHECKSUM_OPEN_TAG + ".*" + Tags.CHECKSUM_CLOSE_TAG ); 
	
	
	
}

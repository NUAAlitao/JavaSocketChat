package MyChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Frame.ChatFrame;
import Frame.LoginFrame;
import Frame.PersonalFrame;


public class Client {
	private static Map<String, String> friends = new HashMap<>();
	private static LoginFrame login = null;
	private static PersonalFrame personal = null;

	
	private static String name,pwd;

	public static String massegeAll="";
	
	private static String friend="";
	private static Socket socket = null;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub	
		
		login = new LoginFrame();
		initFrame(login);	
		
	}
	
	//打开登录界面
	public static void initFrame(LoginFrame login){
		login.jb1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 name = login.jname.getText();
				 pwd = login.jpwd.getText();
					InetAddress addr = null;
					try {
						addr = InetAddress.getByName("localhost");
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					try {
						socket = new Socket(addr, 2555);
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					String massege = null;
					BufferedReader in = null;
					DataOutputStream out = null;
					
					try {
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						out = new DataOutputStream(socket.getOutputStream());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						out.writeBytes(name+" "+pwd+'\n');
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						massege = in.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(massege.equals("OK")){
						login.setVisible(false);
						File file = new File(name);
						
						String temp;
						try {
							in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							while((temp = in.readLine()) != null){
								friends.put(temp,temp);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
						try {
							in.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
						
						personal = new PersonalFrame(name);
						Iterator iter = friends.entrySet().iterator();
						while(iter.hasNext()){
							Map.Entry now = (Entry) iter.next();
							friend = friend + now.getValue()+'\n';
						}
						personal.jta.setText(friend);	
						initChat();
					}
					else{
						login.jname.setText("用户名或密码错误");
					}				
			}
		});
	}

	public static void initChat(){
		personal.jbChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String friendname=null;
				Massege massege = new Massege();
			    friendname = personal.jfriend.getText();
				if(friends.get(friendname)!=null){
					ChatFrame chatFrame = new ChatFrame(name,friendname);					
					new ChatIn(socket, friendname, massege, chatFrame).start();
					new ChatOut(socket, friendname, name, massege, chatFrame).start();
				}
				else{
					personal.jfriend.setText("你和"+friendname+"不是好友，不能聊天");
				}
			}
		});
	}
}

class ChatIn extends Thread{
	Socket socket;
	BufferedReader in=null;
	String friendname;
	Massege massege;
	ChatFrame chatFrame;
	
	public ChatIn(Socket socket, String friendname,Massege massege,ChatFrame chatFrame){
		this.socket = socket;
		this.friendname = friendname;
		this.massege = massege;
		this.chatFrame = chatFrame;
	}
	@Override
	public void run(){
		try{
			while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input = in.readLine();
				String [] temp = input.split("-");
				if(temp[1].equals(friendname)){
					String temp1="";
					temp1=temp1+friendname+": "+temp[2];
					massege.setMassege(temp1);
					chatFrame.jta.setText(massege.getMassege());
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

class ChatOut extends Thread{
	Socket socket;
	String friendname;
	Massege massege;
	ChatFrame chatFrame;
	String myname;
	DataOutputStream out=null;
	
	public ChatOut(Socket socket, String friendname,String myname, Massege massege,ChatFrame chatFrame){
		this.socket=socket;
		this.friendname = friendname;
		this.myname = myname;
		this.massege = massege;
		this.chatFrame = chatFrame;
	}
	
	@Override
	public void run(){
		try{
				out = new DataOutputStream(socket.getOutputStream());
				
				chatFrame.jbSend.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String temp = chatFrame.jmassege.getText();
						String temp1=friendname+ "-" + myname + "-" +temp;
						String temp2 = myname+": "+temp;
						massege.setMassege(temp2);
						chatFrame.jta.setText(massege.getMassege());
						chatFrame.jmassege.setText("");
						try {
							out.writeBytes(temp1+'\n');
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

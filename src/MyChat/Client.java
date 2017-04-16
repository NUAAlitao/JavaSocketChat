package MyChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Frame.ChatFrame;
import Frame.LoginFrame;
import ChatFrame.PersonalFrame;


public class Client {
	private static Map<String, String> friends = new HashMap<>();
	private static LoginFrame login = null;
	private static PersonalFrame personal = null;
	
	private static String name,pwd;	
	private static String friend="";
	private static Socket socket = null;
	
	private static boolean chatFlag = false;
	private static Map<String, ChatFrame> chatWindows = new HashMap<>();
	private static Map<String, Massege> massegeAll = new HashMap<>();
	
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
						out.writeBytes(name+" "+pwd+" login"+'\n');    //向服务器发送登录消息
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
						personal.friendArea.setText(friend);	
						initChat();
					}
					else{
						login.jname.setText("用户名或密码错误");
					}				
			}
		});
		
		login.jb2.addActionListener(new ActionListener() {	
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
					out.writeBytes(name+" "+pwd+" register"+'\n');    //向服务器发送注册消息
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
					String temp = new File("").getAbsolutePath();
					name = temp+'/'+name;
					File file = new File(name);
					
					if(!file.exists()){
						try {
							file.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					login.jname.setText("注册成功，可以登录了！");
				}
				else{
					login.jname.setText("该用户已存在！");
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
				
				
			    friendname = personal.friendName.getText();  
				if(friends.get(friendname)!=null){	
					ChatFrame chatFrame = new ChatFrame(name,friendname);	
					massegeAll.put(friendname, massege);
					chatWindows.put(friendname, chatFrame);
					new ChatOut(socket, friendname, name, massege, chatFrame).start();
					if(chatFlag==false){
						new ChatIn(socket, friendname, massegeAll, chatWindows).start();
						chatFlag = true;
					}
				}
				else{
					personal.friendName.setText("你和"+friendname+"不是好友，不能聊天");
				}
			}
			
		});
		
		personal.jbExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		personal.jbsearchuser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String temp;
				String [] temps;
				String users="";
				File file = new File("Users.txt");
				BufferedReader in = null;		
				try {
					in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					while((temp = in.readLine()) != null){
						temps = temp.split(" ");
						users = users + temps[0] + '\n';
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
				personal.userArea.setText(users);
			}
		});
		
		personal.jbaddfriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String friendname = personal.addfriendname.getText();
				friends.put(friendname, friendname);
				friend = friend + friendname +'\n';
				personal.friendArea.setText(friend);
				BufferedWriter fileout = null;
				try {
					 fileout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name, true)));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fileout.write('\n'+friendname);
					fileout.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fileout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
	Map<String, ChatFrame> chatWindows;
	Map<String, Massege> massegeAll;
	
	public ChatIn(Socket socket, String friendname,Map<String, Massege> massegeAll,Map<String, ChatFrame> chatWindows){
		this.socket = socket;
		this.friendname = friendname;
		this.massegeAll = massegeAll;
		this.chatWindows = chatWindows;
	}
	@Override
	public void run(){
		try{
			while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input = in.readLine();
				String [] temp = input.split("-");
				chatFrame = chatWindows.get(temp[1]);     //查找到当前朋友的聊天框
				massege = massegeAll.get(temp[1]);
				String temp1="";
				temp1=temp1+temp[1]+": "+temp[2];
				massege.setMassege(temp1);
				chatFrame.jta.setText(massege.getMassege());
				
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
	BufferedWriter out=null;
	
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
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				chatFrame.jbSend.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String temp = chatFrame.jmassege.getText();
						String temp1=friendname+ "-" + myname + "-" +temp;
						String temp2 = "我: "+temp;
						massege.setMassege(temp2);
						chatFrame.jta.setText(massege.getMassege());
						chatFrame.jmassege.setText("");
						try {
							out.write(temp1+'\n');
							out.flush();
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

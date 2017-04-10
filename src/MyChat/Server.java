package MyChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server {
	private static Map<String, String> users = new HashMap<>();
	private static Map<String, Socket> socketList = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		initUsers();
		BufferedReader in;
		BufferedWriter out;
		String login,name,pwd;
		ServerSocket server = new ServerSocket(2555);
		while(true){
			Socket socket = server.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			login = in.readLine();
			System.out.println("socket:" + socket);
			System.out.println("收到信息:"+login);
			String []temp = login.split(" ");  //读取登录的名字和密码
			if(((users.get(temp[0]))!=null) &&(users.get(temp[0]).equals(temp[1])) ){
				out.write("OK"+'\n');   //能登录
				out.flush();
				socketList.put(temp[0], socket);
				new ServerThread(socketList, socket).start();
			}
			else{
				out.write("NO"+'\n');   //不能登录
				out.flush();
			}
		}
	}
	
	public static void initUsers() throws IOException{
		String temp;
		String []temps;
		File file = new File("Users.txt");
		BufferedReader in;		
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((temp = in.readLine()) != null){
			temps = temp.split(" ");
			users.put(temps[0], temps[1]);
		}	
		in.close();	
	}
}

class ServerThread extends Thread{
	Socket socket1,socket2;
	private  Map<String, Socket> socketList = new HashMap<>();
	BufferedReader in;
	BufferedWriter out;
	
	public ServerThread(Map<String, Socket> socketList, Socket socket){
		this.socket1 = socket;
		this.socketList = socketList;
	}
	@Override
	public void run(){
		try{
			while(true){
				in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				String massege=null;
				massege = in.readLine();
				System.out.println(massege);
				String [] temp=massege.split("-");
				socket2 = socketList.get(temp[0]);
				out = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
				out.write(massege+'\n');
				out.flush();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

package Frame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class PersonalFrame extends JFrame{
	public JButton jbChat,jbExit;
	public JTextField jfriend;
	JLabel jl1,jl2;
	JPanel jp1,jp2,jp3;
	public JTextArea jta;
	JScrollPane jsp;
	
	public PersonalFrame(String name){
		jbChat = new JButton("聊天");
		jbExit = new JButton("退出");
		jl1 = new JLabel("Hello "+name+", welcome to MyChat");
		jl2 = new JLabel("请输入好友姓名：");
		jfriend = new JTextField(10);
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jp1 = new JPanel();		
		jp1.add(jl1);
		jp2 = new JPanel(new GridLayout(1, 2,10,10));
		jp2.add(jsp);
		
		jp3 = new JPanel(new GridLayout(4, 1));
		jp2.add(jp3);
		jp3.add(jl2);
		jp3.add(jfriend);
		jp3.add(jbChat);
		jp3.add(jbExit);
		
		
		
		this.setLayout(new GridLayout(2, 1));
		this.add(jp1);
		this.add(jp2);
		this.setSize(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
	}
}

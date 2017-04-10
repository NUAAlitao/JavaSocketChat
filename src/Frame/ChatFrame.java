package Frame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ChatFrame extends JFrame{
	public JLabel jl1,jl2;
	public JTextArea jta,jmassege;
	public JScrollPane jsp;
	public JButton jbSend;
	JPanel jp1,jp2;
	 
	public ChatFrame(String name,String friendname){
		jl1 = new JLabel("输入消息：");
		jl2 = new JLabel("hello "+name+", you are chating with "+friendname);
		jbSend = new JButton("发送消息");
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jmassege = new JTextArea();
		
		this.setLayout(null);
		this.add(jl2);
		jl2.setLocation(85, 10);
		jl2.setSize(400, 20);
		
		this.add(jsp);
		jsp.setSize(360,300);
		jsp.setLocation(10, 40);
		
		jp1 = new JPanel(new GridLayout(3,1));
		jp1.setLocation(10, 350);
		jp1.setSize(360, 100 );
		jp1.add(jl1);
		jp1.add(jmassege);
		jp1.add(jbSend);
		this.add(jp1);
		
		this.setSize(400,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}

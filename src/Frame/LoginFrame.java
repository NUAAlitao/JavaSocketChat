package Frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowStateListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginFrame extends JFrame{
	public JButton jb1,jb2;
	public JLabel jl1,jl2;
	public JTextField jname;
	public JTextField jpwd;
	JPanel jp1,jp2,jp3;
	public LoginFrame(){
		jb1 = new JButton("µÇÂ¼");
		jb2 = new JButton("×¢²á");
		jl1 = new JLabel("ÕËºÅ£º");
		jl2 = new JLabel("ÃÜÂë£º");
		jname = new JTextField(10);
		jpwd = new JTextField(10);
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		this.setLayout(new GridLayout(3, 1));
		jp1.add(jl1);
		jp1.add(jname);
		jp2.add(jl2);
		jp2.add(jpwd);
		jp3.add(jb1);
		jp3.add(jb2);
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.setTitle("MyChat");
		this.setSize(300,200);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}

package ChatFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.ComponentOrientation;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class PersonalFrame {

	private JFrame frmDsfd;
	public JTextField friendName;
	JSplitPane splitPane;
	JLabel jlhello;
	JPanel panel;
	JLabel jlfriends;
	public JTextArea friendArea;
	public JButton jbChat;
	public JButton jbExit;
	public JTextArea userArea;
	public JButton jbsearchuser;
	JLabel jlusers;
	public JTextField addfriendname;
	public JButton jbaddfriend;
	public JLabel jladdfriend;
	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalFrame window = new PersonalFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public PersonalFrame(String name) {
		initialize();
		jlhello.setText("Hello "+name+", welcome to MyChat");
		frmDsfd.setTitle(name);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDsfd = new JFrame();		
		frmDsfd.setVisible(true);
		
		frmDsfd.setForeground(Color.BLACK);
		frmDsfd.setFont(UIManager.getFont("TitledBorder.font"));
		frmDsfd.setBounds(100, 100, 408, 537);
		frmDsfd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDsfd.getContentPane().setLayout(new CardLayout(0, 0));
		
	    splitPane = new JSplitPane();
	    splitPane.setDividerSize(1);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmDsfd.getContentPane().add(splitPane, "name_2447060657998");
		
	    jlhello = new JLabel("New label");
	    jlhello.setHorizontalTextPosition(SwingConstants.CENTER);
	    jlhello.setHorizontalAlignment(SwingConstants.CENTER);
	    jlhello.setForeground(Color.BLACK);
	    jlhello.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		splitPane.setLeftComponent(jlhello);
		
		 panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		splitPane.setRightComponent(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		 jlfriends = new JLabel("我的好友列表");
		jlfriends.setBounds(10, 10, 178, 20);
		panel_2.add(jlfriends);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 178, 192);
		panel_2.add(scrollPane);
		
	    friendArea = new JTextArea();
		scrollPane.setViewportView(friendArea);
		
		friendName = new JTextField();
		friendName.setBounds(216, 67, 162, 27);
		panel_2.add(friendName);
		friendName.setColumns(10);
		
		jbChat = new JButton("\u804A\u5929");
		jbChat.setBounds(216, 117, 162, 23);
		panel_2.add(jbChat);
		
		jbExit = new JButton("\u9000\u51FA");
		jbExit.setBounds(216, 150, 162, 23);
		panel_2.add(jbExit);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u597D\u53CB\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setBounds(216, 35, 162, 20);
		panel_2.add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 35, 178, 192);
		panel_1.add(scrollPane_1);
		
		userArea = new JTextArea();
		scrollPane_1.setViewportView(userArea);
		
		jbsearchuser = new JButton("\u67E5\u8BE2\u7528\u6237");
		jbsearchuser.setBounds(225, 36, 93, 23);
		panel_1.add(jbsearchuser);
		
		jlusers = new JLabel("所有用户");
		jlusers.setBounds(10, 11, 54, 19);
		panel_1.add(jlusers);
		
		addfriendname = new JTextField();
		addfriendname.setBounds(225, 147, 115, 21);
		panel_1.add(addfriendname);
		addfriendname.setColumns(10);
		
		jbaddfriend = new JButton("\u6DFB\u52A0\u597D\u53CB");
		jbaddfriend.setBounds(225, 72, 93, 23);
		panel_1.add(jbaddfriend);
		
		jladdfriend = new JLabel("\u8F93\u5165\u7528\u6237\u540D");
		jladdfriend.setBounds(225, 114, 73, 23);
		panel_1.add(jladdfriend);
	}
}

package mytool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2413434395598673868L;
	private ConnSocket conn;
	private Frame instance;
	
	private JLabel typeLabel;
	private JTextField text;
	private JButton chooseBtn;
	private JButton leftBtn;
	private JButton rightBtn;
	private JTextArea msgArea;

	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		create();
		setBounds(new Rectangle(new Dimension(400, 400)));
		setVisible(true);
		setTitle("小工具");
		instance = this;
		actionByType(0);
	}
	
	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			System.out.println(c);
			if(c.equals("choose")){
				msgArea.setText("状态改变，现在是选则文件");
			}else if (c.equals("conn")){
				conn = new ConnSocket(instance);
			}else if(c.equals("encode")){
				msgArea.setText("加密文件\n");
			}else if(c.equals("send")){
				msgArea.setText("发送消息\n");
				conn.sendMsg("{\"a\":\"send\", \"m\":\"test\", \"content\":\"" + text.getText() +"\"}");
			}else if(c.equals("decode")){
				msgArea.setText("解密文件\n");
			}else if(c.equals("temp")){
				msgArea.setText("未实现");
			}
			
			
		}
	};
	
	private void actionByType(int type){
		switch(type){
		case 0:
			setTitle("代码加密工具 ");
			typeLabel.setText("路径");
			chooseBtn.setText("选择");
			chooseBtn.setActionCommand("choose");
			leftBtn.setText("加密");
			leftBtn.setActionCommand("encode");
			rightBtn.setText("解密");
			rightBtn.setActionCommand("decode");
			break;
		case 1:
			setTitle("网络连接小工具  ");
			typeLabel.setText("地址");
			chooseBtn.setText("连接");
			chooseBtn.setActionCommand("conn");
			leftBtn.setText("发送");
			leftBtn.setActionCommand("send");
			rightBtn.setText("待定");
			rightBtn.setActionCommand("temp");
			break;
		}
		
	}

	private void create() {
		GridBagLayout gbl = new GridBagLayout();

		JPanel main = new JPanel(gbl);
		main.setBackground(Color.green);
		setContentPane(main);

		typeLabel = new JLabel("路径");
		main.add(typeLabel, T.gbc(0, 0, 1, 1));

		text = new JTextField();
		main.add(text, T.gbc(1, 0, 3, 1, 10));

		chooseBtn = new JButton("选择");
		chooseBtn.addActionListener(listener);
		main.add(chooseBtn, T.gbc(4,0,1,1));
		
		final JComboBox<Integer> com = new JComboBox<Integer>();
		main.add(com, T.gbc(5,0,1,1));
		com.addItem(0);
		com.addItem(1);
		
		com.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actionByType(com.getSelectedIndex());
				
			}
		});
		
		

		JPanel temp = new JPanel();
		main.add(temp, T.gbc(0, 1, 0, 1));
		
		temp.setLayout(new GridLayout(1,2));

		leftBtn = new JButton("加密");
		leftBtn.addActionListener(listener);
		temp.add(leftBtn,BorderLayout.WEST);

		rightBtn = new JButton("解密");
		rightBtn.addActionListener(listener);
		temp.add(rightBtn, BorderLayout.CENTER);

		msgArea = new JTextArea();
		
		JScrollPane scroll = new JScrollPane(msgArea);
		main.add(scroll, T.gbc(0, 2, 0, 5, 1, 5));
	}
	
	public void showMsg(String msg){
		msgArea.append(msg + "\n");
	}

}

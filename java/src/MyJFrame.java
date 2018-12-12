package com.mm.demo;


import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.*;

import com.mm.demo.Demo.SerialPortClient;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class MyJFrame  implements ActionListener {
	JFrame jf;
	JPanel jp;
	
	CommPortIdentifier port;
	SerialPortClient client;
	
	JButton jb2,jb3,jb1,jb4,jb5,jb6,jb7;
	JTextField jtf2;
	JComboBox jcb1;
	JLabel jl3;
	
	public void cteatorJF() {
		jf =new JFrame();
		jp = new JPanel();
		jp.setBackground(new Color(255, 206, 222));
		setUIFont();
        //第一行
        JLabel jl1 = new JLabel("com:");
        jl1.setHorizontalAlignment(SwingConstants.LEFT);
        jcb1 = new JComboBox();
        jcb1.addItem("COM1");
        jcb1.addItem("COM2");
        jcb1.addItem("COM3");
        jcb1.addItem("COM4");
        jcb1.addItem("COM5");
        jcb1.addItem("COM6");
        jl3 = new JLabel("连接关闭");
        jp.add(jl1);jp.add(jcb1);jp.add(jl3);
        //第二行
        jtf2 = new JTextField(32);
        jp.add(jtf2);
        //第三行
        jb1 = new JButton("连接51单片机");
        jb2 = new JButton("修改1602文字");
        jb3 = new JButton("显示CPU使用率");
        jb6 = new JButton("打开插座");
        jb7 = new JButton("关闭插座");
        jb4 = new JButton("关闭连接");
        jb5 = new JButton("使用手册");
        jp.add(jb1);jp.add(jb2);jp.add(jb3);jp.add(jb4);jp.add(jb5);jp.add(jb6);jp.add(jb7);
        
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        jb5.addActionListener(this);
        jb6.addActionListener(this);
        jb7.addActionListener(this);
        
        jf.add(jp);
        //jf.setLayout();//流式布局
        jf.setTitle("STC89C51单片机连接1602控制系统-喵喵");
        jf.setSize(400,170);//大小
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);//居中
		jf.setIconImage(new ImageIcon("img/1.png").getImage());
        jf.setVisible(true);
       
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(jl3.getText()=="连接关闭") {
					System.exit(0);
				}else {
					client.close();
					jl3.setText("连接关闭");
					System.exit(0);
				}
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s =e.getSource();
		if(s == jb2) {	//修改文字
			try {
				Demo.sendString(jtf2.getText().toString(), client);
			} catch (IOException | InterruptedException e1) {
				JOptionPane.showMessageDialog(null, "发送错误", "警告",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}else if(s == jb3) {	//监测CPU
			try {
				Demo.sendString("CPU:"+CPULoad.getCpuLoadPercentage()+"%", client);
			} catch (IOException | InterruptedException e1) {
				JOptionPane.showMessageDialog(null, "发送错误", "警告",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			jtf2.setText("");
		}else if(s == jb1) {	//连接单片机
			if(jl3.getText()=="连接成功") {
				JOptionPane.showMessageDialog(null, "你已经连接一个端口了，请关闭", "警告",JOptionPane.ERROR_MESSAGE);
			}else {
				port = Demo.getSerialPort((String) jcb1.getSelectedItem());
				System.out.println(port.getName());
				client = new SerialPortClient(port);
				
				try {
					client.initAndOpen();
					jl3.setText("连接成功");
				} catch (IOException | UnsupportedCommOperationException | TooManyListenersException
						| PortInUseException e1) {
					JOptionPane.showMessageDialog(null, "连接错误", "警告",JOptionPane.ERROR_MESSAGE);
					jl3.setText("连接失败");
					e1.printStackTrace();
				}
			}
		}else if(s == jb4){
			if(jl3.getText()=="连接关闭") {
				JOptionPane.showMessageDialog(null, "你已经关闭端口了，请重新开启", "警告",JOptionPane.ERROR_MESSAGE);
			}else {
				client.close();
				jl3.setText("连接关闭");
			}
		}else if(s == jb5){
			JOptionPane.showMessageDialog(null, "使用方法：此软件发送数据:[&+'内容'+' '],请使用者在下位机自行解析", "使用手册", JOptionPane.WARNING_MESSAGE);
		}else if(s ==jb6){
			try {
				Demo.sendString("%&0", client);
			} catch (IOException | InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}else if(s ==jb7) {
			try {
				Demo.sendString("%&1", client);
			} catch (IOException | InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}else {
		
			
		}
	}
	public static void setUIFont()
	{
		Font f = new Font("微软雅黑",Font.PLAIN,13);
		String   names[]={ "Label", "CheckBox", "PopupMenu","MenuItem", "CheckBoxMenuItem",
				"JRadioButtonMenuItem","ComboBox", "Button", "Tree", "ScrollPane",
				"TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
				"OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
				"ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
				"PasswordField","TextField", "Table", "Label", "Viewport",
				"RadioButtonMenuItem","RadioButton", "DesktopPane", "InternalFrame"
		}; 
		for (String item : names) {
			 UIManager.put(item+ ".font",f); 
		}
	}
}


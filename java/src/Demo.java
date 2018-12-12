package com.mm.demo;

import gnu.io.CommPortIdentifier;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;





public class Demo {
	static String a="";
	static int i=0;
	public static void main(String[] args) throws Exception {
		MyJFrame jf = new MyJFrame();
		jf.cteatorJF();
		/*
		CommPortIdentifier port = Demo.getSerialPort("COM5");
		System.out.println(port.getName());
		SerialPortClient client = new SerialPortClient(port);
		client.initAndOpen();
		/*while(true) {
			if(i==0) {
				a = "CPU:"+CPULoad.getCpuLoadPercentage()+"%";
			}else {
				
			}
			a="1234567890abcdefghijklmn";
			sendString(a, client);
		}
		*/
	}
	
	
	
	//发送字符串
	public static void sendString(String sendData,SerialPortClient client) throws IOException, InterruptedException {
		client.send(new byte[]{(byte) '$'});
		client.send(sendData.getBytes());
		client.send(new byte[]{(byte) ' '});
	}
	// 列出端口
	public static Enumeration<CommPortIdentifier> listAllPort() {
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier
				.getPortIdentifiers();
		return portList;
	}
 
	public static List<CommPortIdentifier> listAllPort(int portType) {
		List<CommPortIdentifier> ret = new ArrayList<>();
		Enumeration<CommPortIdentifier> all = listAllPort();
		if (all == null)
			return ret;
		while (all.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) all.nextElement();
			if (portId.getPortType() == portType) {
				ret.add(portId);
			}
		}
		return ret;
	}
 
	public static List<CommPortIdentifier> listAllSerialPort() {
		return listAllPort(CommPortIdentifier.PORT_SERIAL);
	}
 
	// 获取串口
	public static CommPortIdentifier getSerialPort(String portName) {
		List<CommPortIdentifier> list = listAllSerialPort();
		if (list == null)
			return null;
		for (CommPortIdentifier p : list) {
			if (p.getName().equalsIgnoreCase(portName)) {
				return p;
			}
		}
		return null;
	}
 
	// 串口操作
	public static class SerialPortClient implements SerialPortEventListener {
		public final CommPortIdentifier port;
		public SerialPort serialPort;
		public InputStream is;
		public OutputStream os;
 
		public SerialPortClient(CommPortIdentifier port) {
			this.port = port;
		}
 
		public void initAndOpen() throws IOException, UnsupportedCommOperationException,
				TooManyListenersException, PortInUseException {
			System.out.println("串口：初始化和打开。。。（波特率9600，数据位8，停止位1，无奇偶校验）");
			serialPort = (SerialPort) port.open("SerialPort-Test", 2000);
 
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
 
			serialPort.setSerialPortParams(9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
 
			os = serialPort.getOutputStream();
			is = serialPort.getInputStream();
		}
 
		public void close() {
			System.out.println("串口：关闭。。。");
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
			if (serialPort != null)
				serialPort.close();
		}
 
		public void send(byte[] arr) throws IOException, InterruptedException {
			if (arr != null && os != null) {
				//System.out.println("串口：发送字节个数" + arr.length);
				os.write(arr);
			} else {
				//System.out.println("串口：发送失败，发送数据为空或没打开串口");
			}
			Thread.sleep(200);
		}
 
		protected void onReceive(SerialPortEvent event) {
			//System.out.println("串口：读数据。。。");
			int newData = 0;
			do {
				try {
					newData = is.read();
					//System.out.println("串口：读到数据：" + Integer.toString(newData, 16));
				} catch (IOException e) {
					return;
				}
			} while (newData != -1);
		}
 
		public void serialEvent(SerialPortEvent event) {
			switch (event.getEventType()) {
			case SerialPortEvent.BI:
			case SerialPortEvent.OE:
			case SerialPortEvent.FE:
			case SerialPortEvent.PE:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.RI:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:// 获取到串口返回信息
				onReceive(event);
				break;
			default:
				break;
			}
		}
	}
}

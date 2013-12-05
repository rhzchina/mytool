package mytool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
public class ConnSocket {
	Socket socket;
	OutputStream out;
	InputStream in;
	MsgThread msg;
	Frame mainWindow;
	
	byte[] buff = new byte[1024];
	
	public ConnSocket(Frame main){ 
		msg = new MsgThread();
		connect();
		msg.start();
		mainWindow = main;
	}
	
	public void sendMsg(String str){
		try {
			byte[] msg = new byte[1024];
			int len = str.getBytes().length;
			
			msg[0] = (byte)(len >> 24  & 0xff ); 
			msg[1] = (byte)((len >> 16) & 0xff );
			msg[2] =(byte)((len >> 8) & 0xff );
			msg[3] =(byte)(len & 0xff );
			
			for(int i = 0;i < str.getBytes().length;i++){
				msg[i + 4] = str.getBytes()[i];
			}
			System.out.println(msg);
			out.write(msg,0,len + 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void connect(){
		new Thread(){
			public void run(){
				try {
					socket = new Socket(InetAddress.getByName("211.154.135.186"), 1234);
					out = socket.getOutputStream();
					in = socket.getInputStream();
					
					
					while(!socket.isInputShutdown()){
						System.out.println("wait for msg");
						int code = in.read(buff);
						msg.recMsg(buff);
						System.out.println(code);
						if(code == -1){
							break;
						}
					}
					System.out.println("shutdonw");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
	}
	
	class MsgThread extends Thread{
		private ArrayList<byte[]> msgQueue = new ArrayList<byte[]>();
		public MsgThread(){
			
		}
		public void recMsg(byte[] msg){
			msgQueue.add(msg);
		}
		@Override
		public void run(){
			while(true){
				if(msgQueue.size() > 0){
					byte[] cur = msgQueue.remove(0);
					int len = 0;
					len = cur[0] << 24 & 0xf000 ;
					len += cur[1] << 16 & 0xff00 ;
					len += cur[2] << 8 & 0xfff0 ;
					len += cur[3];
					mainWindow.showMsg(new String(cur, 4, len));
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

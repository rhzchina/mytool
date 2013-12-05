package mytool;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {
	public static String OUT = "D:\\developer\\cocos2d-2.0-x-2.0.4_init\\Debug.win32\\";; 
	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
//		String path = "D:\\developer\\cocos2d-2.0-x-2.0.4_init\\Hero\\Resources\\";
//		String path = "D:\\developer\\cocos2d-2.0-x-2.0.4_init\\GameLua\\Resources\\";
//		String path = "";
//		getFiles(path + "framework", true);
//		getFiles(path + "GameLuaScript", true);
		
//		getFiles(path + "framework", false);
//		getFiles(path + "GameScript", false);
//		System.out.println('z' ^ '`');
//		`'));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}
	
	public static void getFiles(String path, boolean decode) throws IOException{
		File f = new File(path);
		if(f.isDirectory()){
			format(f, decode);
			File[] sub = f.listFiles();
			for(File file : sub){
				if(file.isDirectory()){
					getFiles(file.getAbsolutePath(), decode);
				}else{
					format(file, decode);
				}
			}
		}else{
			format(f, decode);
		}
	}
	
	public static void format(File file, boolean decode) throws IOException{
		String path = file.getAbsolutePath();
		String out = path.substring(path.indexOf("Resources") + 10);
		File dir = new File(OUT + out);
		
		if(file.isDirectory()){
			if(!dir.exists()){
				dir.mkdirs();
			}
			return;
		}else{
			if(!decode){
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				PrintWriter pw = new PrintWriter(dir);
				String str = null;
				boolean block = false;  //是否是块注释
				while( (str = br.readLine()) != null){
//					str = str.trim();
//					if(!block && (str.startsWith("--") || str.startsWith("--[["))){
//						if(str.startsWith("--[[") && !str.endsWith("]]")){
//							block = true;	
//						}
//					}else{
//						if(block && (str.endsWith("]]") || str.endsWith("]]--"))){
//							block = false;
//						}else{
//							if(!block && !str.equals("")){
//								int index = str.indexOf("--");
//								if(index != -1){
//									str = str.substring(0,index);
//								}
								pw.println(str);
//							}
//						}
//					}
					
				}
				br.close();
				pw.close();
			}
		}
		if(dir.getName().equals("version1.lua") || dir.getName().equals("channel1.lua")){
			
		}else{
			encode(dir);
		}
	}
	
	public static void encode(File file) throws IOException{
//		byte[] key = {'c', 't', 's', 'd'};
//		byte[] key = {'`','*', '&'};
		byte[] key = {'`'};
		byte[] buf = new byte[1024];
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		int len = 0;
		long offset = 0;
		int index = 0;
		while((len = raf.read(buf)) != -1 ){
			for(int i = 0; i < len; i++){
				if(index >= key.length){
					index = 0;
				}
				if(buf[i] != ' ' && buf[i] != 'z' && buf[i] != '`'){
					buf[i] ^= key[index++];
				}else{
				}
				
			}
			raf.seek(offset);
			raf.write(buf, 0, len);
			offset += len;
		}
		raf.close();
	}
}

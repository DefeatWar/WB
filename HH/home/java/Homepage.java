package HH.home.java;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@ServerEndpoint("/WebSocketHomepage")
public class Homepage {
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static Map<Session,String[]> map=new ConcurrentHashMap<Session,String[]>();
	private final int nickname;
	private Session session;
	
	private static JSONArray photo_size;
	private static ArrayList<String> photoname=new ArrayList<String>();
	SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHH");
	
	
	ByteBuffer picturebyte=ByteBuffer.allocate(9999);
	
	public Homepage() {
        nickname = connectionIds.getAndIncrement();
	}
	
	@OnOpen
    public void open(Session session) {

    }
	@OnMessage
    public void echoTextMessage(Session session, String msg) throws IOException{
		System.out.println(msg);
		try{
			JSONObject jsobj=JSONObject.fromObject(msg);
			String key=jsobj.getString("key");
			if(key.equals("sign_new")){
				String username_page=jsobj.getString("username_page");
				String home_interest_math=HomepageJDBC.Home_user_interest(Integer.parseInt(username_page));
				System.out.println(home_interest_math);
				
				String[] homein=home_interest_math.split(",");
				
				map.put(session, homein);
				
				String backmessage=HomepageJDBC.Home_secton(home_interest_math);
				session.getBasicRemote().sendText(backmessage);
				System.out.println(backmessage);	
			}
			if(key.equals("newpost")){
				photoname.clear();
				String photo=jsobj.getString("photo");
				
				String username_page=jsobj.getString("username_page");
				String write_title=jsobj.getString("write_title");
				String write_content=jsobj.getString("write_content");
				String interest=jsobj.getString("interest");
				
				if(photo.equals("y")){
					String size=jsobj.getString("size");
					photo_size=JSONArray.fromObject(size);
					for(int i=0;i<photo_size.size();i++){
						String newdata=df.format(new Date());
						int mathn=(int)(Math.random()*1000)+10;
						String pname=newdata+mathn;
						String backinteg = null;
						if(interest.equals("IT技术")){
							backinteg="ITTechnology";
						}
						if(interest.equals("房产")){
							backinteg="Houseproperty";
						}
						photoname.add(backinteg+"/"+pname+".jpg");
					}
				}
				System.out.println("newhhh"+photoname.toString());
				
				System.out.println("username_page;"+username_page);	
				int post_id=NewpostJDBC.Test(username_page, interest, write_title,write_content,photoname);
				
				String backmessage=NewpostJDBC.Home_secton(post_id);
				broadcast(backmessage,interest);
			}
			if(key.equals("post_content")){
				String text_id=jsobj.getString("post_text_ie");
				PostJDBC.post_look(Integer.valueOf(text_id));
				int msgg=Integer.valueOf(text_id);
				String[] back=PostJDBC.post_message(msgg);
				String conback=PostJDBC.post_counent_back(back);
				System.out.println(conback);
				session.getBasicRemote().sendText(conback);
			}
			if(key.equals("new_good")){
				String text_id=jsobj.getString("text_id");
				System.out.println(text_id);
				int newgood=PostgbJDBC.post_good(Integer.parseInt(text_id));
				String[] backmessages={"good",String.valueOf(newgood)};
				session.getBasicRemote().sendText(JSONArray.fromObject(backmessages).toString());
			}
			if(key.equals("new_bad")){
				String text_id=jsobj.getString("text_id");
				System.out.println(text_id);
				int newbad=PostgbJDBC.post_bad(Integer.parseInt(text_id));
				String[] backmessages={"bad",String.valueOf(newbad)};
				session.getBasicRemote().sendText(JSONArray.fromObject(backmessages).toString());
			}
			if(key.equals("new_chat_connect")){
				String post_id=jsobj.getString("post_id");
				String username_page=jsobj.getString("username_page");
				String post_connct=jsobj.getString("post_connct");
				Newchat.new_chat(Integer.parseInt(post_id),Integer.parseInt(username_page),post_connct);
			}
			if(key.equals("sigin_chat")){
				String post_id=jsobj.getString("post_id");
				String chat_m=Newchat.sign_chat(Integer.parseInt(post_id));
				session.getBasicRemote().sendText(chat_m);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
	
	@OnError
	public void onError(Throwable throwable,Session session){
		map.remove(this);
	}
	@OnClose
    public void end(Session session) {
		map.remove(session);
    }
int fileosize=0;

	@OnMessage
	public void onBinaryMessage(byte[] b) {
System.out.println("h:"+b[0]);
fileosize=fileosize+b.length;

String outputFile;

if(photo_size.getString(0)!=null){
	int filesize=Integer.parseInt((String) photo_size.getString(0));
	String filename=photoname.get(0);
	outputFile="E:/Tomcat/apache-tomcat-7.0.70/wtpwebapps/HH/user_picture/"+filename;
	System.out.println(outputFile);
	if(fileosize==filesize){
		photo_size.remove(0);
		photoname.remove(0);
		fileosize=0;
		//System.out.println(photoname.get(0));
	}
}else{
	return;
}

BufferedOutputStream stream = null;  
File file = new File(outputFile);
	try  
	{   
	  FileOutputStream fstream = new FileOutputStream(file,true);  
	  stream = new BufferedOutputStream(fstream);  
	  stream.write(b); 
	  fstream.flush();  
	}catch (Exception e)  {  
	  e.printStackTrace();
	}finally{
		
	  if (stream != null){  
	    try{  
	      stream.close();  
	    }catch (IOException e1){  
	      e1.printStackTrace();  
	    }  
	  }  
	} 
}
	private static void broadcast(String msg,String interest) {
        for(Entry<Session, String[]> m:map.entrySet()){
        	String[] mapm=m.getValue();
        	for(int i=0;i<mapm.length;i++){
        		System.out.println(mapm[i]);
        		System.out.println(interest);
        		if(mapm[i].equals(interest)){
        			try {
    	                synchronized (m) {
    	                	 m.getKey().getBasicRemote().sendText(msg);
    	                }
    	            } catch (IOException e) {
    	            	map.remove(e);
    	                try {
    	                	m.getKey().close();
    	                } catch (IOException e1) {
    	                    // Ignore
    	                }
    	                broadcast(msg,interest);
    	            }
        		}
        	}
        }
    }
}



/*
RandomAccessFile file;
try {
	file = new RandomAccessFile("user_picture/IT/hh.jpg", null);
	FileChannel fc=file.getChannel();
	while(bybu.hasRemaining()){
		try {
			fc.write(bybu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} catch (FileNotFoundException e) {
	e.printStackTrace();
}
*/
package HH.Signinin.java;

import java.io.IOException;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/WebSocketsignin")
public class Signinin {
	@OnMessage
    public void echoTextMessage(Session session, String msg) {
        try {
            if (session.isOpen()) {
            	String math[]=msg.split("\\|");
            	int username=Integer.parseInt(math[0]);
            	String password=math[1];
            	
            	boolean jdbc_back=Signinjdbc.Sign_Verification(username, password);
            	System.out.print(jdbc_back);
            	if(jdbc_back==true){
            		session.getBasicRemote().sendText("0");
            	}
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
            	
            }
        }
    }
	@OnError
	public void onError(Throwable throwable,Session session){
		 
	}
}

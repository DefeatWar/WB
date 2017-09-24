package HH.home.java;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONArray;

public class Newchat {
	static String user_name(int post_id){
		String sql="SELECT interest FROM theme_table WHERE text_id="+post_id;
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		String url="jdbc:mysql://localhost:3306/hh_home?" 
		+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url);	
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery(sql);
			while (rs.next()) { 
				return rs.getString("interest"); 
			} 	
		}catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {	
				rs.close();
				ps.close();
				conn.close();				
			}catch (SQLException e) {	
				e.printStackTrace();
			}
		}
		return null;
	}
	static String[] user_message(int user_name){
		String user_nickname;
		String user_header_photo;
		String sql="SELECT user_nickname,user_header_photo FROM user_name WHERE username="+user_name;
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		String url="jdbc:mysql://localhost:3306/hh_user?" 
		+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url);	
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery(sql);
			while (rs.next()) { 
				user_nickname=rs.getString("user_nickname"); 
				user_header_photo=rs.getString("user_header_photo");
				String[] b={user_nickname,user_header_photo};
				return b;
			} 	
		}catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {	
				rs.close();
				ps.close();
				conn.close();				
			}catch (SQLException e) {	
				e.printStackTrace();
			}
		}
		return null;
	}
public static void new_chat(int post_id,int user_name,String chat_connect){
	String interest=user_name(post_id);
	String table_name=null;
	if(interest.equals("IT技术")){
		table_name="ittechnology_chat";
	}
	if(interest.equals("房产")){
		table_name="houseproperty_chat";
	}
	String[] usermessag=user_message(user_name);
	String user_nickname=usermessag[0];
	String user_header_photo=usermessag[1];
String sql=	"INSERT INTO "+table_name+"(text_id,chat_name,chat_header,chat_content) VALUES ("+post_id+",'"+user_nickname+"','"+user_header_photo+"','"+chat_connect+"');";
System.out.println(sql);
	
			Connection conn = null;
			CallableStatement ps=null;
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				String url="jdbc:mysql://localhost:3306/hh_home?" 
						+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
				conn=DriverManager.getConnection(url);	
				ps=conn.prepareCall(sql);
				ps.executeUpdate();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try{
					if(ps!=null){ps.close();} 
					if(conn!=null){conn.close();} 
				}catch(SQLException e){
					e.printStackTrace();
			}
			}
	}

	public static String sign_chat(int post_id){
		
ArrayList<String[]> chat_mess=new ArrayList<String[]>();	

		String[]chat_back = new String[1];
		chat_back[0]="chat_back";
		chat_mess.add(chat_back);
		String interest=user_name(post_id);
		String table_name=null;
		if(interest.equals("IT技术")){
			table_name="ittechnology_chat";
		}
		if(interest.equals("房产")){
			table_name="houseproperty_chat";
		}
		
		String sql="SELECT*FROM "+table_name+" WHERE text_id="+post_id+" LIMIT 10";
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		String url="jdbc:mysql://localhost:3306/hh_home?" 
		+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url);	
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery(sql);			
			while (rs.next()) { 
String[] back=new String[3];			

back[0]=rs.getString("chat_name").toString(); 
back[1]=rs.getString("chat_header").toString(); 
back[2]=rs.getString("chat_content").toString(); 

chat_mess.add(back);
			} 
		}catch (SQLException e) {	
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {	
				rs.close();
				ps.close();
				conn.close();				
			}catch (SQLException e) {	
				e.printStackTrace();
			}
		}
		return JSONArray.fromObject(chat_mess.toArray()).toString();
	}
}
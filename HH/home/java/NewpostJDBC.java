package HH.home.java;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import net.sf.json.JSONArray;

public class NewpostJDBC {
	static String[] user_name(int sender_name){
		String user_nickname = null;
		String user_header_photo = null;
		String sql="SELECT*FROM user_name WHERE username="+sender_name;
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
		if(user_nickname!=null){
			String[] user_message={user_nickname,user_header_photo};
			return user_message;
		}else{
			return null;
		}
	}
	
	public static int Test(String sender_name,String interest,String theme_title,String theme_content,ArrayList<String> photoname){
		
		String[] arrayph=new String[5];
		for(int i=0;i<photoname.size();i++){
			arrayph[i]=photoname.get(i);
		}
		if(photoname.size()!=5){
			for(int i=0;i<7-photoname.size();i++){
				photoname.add("null");
			}
		}
		System.out.println(photoname.size());
		String[] user_message=user_name(Integer.parseInt(sender_name));
			sender_name=user_message[0];
			String user_header_photo=user_message[1];
			Connection conn = null;
			CallableStatement ps=null;
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				String url="jdbc:mysql://localhost:3306/hh_home?" 
						+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
				conn=DriverManager.getConnection(url);	
				
				ps=conn.prepareCall("{CALL theme_table_sash(?,?,?,?,?,?,?,?,?,?,?)}");
				ps.setString(1, sender_name);
				ps.setString(2, interest);
				
				ps.setString(3, user_header_photo);
				
				ps.setString(4, theme_title);
				ps.setString(5, theme_content);
				
				ps.setString(6, arrayph[0]);
				ps.setString(7, arrayph[1]);
				ps.setString(8, arrayph[2]);
				ps.setString(9, arrayph[3]);
				ps.setString(10, arrayph[4]);
				
				ps.setString(11, "@total");
				ps.registerOutParameter(11, Types.INTEGER);
				ps.executeUpdate();
				
				arrayph=null;
				
				return ps.getInt(11);
				
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
			
			
			return 0;
	}
	public static String Home_secton(int post_id){
		
String[] messageback=new String[9];	

		String sql="SELECT*FROM theme_table_copy WHERE text_id="+post_id;
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
				messageback[0]=String.valueOf(rs.getInt("text_id")); 
				messageback[1]=rs.getString("sender_name"); 
				messageback[2]=rs.getString("sender_head"); 
				messageback[3]=rs.getString("interest"); 
				messageback[4]=rs.getString("theme_title"); 
				messageback[5]=rs.getString("add_time"); 
				messageback[6]=String.valueOf(rs.getInt("good")); 
				messageback[7]=String.valueOf(rs.getInt("bad")); 
				messageback[8]=String.valueOf(rs.getInt("look")); 
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
		return JSONArray.fromObject(messageback).toString();
	}

}


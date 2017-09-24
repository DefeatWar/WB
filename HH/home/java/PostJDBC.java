package HH.home.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONArray;

public class PostJDBC {
	public static void post_look(int text_id){
		Connection conn = null;
		PreparedStatement ps=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hh_home?" 
					+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
			String sql1="CALL theme_table_look("+text_id+")";
			conn=DriverManager.getConnection(url);	
			ps=conn.prepareStatement(sql1);
			ps.executeUpdate();
		}catch (ClassNotFoundException e) {	
			e.printStackTrace();	
		}catch (SQLException e) {	
			e.printStackTrace();
		}finally {
			try{
			if(ps!=null){
				ps.close();
			} 
			if(conn!=null){
					conn.close();
			} 
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
}
	
	
	
	
	
	
	public static String[] post_message(int text_id){
		
String[] messageback=new String[15];	
		
		String sql="SELECT*FROM theme_table WHERE text_id="+text_id;
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
return messageback;
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
	public static String post_counent_back(String[] backconn){
		int text_id=Integer.parseInt(backconn[0]);		
		String sql="SELECT*FROM theme_content WHERE text_id="+text_id;
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
				backconn[9]=rs.getString("theme_content");
				backconn[10]=rs.getString("photo_src1");
				backconn[11]=rs.getString("photo_src2");
				backconn[12]=rs.getString("photo_src3");
				backconn[13]=rs.getString("photo_src4");
				backconn[14]=rs.getString("photo_src5");
return JSONArray.fromObject(backconn).toString();
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
}

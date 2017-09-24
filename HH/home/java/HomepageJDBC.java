package HH.home.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONArray;

public class HomepageJDBC {
	public static String Home_user_interest(int username){
		String interest = null;
		String sql="SELECT*FROM user_table WHERE username="+username;
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
				interest=rs.getString("user_interest"); 
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
		return interest;
	}
	public static String Home_secton(String home_interest_math){
		int text_id;
String[][] messageback=new String[10][9];
		String sender_name=null;
		String sender_head=null;
		String interest=null;
		String theme_content=null;
		String add_time=null;
		int good;
		int bad;
		int look;
		String[] homein=home_interest_math.split(",");
		String inbass="";
		for(int i=0;i<homein.length;i++){
			if(i==homein.length-1){
				inbass=inbass+"\""+homein[i]+"\"";
			}else{
				inbass=inbass+"\""+homein[i]+"\",";
			}
		}
		String sql="SELECT*FROM theme_table_copy WHERE interest IN ("+inbass+") LIMIT 10";
		System.out.println(sql);
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
int i=0;
			while (rs.next()) { 
				text_id=rs.getInt("text_id"); 
				sender_name=rs.getString("sender_name"); 
				sender_head=rs.getString("sender_head"); 
				interest=rs.getString("interest"); 
				theme_content=rs.getString("theme_title"); 
				add_time=rs.getString("add_time"); 
				good=rs.getInt("good"); 
				bad=rs.getInt("bad"); 
				look=rs.getInt("look"); 
messageback[i][0]=Integer.toString(text_id);
messageback[i][1]=sender_name;
messageback[i][2]=sender_head;
messageback[i][3]=interest;
messageback[i][4]=theme_content;
messageback[i][5]=add_time;
messageback[i][6]=Integer.toString(good);
messageback[i][7]=Integer.toString(bad);
messageback[i][8]=Integer.toString(look);
i++;
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
		//System.out.println("m"+id_max());
		return JSONArray.fromObject(messageback).toString();
	}
	
}

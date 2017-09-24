package HH.Signinin.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signinjdbc {
	public static boolean Sign_Verification(int username,String password){
		int database_username = 0;
		String database_password = null;
		
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
				database_username=rs.getInt("username"); 
				database_password=rs.getString("password"); 
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
		
		if(username==database_username && password.equals(database_password)){
			return true;
		}else{
			return false;
		}
	}
}

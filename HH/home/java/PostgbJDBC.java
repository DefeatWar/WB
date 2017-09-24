package HH.home.java;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class PostgbJDBC {
	public static int post_good(int text_id){
		Connection conn = null;
		CallableStatement ps=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hh_home?" 
					+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
			conn=DriverManager.getConnection(url);	
			
			ps=conn.prepareCall("{CALL theme_table_good(?,?)}");
			ps.setInt(1, text_id);
			ps.setString(2, "@total");
			ps.registerOutParameter(2, Types.INTEGER);
			ps.executeUpdate();

			return ps.getInt(2);
			
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
	public static int post_bad(int text_id){
		Connection conn = null;
		CallableStatement ps=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hh_home?" 
					+"user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";
			conn=DriverManager.getConnection(url);	
			
			ps=conn.prepareCall("{CALL theme_table_bad(?,?)}");
			ps.setInt(1, text_id);
			ps.setString(2, "@total");
			ps.registerOutParameter(2, Types.INTEGER);
			ps.executeUpdate();

			return ps.getInt(2);
			
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
}

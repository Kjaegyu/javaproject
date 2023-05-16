package ch20.oracle.sec09.exam01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSelectExample {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521/xe", 
				"chess", 
				"951219"
			);	
			
			//매개변수화된 SQL 문 작성
			String sql = "" +
				"SELECT userid, username, userpassword, userage, useremail " +
				"FROM users " +
				"WHERE userid=?";
			
			//PreparedStatement 얻기 및 값 지정
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "winter2");
			
			//SQL 문 실행 후, ResultSet을 통해 데이터 읽기
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {						//1개의 데이터 행을 가져왔을 경우
				User user = new User();				
				user.setUserId(rs.getString("userid"));
				user.setUserName(rs.getString("username"));
				user.setUserPassword(rs.getString("userpassword"));
				user.setUserAge(rs.getInt(4));       //컬럼 순번을 이용
				user.setUserEmail(rs.getString(5));  //컬럼 순번을 이용				
				System.out.println(user);
			} else {                           //데이터 행을 가져오지 않았을 경우
				System.out.println("사용자 아이디가 존재하지 않음");
			}
			rs.close();
			
			//PreparedStatement 닫기
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try { 
					//연결 끊기
					conn.close(); 
				} catch (SQLException e) {}
			}
		}
	}
}
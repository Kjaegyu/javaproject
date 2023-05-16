package ch20.oracle.sec12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ch20.oracle.sec09.exam02.Board;

public class BoardExample6 {
	//Field
	private Scanner scanner = new Scanner(System.in);
	private Connection conn;
	
	//Constructor
	public BoardExample6() {
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521/xe", 
				"chess", 
				"951219"
			);
		} catch(Exception e) {
			e.printStackTrace();
			exit();
		}
	}
	
	//Method	
	public void list() {
		//타이틀 및 컬럼명 출력
		System.out.println();
		System.out.println("[게시물 목록]");
		System.out.println("-----------------------------------------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
		System.out.println("-----------------------------------------------------------------------");
		
		//boards 테이블에서 게시물 정보를 가져와서 출력하기
		try {
			String sql = "" +
				"SELECT bno, btitle, bcontent, bwriter, bdate " +
				"FROM boards " + 
				"ORDER BY bno DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {		
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setBtitle(rs.getString("btitle"));
				board.setBcontent(rs.getString("bcontent"));
				board.setBwriter(rs.getString("bwriter"));
				board.setBdate(rs.getDate("bdate"));
				System.out.printf("%-6s%-12s%-16s%-40s \n", 
						board.getBno(), 
						board.getBwriter(),
						board.getBdate(),
						board.getBtitle());
			}
			rs.close();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			exit();
		}
		
		//메인 메뉴 출력
		mainMenu();
	}
	
	public void mainMenu() {
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
		System.out.print("메뉴 선택: ");
		String menuNo = scanner.nextLine();
		System.out.println();
		
		switch(menuNo) {
			case "1" -> create();
			case "2" -> read();
			case "3" -> clear();
			case "4" -> exit();
		}
	}	
	
	public void create() {
		//입력 받기
		Board board = new Board();
		System.out.println("[새 게시물 입력]");
		System.out.print("제목: "); 	
		board.setBtitle(scanner.nextLine());
		System.out.print("내용: "); 	
		board.setBcontent(scanner.nextLine());
		System.out.print("작성자: "); 	
		board.setBwriter(scanner.nextLine());
		
		//보조 메뉴 출력
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
		System.out.print("메뉴 선택: ");
		String menuNo = scanner.nextLine();
		if(menuNo.equals("1")) {
			//boards 테이블에 게시물 정보 저장
			try {
				String sql = "" +
					"INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate) " +
					"VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getBtitle());
				pstmt.setString(2, board.getBcontent());
				pstmt.setString(3, board.getBwriter());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				exit();
			}
		}
		
		//게시물 목록 출력
		list();
	}
	
	public void read() {
		//입력 받기
		System.out.println("[게시물 읽기]");
		System.out.print("bno: "); 	
		int bno = Integer.parseInt(scanner.nextLine());
		
		//boards 테이블에서 해당 게시물을 가져와 출력
		try {
			String sql = "" +
				"SELECT bno, btitle, bcontent, bwriter, bdate " +
				"FROM boards " +
				"WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setBtitle(rs.getString("btitle"));
				board.setBcontent(rs.getString("bcontent"));
				board.setBwriter(rs.getString("bwriter"));
				board.setBdate(rs.getDate("bdate"));
				System.out.println("#############");
				System.out.println("번호: " + board.getBno());
				System.out.println("제목: " + board.getBtitle());
				System.out.println("내용: " + board.getBcontent());
				System.out.println("작성자: " + board.getBwriter());
				System.out.println("날짜: " + board.getBdate());
				//보조 메뉴 출력
				System.out.println("-------------------------------------------------------------------");
				System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
				System.out.print("메뉴 선택: ");
				String menuNo = scanner.nextLine();
				System.out.println();
				
				if(menuNo.equals("1")) {
					update(board);
				} else if(menuNo.equals("2")) {
					delete(board);
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}
		
		//게시물 목록 출력
		list();
	}
	
	public void update(Board board) {
		//수정 내용 입력 받기
		System.out.println("[수정 내용 입력]");
		System.out.print("제목: "); 	
		board.setBtitle(scanner.nextLine());
		System.out.print("내용: "); 	
		board.setBcontent(scanner.nextLine());
		System.out.print("작성자: "); 	
		board.setBwriter(scanner.nextLine());
		
		//보조 메뉴 출력
		System.out.println("-------------------------------------------------------------------");
		System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
		System.out.print("메뉴 선택: ");
		String menuNo = scanner.nextLine();
		if(menuNo.equals("1")) {
			//boards 테이블에서 게시물 정보 수정
			try {
				String sql = "" +
					"UPDATE boards SET btitle=?, bcontent=?, bwriter=? " +
					"WHERE bno=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getBtitle());
				pstmt.setString(2, board.getBcontent());
				pstmt.setString(3, board.getBwriter());
				pstmt.setInt(4, board.getBno());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				exit();
			}
		}
		
		//게시물 목록 출력
		list();
	}
	
	public void delete(Board board) {
		System.out.println("*** delete() 메소드 실행됨");
		list();
	}
	
	public void clear() {
		System.out.println("*** clear() 메소드 실행됨");
		list();
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public static void main(String[] args) {
		BoardExample6 boardExample = new BoardExample6();
		boardExample.list();
	}
}
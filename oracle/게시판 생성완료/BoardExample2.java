package ch20.oracle.sec12;

import java.util.Scanner;

public class BoardExample2 {
	//Field
	private Scanner scanner = new Scanner(System.in);
	
	//Constructor
	
	//Method	
	public void list() {
		System.out.println();
		System.out.println("[게시물 목록]");
		System.out.println("-----------------------------------------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
		System.out.println("-----------------------------------------------------------------------");
		System.out.printf("%-6s%-12s%-16s%-40s \n", 
				"1", "winter", "2022.05.14", "게시판에 오신 것을 환영합니다.");
		System.out.printf("%-6s%-12s%-16s%-40s \n", 
				"2", "winter", "2023.05.14", "5월은 일교차가 심하니 감기조심 하세요.");
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
		System.out.println("*** create() 메소드 실행됨");
		list();
	}
	
	public void read() {
		System.out.println("*** read() 메소드 실행됨");
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
		BoardExample2 boardExample = new BoardExample2();
		boardExample.list();
	}
}
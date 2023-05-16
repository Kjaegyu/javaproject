package ch20.oracle.sec09.exam01;

import lombok.Data;
	
@Data //Constructor, Getter, Setter, hashCode(), equals(), toString() 자동 생성
public class User {
	private String userId;
	private String userName;
	private String userPassword;
	private int userAge;
	private String userEmail;
}
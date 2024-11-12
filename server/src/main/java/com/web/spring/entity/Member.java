package com.web.spring.entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Member {



	private String id;
	private String pwd;//길이를 주면 안됨..암호화때 길어진다
    private String name;

    //추가
    private String role;// ROLE_CHILD, ROEL_PARENT
    
    private Long memberNum;
 
 


    /*
     * 아래 코드는 react에서 role을 가지고 구분하고서 해당 num 값을 받기 위해서 존재하는 코드 react코드를 확인 후 변경예정
     * */
   

	@Override
	public String toString() {
		return "Member [Id=" + id + ", =" + pwd + ", name=" + name + ", role=" + role
				+ ", memberNum=" + memberNum + "]";
	}




	public Member(String id, String name, String role, Long memberNum) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.memberNum = memberNum;
	}
    
	
	

  
    

}

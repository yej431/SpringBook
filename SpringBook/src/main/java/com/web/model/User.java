package com.web.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM : Java나 다른언어의 Object를 테이블로 매핑해주는 기능.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert //insert할 때 null인 필드 제외
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//jpa 넘버링 전략을 따라가지 않고, 프로젝트에 연결된 DB의 넘버링 전략을 따라가게 한다.
	//오라클 사용 시 IDENTITY는 시퀀스가 되고 MySQL을 사용하면 auto_increment가 된다.
	private int id;
	
	@Column(nullable=false, length=100, unique=true)
	private String userId;
	
	@Column(nullable=false, length=100) //해시로 비밀번호를 암호화하기때문에 length값을 넉넉히 준다.
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	private String phone;
	
	//@ColumnDefault("user")
	@Enumerated(EnumType.STRING) //DB는 RoleType이 없으므로 해당 Enum이 스트링이라는것을 알려준다.
	private RoleType role;
	
	private String oauth; 
	
	private String oauthId;
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
}

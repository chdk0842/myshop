package com.myshop.entity;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart") // 테이블명
@Getter
@Setter
@ToString
public class Cart {
    
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY) //지연로딩 상태
	@JoinColumn(name = "member_id")
	private Member member;
     
}
package com.myshop.entity;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_item") // 테이블명
@Getter
@Setter
@ToString
public class CartItem {

	@Id
	@Column(name = "cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩 상태
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩 상태
	@JoinColumn(name = "item_id")
	private Item item;

	private int count;
	
}

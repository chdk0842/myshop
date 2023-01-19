package com.myshop.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_item") // 테이블명
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity{
	
	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩 상태
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY) //지연로딩 상태
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice;

	private int count;
	
	
}

package com.myshop.entity;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(value = { AuditingEntityListener.class }) // Auditing을 적용하기 위해
@MappedSuperclass // 부모클래스를 상속받는 자식 클래스한테 매핑정보만 제공
@Getter
public class BaseEntity extends BaseTimeEntity {
	
	@CreatedBy
	@Column(updatable = false) //등록만 되고 수정 안됨.
	private String createBy; //등록자
    
	@LastModifiedBy
	private String modifiedBy; //수정자
}

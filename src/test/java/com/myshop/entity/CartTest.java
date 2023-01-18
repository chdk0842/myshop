package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.myshop.dto.MemberFormDto;
import com.myshop.repository.CartRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.service.MemberService;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncorder;
	
	@PersistenceContext
	EntityManager em;
	
	public Member createMember() {
		MemberFormDto member = new MemberFormDto();
		member.setName("신초아");
		member.setEmail("test@email.com");
		member.setAddress("경기도 포천시");
		member.setPassword("1234");
		
		return Member.createMember(member, passwordEncorder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findcartAndMemberTest() {
		Member member = createMember();
		memberRepository.save(member);
		
		Cart cart = new Cart();
		cart.setMember(member);
		cartRepository.save(cart);
		
		em.flush(); //트랜젝션이 끝날때 데이터 베이스에 반영
		em.clear(); //영속성 컨텍스트를 비워준다 -> 실제 데이터 베이스에서 장바구니를 엔티티를 가지고 올 때 회원 엔티티도 같이 가지고 오는지 보기 위해.
		
		Cart savedCart = cartRepository.findById(cart.getId())
				         .orElseThrow(EntityNotFoundException::new);

		assertEquals(savedCart.getMember().getId(), member.getId());

}
}

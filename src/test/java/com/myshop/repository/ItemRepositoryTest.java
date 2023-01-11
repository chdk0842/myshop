package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.mysql.cj.x.protobuf.MysqlxCrud.Limit;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

	@Autowired // 생성된 해당 클래스 타입 객체의 인스턴스를 연결함. 의존성주입
	ItemRepository itemRepository;

	@PersistenceContext // 영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em; // 엔티티 매니저

	/*
	 * @Test //메소드를 테스트 하겠다
	 * 
	 * @DisplayName("상품 저장 테스트") //테스트코드 네임
	 */
	/*
	 * public void createItemTest() { Item item = new Item();
	 * item.setItemNm("테스트상품"); item.setPrice(10000);
	 * item.setItemDetail("테스트 상품 상세 설명");
	 * item.setItemSellStatus(ItemSellStatus.SELL); item.setStockNumber(100);
	 * item.setRegTime(LocalDateTime.now());
	 * item.setUpdateTime(LocalDateTime.now());
	 * 
	 * Item savedItem = itemRepository.save(item); // save(데이터 insert 역할)
	 * 
	 * System.out.println(savedItem.toString()); }
	 */

	public void createItemTest() {

		for (int i = 1; i <= 10; i++) {

			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());

			Item savedItem = itemRepository.save(item); // save(데이터 insert 역할)

		}
	}

	@Test
	@DisplayName("상품명 조회 테스트")

	public void findByItemNmTest() {
		this.createItemTest(); // item 테이블에 데이터 10개 insert
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품2");

		for (Item item : itemList) {
			System.out.println(item.toString());
		}

	}

	@Test
	@DisplayName("상품명, 상품상세설명 or 테스트")
	public void findByItemNmOrItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Test
	@DisplayName("가격 LessThan 테스트")

	public void findByPriceLessThan() {
		this.createItemTest(); // item 테이블에 데이터 10개 insert
		List<Item> itemList = itemRepository.findByPriceLessThan(10005);

		for (Item item : itemList) {
			System.out.println(item.toString());
		}

	}

	@Test
	@DisplayName("가격 내림차순 조회 테스트")

	public void findByPriceLessThanOrderByPriceDesc() {
		this.createItemTest(); // item 테이블에 데이터 10개 insert
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

		for (Item item : itemList) {
			System.out.println(item.toString());
		}

	}

	/*
	 * // 퀴즈1-1
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈1-1") public void findByItemNmAndItemSellStatus() {
	 * this.createItemTest(); // item 테이블에 데이터 10개 insert
	 * 
	 * List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1",
	 * ItemSellStatus.SELL);
	 * 
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 * // 퀴즈1-2
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈1-2") public void findByPriceBetween() {
	 * this.createItemTest(); // item 테이블에 데이터 10개 insert
	 * 
	 * List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
	 * 
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 * // 퀴즈1-3
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈1-3") public void findByRegTimeBefore() {
	 * this.createItemTest(); // item 테이블에 데이터 10개 insert
	 * 
	 * List<Item> itemList =
	 * itemRepository.findByRegTimeAfter(LocalDateTime.of(2023, 01, 01, 12, 12,
	 * 44));
	 * 
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 * // 퀴즈1-4
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈1-4") public void findByItemSellStatusNotNull() {
	 * this.createItemTest(); // item 테이블에 데이터 10개 insert
	 * 
	 * List<Item> itemList = itemRepository.findByItemSellStatusNotNull();
	 * 
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 * // 퀴즈1-5
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈1-5") public void findByItemDetailEndingWith() {
	 * this.createItemTest(); // item 테이블에 데이터 10개 insert
	 * 
	 * List<Item> itemList = itemRepository.findByItemDetailEndingWith("상품1");
	 * 
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 */

	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}

	}

	@Test
	@DisplayName("@native Query를 이용한 상품 조회 테스트")
	public void findByItemDetailByNativeTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}

	}

	/*
	 * //퀴즈2-1
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈2-1") public void findByPriceGreaterThanTest() {
	 * this.createItemTest(); List<Item> itemList =
	 * itemRepository.findByPriceGreaterThan(10005); for (Item item : itemList) {
	 * 
	 * System.out.println(item.toString());
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * 
	 * //퀴즈2-2
	 * 
	 * @Test
	 * 
	 * @DisplayName("퀴즈2-2") public void findByItemNmitemSellStatusTest() {
	 * this.createItemTest(); List<Item> itemList =
	 * itemRepository.findByItemNmitemSellStatus("테스트 상품1", ItemSellStatus.SELL);
	 * for (Item item : itemList) { System.out.println(item.toString()); }
	 * 
	 * }
	 * 
	 */
	
	@Test
	@DisplayName("querydsl 조회 테스트")
	public void queryDslTest() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		//select * from item where itemSellStatus = 'sell' and itemDetail like %테스트 상품 상세 설명% order by price desc 
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
				.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
				.orderBy(qItem.price.desc()); //가격 내림차순으로 정렬
		
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("querydsl 조회 테스트2")
	public void queryDslTest2() {
		this.createItemTest2();
		
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		Pageable page = PageRequest.of(0, 2); //of(조회할 페이지의 번호, 한 페이지당 조회할 데이터의 갯수)
		
		//select * from item where itemSellStatus = 'sell' and itemDetail like %테스트 상품 상세 설명% order by price desc 
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
				.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
				.where(qItem.price.gt(10003))
				.offset(page.getOffset())
		        .limit(page.getPageSize());
		
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
			  
	}

	
	public void createItemTest2() {

		for (int i = 1; i <= 5; i++) {

			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());

			Item savedItem = itemRepository.save(item); // save(데이터 insert 역할)

		}
		
		for (int i = 6; i <= 10; i++) {

			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());

			Item savedItem = itemRepository.save(item); // save(데이터 insert 역할)

		}
	}
	
	@Test
	@DisplayName("퀴즈3-1")
	public void queryDslTest3_1() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
		
		//select * from item where item_nm = ? || itemSellStatus = SELL
		////select * from item where itemSellStatus = 'sell' and itemDetail like %테스트 상품 상세 설명% order by price desc 
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.itemNm.like("테스트 상품1"))
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
				
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	
	@Test
	@DisplayName("퀴즈3-2")
	public void queryDslTest3_2() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
			
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.price.between(10004, 10008));
				
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	
	@Test
	@DisplayName("퀴즈3-3")
	public void queryDslTest3_3() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
			
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.regTime.after(LocalDateTime.of(2023,1,1,12,12,44)));
				
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Test
	@DisplayName("퀴즈3-4")
	public void queryDslTest3_4() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
			
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.isNotNull());
				
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Test
	@DisplayName("퀴즈3-5")
	public void queryDslTest3_5() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체
		QItem qItem = QItem.item;
			
		JPAQuery<Item> query = 
				qf.selectFrom(qItem)
				.where(qItem.itemDetail.like("%설명1"));
				
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	

}

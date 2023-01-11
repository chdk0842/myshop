package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;

//JpaRepository : 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의가 되어 있다.
//JpaRepository<사용할 엔티티 클래스, 엔티티 클래스 기본 타입>
public interface ItemRepository extends JpaRepository<Item, Long> {

	// select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);

	// select * from item where item_nm = ? or item_detail = ?
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

	// select * from item where price < ?
	List<Item> findByPriceLessThan(Integer price);

	// select * from item where price < ? by price desc
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

	/*
	// 퀴즈1-1
	// select * from item where item_nm = ? || itemSellStatus = SELL
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus ItemSellStatus);

	// 퀴즈1-2
	// select * from item where price <= 10004 || price >= 10008
	List<Item> findByPriceBetween(Integer price, Integer price1);

	// 퀴즈1-3
	// select * from item where regTime < 2023-1-1 12:12:44
	List<Item> findByRegTimeAfter(LocalDateTime regTime);

	// 퀴즈1-4
	// select * from item where ItemSell not null
	List<Item> findByItemSellStatusNotNull();

	// 퀴즈1-5
	// select * from item like ItemDetail(%설명1) 
	List<Item> findByItemDetailEndingWith(String ItemDetail);
	*/
	
	/*
	 * @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc"
	 * ) List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	 */
	
	@Query("select i from Item i where i.itemDetail like %?1% order by i.price desc")
	List<Item> findByItemDetail(String itemDetail);
	
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	//퀴즈2-1
	
	  @Query(value = "select * from item i where i.price >= :price", nativeQuery = true)
      List<Item> findByPriceGreaterThan(@Param("price") Integer price);
	
	
	//퀴즈2-2
	/*
	 * @Query("select i from Item i where i.itemNm in :itemNm and i.itemSellStatus in :itemSellStatus"
	 * ) List<Item> findByItemNmitemSellStatus(@Param("itemNm") String
	 * itemNm, @Param("itemSellStatus") ItemSellStatus itemSellStatus);
	 */

}

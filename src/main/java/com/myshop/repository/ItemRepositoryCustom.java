package com.myshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;

//1. 사용자 정의 인터페이스
public interface ItemRepositoryCustom {
     Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

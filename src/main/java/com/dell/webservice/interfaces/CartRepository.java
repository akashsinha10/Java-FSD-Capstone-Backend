package com.dell.webservice.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dell.webservice.entity.Cart;
import com.dell.webservice.entity.User;


public interface CartRepository extends PagingAndSortingRepository<Cart,Integer>{
	Page<Cart> findByUserContaining(User user, Pageable pageable);
}

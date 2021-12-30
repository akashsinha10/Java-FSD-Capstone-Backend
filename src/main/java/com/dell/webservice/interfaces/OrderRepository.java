package com.dell.webservice.interfaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dell.webservice.entity.Order;
import com.dell.webservice.entity.User;

public interface OrderRepository extends PagingAndSortingRepository<Order,Integer>{
	Page<Order> findByUserContaining(User user, Pageable pageable);
}

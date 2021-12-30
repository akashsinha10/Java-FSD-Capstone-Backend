package com.dell.webservice.interfaces;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dell.webservice.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User,Integer>{
}

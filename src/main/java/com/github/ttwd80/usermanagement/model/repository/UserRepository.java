package com.github.ttwd80.usermanagement.model.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.github.ttwd80.usermanagement.model.entity.User;

public interface UserRepository extends ElasticsearchRepository<User, String> {

}

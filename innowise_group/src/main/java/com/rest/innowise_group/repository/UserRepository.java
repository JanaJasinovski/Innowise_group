package com.rest.innowise_group.repository;

import com.rest.innowise_group.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

    Boolean existsByEmail(String email);}

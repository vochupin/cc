package com.example.application.data.service;

import com.example.application.data.entity.Client;
import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    User findByFirstname(String firstname);
    User findByLastname(String lastname);
}

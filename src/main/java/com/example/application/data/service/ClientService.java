package com.example.application.data.service;

import com.example.application.data.entity.Client;
import com.example.application.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> get(Long id) {
        return repository.findById(id);
    }

    public Client update(Client entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Client> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Client> list(Pageable pageable, Specification<Client> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Client> all() {
        return repository.findAll();
    }
}

package com.oliveiradev.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oliveiradev.picpaysimplificado.domain.user.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);

    Optional<User> findUserById(Long id);
}

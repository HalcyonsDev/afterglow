package com.halcyon.afterglow.repository;

import com.halcyon.afterglow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.firstname = ?1 WHERE user.id = ?2")
    void updateFirstnameById(String firstname, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.lastname = ?1 WHERE user.id = ?2")
    void updateLastnameById(String lastname, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.description = ?1 WHERE user.id = ?2")
    void updateDescriptionById(String description, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.firstname = ?1, user.lastname = ?2, user.description = ?3 WHERE user.email = ?4")
    void updateDataByEmail(String firstname, String lastname, String description, String email);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
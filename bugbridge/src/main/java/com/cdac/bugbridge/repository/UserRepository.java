package com.cdac.bugbridge.repository;

import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.util.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.email = :newEmail, u.role = :role WHERE u.id = :userId")
    int updateById(@Param("userId") Long uniqueId, @Param("newEmail") String newEmail,
            @Param("name") String name, @Param("role") UserRole role);

    @Modifying
    @Query("DELETE from User u WHERE u.id = :userId")
    int deleteUserById(@Param("userId") Long userId);


    List<User> findByRole(UserRole role);

}

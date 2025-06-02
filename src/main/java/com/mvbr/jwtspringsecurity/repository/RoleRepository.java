package com.mvbr.jwtspringsecurity.repository;

import com.mvbr.jwtspringsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);

    @Query(value = "select * from roles where name = :name limit 1", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);

}

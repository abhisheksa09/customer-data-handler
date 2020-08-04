package com.tcs.customer.data.management.repository;

import com.tcs.customer.data.management.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("select u from PersonEntity u where (:firstName is null or u.firstName = :firstName)"
            +" and (:lastName is null or u.lastName = :lastName)")
    List<PersonEntity> searchByFirstAndOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}

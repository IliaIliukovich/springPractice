package com.telran.springpractice.repository;

import com.telran.springpractice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastNameStartingWithAndAddressContaining(String lastName, String address);

    @Query("delete from Client c where c.status = 'INACTIVE'")
    @Modifying
    int customQuery();

}

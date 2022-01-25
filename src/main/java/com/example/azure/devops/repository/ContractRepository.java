package com.example.azure.devops.repository;

import com.example.azure.devops.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(
            value = "select * from contract as u where u.name = :name",
            nativeQuery = true)
    Contract findByNameNativeQuery(@Param("name") String name);
}

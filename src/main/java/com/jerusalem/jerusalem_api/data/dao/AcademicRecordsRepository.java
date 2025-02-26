package com.jerusalem.jerusalem_api.data.dao;


import com.jerusalem.jerusalem_api.data.vo.AcademicRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicRecordsRepository extends JpaRepository<AcademicRecords, Long>, RepositoryCustom {
    // Custom query to find academic records by user ID
    @Query("SELECT ar FROM AcademicRecords ar WHERE ar.user.id = :userId")
    Optional<AcademicRecords> findByUserId(@Param("userId") Long userId);

}

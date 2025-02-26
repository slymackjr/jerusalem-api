package com.jerusalem.jerusalem_api.data.dao;

import com.jerusalem.jerusalem_api.data.vo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectsRepository extends JpaRepository<Subject, Long>, RepositoryCustom {

}
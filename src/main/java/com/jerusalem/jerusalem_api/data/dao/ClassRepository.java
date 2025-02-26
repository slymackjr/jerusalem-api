package com.jerusalem.jerusalem_api.data.dao;

import com.jerusalem.jerusalem_api.data.vo.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>, RepositoryCustom {

}

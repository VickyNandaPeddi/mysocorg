package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.Sectors;

@Repository
public interface SectorsRepository extends JpaRepository<Sectors, Long> {

}

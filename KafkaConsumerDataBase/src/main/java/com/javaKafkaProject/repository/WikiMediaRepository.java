package com.javaKafkaProject.repository;

import com.javaKafkaProject.entity.WikiMediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiMediaRepository extends JpaRepository<WikiMediaData,Long> {
}

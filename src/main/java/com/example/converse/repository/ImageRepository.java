package com.example.converse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.converse.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}

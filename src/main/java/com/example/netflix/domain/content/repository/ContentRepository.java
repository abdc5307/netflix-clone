package com.example.netflix.domain.content.repository;

import com.example.netflix.domain.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
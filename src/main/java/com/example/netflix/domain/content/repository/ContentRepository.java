package com.example.netflix.domain.content.repository;

import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.content.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT c FROM Content c WHERE " +
            "(:genre IS NULL OR c.genre = :genre) AND " +
            "(:keyword IS NULL OR c.title LIKE %:keyword% OR c.description LIKE %:keyword%)")
    Page<Content> searchContents(@Param("genre") Genre genre,
                                 @Param("keyword") String keyword,
                                 Pageable pageable);
}
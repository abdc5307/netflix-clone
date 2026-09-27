package com.example.netflix.domain.wishlist.repository;

import com.example.netflix.domain.wishlist.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    // 찜 여부 확인
    boolean existsByUserIdAndContentId(Long userId, Long contentId);

    // 찜 단건 조회 (삭제용)
    Optional<Wishlist> findByUserIdAndContentId(Long userId, Long contentId);

    // 사용자의 마이리스트 목록 조회 (최신 찜한 순 페이징)
    Page<Wishlist> findAllByUserId(Long userId, Pageable pageable);
}
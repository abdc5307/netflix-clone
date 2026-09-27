package com.example.netflix.domain.wishlist.service;

import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.content.repository.ContentRepository;
import com.example.netflix.domain.user.entity.User;
import com.example.netflix.domain.user.repository.UserRepository;
import com.example.netflix.domain.wishlist.dto.WishlistResponseDto;
import com.example.netflix.domain.wishlist.entity.Wishlist;
import com.example.netflix.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    // 1. 찜 추가
    @Transactional
    public void addWishlist(String userEmail, Long contentId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));

        if (wishlistRepository.existsByUserIdAndContentId(user.getId(), content.getId())) {
            throw new IllegalArgumentException("이미 찜한 콘텐츠입니다.");
        }

        wishlistRepository.save(Wishlist.builder()
                .user(user)
                .content(content)
                .build());
    }

    // 2. 찜 삭제
    @Transactional
    public void deleteWishlist(String userEmail, Long contentId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Wishlist wishlist = wishlistRepository.findByUserIdAndContentId(user.getId(), contentId)
                .orElseThrow(() -> new IllegalArgumentException("마이리스트에 없는 콘텐츠입니다."));

        wishlistRepository.delete(wishlist);
    }

    // 3. 내 마이리스트 조회
    public Page<WishlistResponseDto> getMyWishlist(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return wishlistRepository.findAllByUserId(user.getId(), pageable)
                .map(WishlistResponseDto::new);
    }
}
package com.example.netflix.domain.wishlist.controller;

import com.example.netflix.domain.wishlist.dto.WishlistResponseDto;
import com.example.netflix.domain.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wishlist API", description = "마이리스트(찜하기) 관련 API")
@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @Operation(summary = "마이리스트에 콘텐츠 추가")
    @PostMapping("/{contentId}")
    public ResponseEntity<String> addWishlist(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long contentId) {
        wishlistService.addWishlist(userDetails.getUsername(), contentId);
        return ResponseEntity.ok("마이리스트에 추가되었습니다.");
    }

    @Operation(summary = "마이리스트에서 콘텐츠 제거")
    @DeleteMapping("/{contentId}")
    public ResponseEntity<String> deleteWishlist(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long contentId) {
        wishlistService.deleteWishlist(userDetails.getUsername(), contentId);
        return ResponseEntity.ok("마이리스트에서 삭제되었습니다.");
    }

    @Operation(summary = "내 마이리스트 조회")
    @GetMapping
    public ResponseEntity<Page<WishlistResponseDto>> getMyWishlist(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(wishlistService.getMyWishlist(userDetails.getUsername(), pageable));
    }
}
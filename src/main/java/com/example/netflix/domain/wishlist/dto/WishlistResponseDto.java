package com.example.netflix.domain.wishlist.dto;

import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.wishlist.entity.Wishlist;
import lombok.Getter;

@Getter
public class WishlistResponseDto {
    private final Long wishlistId;
    private final Long contentId;
    private final String title;
    private final String thumbnailUrl;

    public WishlistResponseDto(Wishlist wishlist) {
        Content content = wishlist.getContent();
        this.wishlistId = wishlist.getId();
        this.contentId = content.getId();
        this.title = content.getTitle();
        this.thumbnailUrl = content.getThumbnailUrl();
    }
}
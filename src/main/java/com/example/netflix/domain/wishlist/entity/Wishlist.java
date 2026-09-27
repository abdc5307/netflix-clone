package com.example.netflix.domain.wishlist.entity;

import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "wishlists",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "content_id"}) // 한 유저가 같은 콘텐츠 중복 찜 방지
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    private LocalDateTime createdAt;

    @Builder
    public Wishlist(User user, Content content) {
        this.user = user;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
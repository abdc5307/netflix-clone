package com.example.netflix.domain.content.dto;

import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.content.entity.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ContentDto {

    // 등록 및 수정 요청 시 사용하는 DTO
    @Getter
    @NoArgsConstructor
    public static class Request {
        private String title;
        private String description;
        private String thumbnailUrl;
        private String videoUrl;
        private String category;
        private Genre genre;

        public Content toEntity() {
            return Content.builder()
                    .title(this.title)
                    .description(this.description)
                    .thumbnailUrl(this.thumbnailUrl)
                    .videoUrl(this.videoUrl)
                    .category(this.category)
                    .genre(this.genre)
                    .build();
        }
    }

    // 조회 응답 시 사용하는 DTO
    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String thumbnailUrl;
        private String videoUrl;
        private String category;
        private Genre genre;
        private LocalDateTime createdAt;

        public Response(Content content) {
            this.id = content.getId();
            this.title = content.getTitle();
            this.description = content.getDescription();
            this.thumbnailUrl = content.getThumbnailUrl();
            this.videoUrl = content.getVideoUrl();
            this.category = content.getCategory();
            this.genre = content.getGenre();
            this.createdAt = content.getCreatedAt();
        }
    }
}
package com.example.netflix.domain.content.dto;

import com.example.netflix.domain.content.entity.Content;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ContentDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String title;
        private String description;
        private String thumbnailUrl;
        private String videoUrl;
        private String category;
    }

    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String thumbnailUrl;
        private String videoUrl;
        private String category;

        public Response(Content content) {
            this.id = content.getId();
            this.title = content.getTitle();
            this.description = content.getDescription();
            this.thumbnailUrl = content.getThumbnailUrl();
            this.videoUrl = content.getVideoUrl();
            this.category = content.getCategory();
        }
    }
}
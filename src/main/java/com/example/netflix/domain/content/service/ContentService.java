package com.example.netflix.domain.content.service;

import com.example.netflix.domain.content.dto.ContentDto;
import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public Long createContent(ContentDto.Request dto) {
        Content content = Content.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .thumbnailUrl(dto.getThumbnailUrl())
                .videoUrl(dto.getVideoUrl())
                .category(dto.getCategory())
                .build();
        return contentRepository.save(content).getId();
    }

    @Transactional(readOnly = true)
    public List<ContentDto.Response> getAllContents() {
        return contentRepository.findAll().stream()
                .map(ContentDto.Response::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContentDto.Response getContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다."));
        return new ContentDto.Response(content);
    }

    @Transactional
    public void updateContent(Long id, ContentDto.Request dto) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다."));
        content.update(dto.getTitle(), dto.getDescription(), dto.getThumbnailUrl(), dto.getVideoUrl(), dto.getCategory());
    }

    @Transactional
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }
}

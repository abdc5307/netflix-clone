package com.example.netflix.domain.content.service;

import com.example.netflix.domain.content.dto.ContentDto;
import com.example.netflix.domain.content.entity.Content;
import com.example.netflix.domain.content.entity.Genre;
import com.example.netflix.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;

    @Transactional
    public Long createContent(ContentDto.Request requestDto) {
        return contentRepository.save(requestDto.toEntity()).getId();
    }

    public ContentDto.Response getContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));
        return new ContentDto.Response(content);
    }

    // 3주차: 검색, 장르 필터링, 페이징 및 정렬 조회
    public Page<ContentDto.Response> getContentsWithPaging(Genre genre, String keyword, Pageable pageable) {
        return contentRepository.searchContents(genre, keyword, pageable)
                .map(ContentDto.Response::new);
    }

    @Transactional
    public Long updateContent(Long id, ContentDto.Request requestDto) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));

        content.update(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getThumbnailUrl(),
                requestDto.getVideoUrl(),
                requestDto.getCategory(),
                requestDto.getGenre()
        );
        return content.getId();
    }

    @Transactional
    public void deleteContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));
        contentRepository.delete(content);
    }
}
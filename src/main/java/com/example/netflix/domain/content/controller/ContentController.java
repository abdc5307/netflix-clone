package com.example.netflix.domain.content.controller;

import com.example.netflix.domain.content.dto.ContentDto;
import com.example.netflix.domain.content.entity.Genre;
import com.example.netflix.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    // 1. 콘텐츠 등록 (관리자)
    @PostMapping
    public ResponseEntity<Long> createContent(@RequestBody ContentDto.Request requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createContent(requestDto));
    }

    // 2. 콘텐츠 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ContentDto.Response> getContent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contentService.getContent(id));
    }

    // 3. 검색어, 장르 필터, 페이징/정렬 지원 목록 조회
    // 예시: GET /api/contents?genre=ROMANCE&keyword=청춘&page=0&size=10&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<Page<ContentDto.Response>> getContents(
            @RequestParam(name = "genre", required = false) Genre genre,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(contentService.getContentsWithPaging(genre, keyword, pageable));
    }

    // 4. 콘텐츠 수정 (관리자)
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateContent(@PathVariable("id") Long id, @RequestBody ContentDto.Request requestDto) {
        return ResponseEntity.ok(contentService.updateContent(id, requestDto));
    }

    // 5. 콘텐츠 삭제 (관리자)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable("id") Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok("콘텐츠가 삭제되었습니다.");
    }
}

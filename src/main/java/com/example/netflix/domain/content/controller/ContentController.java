package com.example.netflix.domain.content.controller;

import com.example.netflix.domain.content.dto.ContentDto;
import com.example.netflix.domain.content.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    // 1. 등록 (ADMIN만 가능)
    @PostMapping
    public ResponseEntity<Long> createContent(@RequestBody @Valid ContentDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createContent(request));
    }

    // 2. 전체 목록 조회 (누구나 가능)
    @GetMapping
    public ResponseEntity<List<ContentDto.Response>> getAllContents() {
        return ResponseEntity.ok(contentService.getAllContents());
    }

    // 3. 상세 조회 (누구나 가능)
    @GetMapping("/{id}")
    public ResponseEntity<ContentDto.Response> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContent(id));
    }

    // 4. 수정 (ADMIN만 가능)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContent(@PathVariable Long id, @RequestBody @Valid ContentDto.Request request) {
        contentService.updateContent(id, request);
        return ResponseEntity.ok("콘텐츠가 수정되었습니다.");
    }

    // 5. 삭제 (ADMIN만 가능)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok("콘텐츠가 삭제되었습니다.");
    }
}

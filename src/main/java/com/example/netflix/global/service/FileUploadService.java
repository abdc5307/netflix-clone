package com.example.netflix.global.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 비어 있습니다.");
        }

        // 1. 프로젝트 기준의 '절대 경로'로 디렉터리 설정 및 생성
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        File directory = uploadPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 2. 고유한 파일명 생성 (UUID + 원본 파일 확장자)
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String savedFilename = UUID.randomUUID() + extension;

        // 3. 절대 경로로 파일 저장 (임시 폴더로 튀는 현상 방지)
        File targetFile = uploadPath.resolve(savedFilename).toFile();
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }

        // 4. 접근 가능한 상대 URL 반환
        return "/images/" + savedFilename;
    }
}
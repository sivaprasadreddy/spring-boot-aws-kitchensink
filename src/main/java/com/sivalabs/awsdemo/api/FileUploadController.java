package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.domain.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FileUploadController {
    private final FileStorageService fileStorageService;

    @PostMapping("/fileuploads")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        var filename = multipartFile.getOriginalFilename();
        var extn = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID()+extn;
        fileStorageService.upload(filename, multipartFile.getInputStream());
        Map<String, String> response = Map.of(
                "status", "success",
                "filename", filename
        );
        return ResponseEntity.ok(response);
    }
}

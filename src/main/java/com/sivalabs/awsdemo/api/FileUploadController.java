package com.sivalabs.awsdemo.api;

import com.sivalabs.awsdemo.domain.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FileUploadController {
    private final FileStorageService fileStorageService;

    @GetMapping("/fileuploads")
    public String docs() {
        return "fileuploads";
    }

    @PostMapping("/fileuploads")
    public String uploadDocs(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        var filename = multipartFile.getOriginalFilename();
        var extn = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID()+extn;
        fileStorageService.upload(filename, multipartFile.getInputStream());
        return "redirect:/fileuploads";
    }
}

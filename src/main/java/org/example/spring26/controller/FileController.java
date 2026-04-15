package org.example.spring26.controller;

import org.example.spring26.service.S3Service;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileController.class);

    private final S3Service s3Service;

    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/files")
    public String filesPage(Model model, @AuthenticationPrincipal OAuth2User user, CsrfToken csrfToken) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("displayName", user.getAttribute("name"));
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("csrfToken", csrfToken.getToken());
        return "files";
    }

    @GetMapping("/fileinfo")
    public String fileInfo(Model model, @AuthenticationPrincipal OAuth2User user, CsrfToken csrfToken) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("displayName", user.getAttribute("name"));
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("csrfToken", csrfToken.getToken());
        model.addAttribute("fileInfo", s3Service.fileInfo());
        return "fileinfo";
    }

    @GetMapping("/api/files")
    @ResponseBody
    public List<String> listFiles() {
        return s3Service.listFiles();
    }

    @GetMapping("/api/files/upload-url")
    @ResponseBody
    public Map<String, String> getUploadUrl(@RequestParam String fileName, @RequestParam String contentType) {
        String url = s3Service.generatePresignedUploadUrl(fileName, contentType);
        return Map.of("url", url);
    }

    @GetMapping("/api/files/download-url")
    @ResponseBody
    public Map<String, String> getDownloadUrl(@RequestParam String fileName) {
        String url = s3Service.generatePresignedDownloadUrl(fileName);
        return Map.of("url", url);
    }

    @PostMapping("/api/files/callback")
    @ResponseBody
    public Map<String, String> uploadCallback(@RequestParam String fileName) {
        log.info("Callback received: File {} has been uploaded successfully", fileName);
        // Here you could perform further processing, like saving metadata to a database
        return Map.of("status", "success", "message", "Callback received for " + fileName);
    }
}

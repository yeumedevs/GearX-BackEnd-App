package com.gearx.feature.media.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.gearx.common.response.ApiResponse;
import com.gearx.common.response.ResponseHandler;
import com.gearx.feature.media.util.UploadMedia;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class FileUploadController {

    private final UploadMedia uploadMedia;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<String>>> upload(
            @RequestParam("files") MultipartFile[] files) {
        String[] urls = uploadMedia.uploadMedia(files);
        return ResponseHandler.success(Arrays.asList(urls));
    }
}

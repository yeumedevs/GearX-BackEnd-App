package com.gearx.feature.media.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadMedia implements com.gearx.feature.media.util.UploadMedia {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder:gearx/products}")
    private String folder;

    @Override
    public String[] uploadMedia(MultipartFile[] files) {
        if (files == null || files.length == 0) return new String[0];

        List<String> urls = new ArrayList<>(files.length);
        for (MultipartFile f : files) {
            if (f == null || f.isEmpty()) continue;
            try {
                Map<?, ?> res =
                        cloudinary
                                .uploader()
                                .upload(
                                        f.getBytes(),
                                        // Dùng resource_type=auto để nhận cả ảnh & video một cách
                                        // thống nhất
                                        ObjectUtils.asMap(
                                                "resource_type",
                                                "auto",
                                                "folder",
                                                folder,
                                                "use_filename",
                                                true,
                                                "unique_filename",
                                                true));
                urls.add((String) res.get("secure_url")); // luôn trả URL https
            } catch (Exception e) {
                log.error("Upload failed: {}", f.getOriginalFilename(), e);
                // tiếp tục file khác, không văng lỗi toàn batch
            }
        }
        return urls.toArray(new String[0]);
    }
}

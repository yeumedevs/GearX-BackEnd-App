package com.gearx.feature.media.util;

import org.springframework.web.multipart.MultipartFile;

public interface UploadMedia {
    String[] uploadMedia(MultipartFile[] files);
}

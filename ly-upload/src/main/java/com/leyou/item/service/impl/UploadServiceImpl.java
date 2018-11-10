package com.leyou.item.service.impl;

import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.service.IUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadServiceImpl implements IUploadService {

    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (!suffixes.contains(contentType)) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            File dir = new File("E:\\nginx\\nginx-1.13.8\\html");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(new File(dir, file.getOriginalFilename()));
            String url = "http://image.leyou.com/" + file.getOriginalFilename();
            return url;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.UPLOAD_IMAGE_ERROR);
        }
    }
}

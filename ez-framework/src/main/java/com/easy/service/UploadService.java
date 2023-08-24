package com.easy.service;

import com.easy.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wxx
 * @create 08--22--9:25
 * @description:
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
